
import argparse
from pathlib import Path

def parse_failed_for_normal_build(log_path, output_path, keyword="BUILD FAILED in", context_lines=300):
    try:
        with open(log_path, "r", encoding="utf-8", errors="ignore") as f:
            lines = f.readlines()
    except FileNotFoundError:
        print(f"找不到檔案：{log_path}")
        return

    target_lines = [i for i, line in enumerate(lines) if keyword.lower() in line.lower()]

    with open(output_path, "w", encoding="utf-8") as out_file:
        if not target_lines:
            out_file.write("無 BUILD FAILED 紀錄\n")
        else:
            for i, line_number in enumerate(target_lines):
                start = max(0, line_number - context_lines)
                context = lines[start:line_number]
                out_file.write(f"===== 300 lines before line {line_number + 1} (Match #{i+1}) =====\n")
                for j, content in enumerate(context, start=start + 1):
                    out_file.write(f"{j:4d}: {content}")
                out_file.write("\n\n")
    print(f"擷取完成，輸出至：{output_path}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="擷取 BUILD FAILED 前 300 行 Log")
    parser.add_argument("log_path", type=Path, help="輸入的 log 檔案路徑")
    parser.add_argument("output_path", type=Path, help="輸出的檔案路徑")
    args = parser.parse_args()

    parse_failed_for_normal_build(args.log_path, args.output_path)
