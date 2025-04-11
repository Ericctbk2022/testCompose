import sys
import os
import shutil
import zipfile
from bs4 import BeautifulSoup

# ✅ 檢查參數
if len(sys.argv) < 2:
    print("❌ 請提供測試報告 HTML 路徑，例如：python parse_failed_tests.py <report_path> [output_dir]")
    sys.exit(1)

report_path = sys.argv[1]
output_base = sys.argv[2] if len(sys.argv) > 2 else "output_results"
output_dir = os.path.join(output_base, "report")

# 🔁 清空舊報告
if os.path.exists(output_dir):
    shutil.rmtree(output_dir)
os.makedirs(output_dir)

# ✅ 讀取 index.html
with open(report_path, 'r', encoding='utf-8') as f:
    soup = BeautifulSoup(f, 'html.parser')

failed_tab = soup.find("div", id="tab0")
hrefs = [a['href'] for a in failed_tab.find_all('a', href=True)]
html_files = set(href.split('#')[0] for href in hrefs if href.endswith('.html'))

# 📝 儲存 failed_links.txt
with open(os.path.join(output_dir, "failed_links.txt"), "w", encoding="utf-8") as f:
    f.write("---- ❌ Failed Test Links ----\n")
    for href in hrefs:
        f.write(f"{href}\n")

# 📎 複製 index.html
shutil.copy2(report_path, os.path.join(output_dir, "index.html"))

# 📁 複製對應 class 測試報告
report_root = os.path.dirname(report_path)
copied_html_paths = []
for html_rel in html_files:
    src = os.path.join(report_root, html_rel)
    dst = os.path.join(output_dir, html_rel)
    if os.path.exists(src):
        os.makedirs(os.path.dirname(dst), exist_ok=True)
        shutil.copy2(src, dst)
        copied_html_paths.append((dst, html_rel))

# 🧪 生成 summary 表格資料 & Fail_item.txt
summary_rows = []
fail_txt_path = os.path.join(output_dir, "Fail_item.txt")
fail_count = 0

with open(fail_txt_path, "w", encoding="utf-8") as fail_txt:
    for full_path, rel_path in copied_html_paths:
        with open(full_path, "r", encoding="utf-8") as f:
            class_soup = BeautifulSoup(f, "html.parser")

        class_name = class_soup.find("h1").text.replace("Class ", "").strip()
        failed_section = class_soup.find("div", id="tab0")
        if not failed_section:
            continue

        test_items = failed_section.find_all("div", class_="test")
        if not test_items:
            continue

        fail_txt.write(f"[{class_name}]\n")

        for test in test_items:
            test_name = test.find("h3").text.strip()
            trace_lines = test.find("pre").text.strip().splitlines()
            if trace_lines:
                first_line = trace_lines[0]
                fail_txt.write(f"  - {test_name}: {first_line}\n")
                trace_html = "<br>".join(trace_lines[:10])
                link_path = os.path.relpath(rel_path, start=".")
                summary_rows.append(f"""
                    <tr>
                        <td>{class_name}</td>
                        <td><a class="test-link" href="{link_path}#{test_name}">{test_name}</a></td>
                        <td><pre>{trace_html}</pre></td>
                    </tr>
                """)
                fail_count += 1

        fail_txt.write("\n")

    fail_txt.write(f"==> 總錯誤項目數量：{fail_count}\n")

# 🧾 產出 failure_summary.html（含樣式）
summary_html = f"""
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Failed Test Summary</title>
    <style>
        body {{
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #fff;
        }}
        h1 {{
            font-size: 24px;
            margin-bottom: 20px;
        }}
        table {{
            border-collapse: collapse;
            width: 100%;
            table-layout: fixed;
            word-break: break-word;
        }}
        th, td {{
            border: 1px solid #333;
            padding: 10px;
            vertical-align: top;
            text-align: left;
        }}
        th {{
            background-color: #dfe8f7;
            color: #000;
        }}
        tr:nth-child(even) {{
            background-color: #f8f8f8;
        }}
        tr:hover {{
            background-color: #f0f8ff;
        }}
        pre {{
            margin: 0;
            font-family: Consolas, monospace;
            font-size: 13px;
            line-height: 1.4;
            white-space: pre-wrap;
            word-break: break-word;
        }}
        a.test-link {{
            color: #0645AD;
            text-decoration: none;
        }}
        a.test-link:hover {{
            text-decoration: underline;
        }}
    </style>
</head>
<body>
    <h1>🧪 Failed Test Summary</h1>
    <table>
        <thead>
            <tr>
                <th>Class</th>
                <th>Test Name</th>
                <th>Stack Trace (First 10 lines)</th>
            </tr>
        </thead>
        <tbody>
            {''.join(summary_rows)}
        </tbody>
    </table>
</body>
</html>
"""

with open(os.path.join(output_dir, "failure_summary.html"), "w", encoding="utf-8") as f:
    f.write(summary_html)

# 📦 壓縮整個 report 資料夾
zip_path = os.path.join(output_base, "report.zip")
if os.path.exists(zip_path):
    os.remove(zip_path)

with zipfile.ZipFile(zip_path, 'w', zipfile.ZIP_DEFLATED) as zipf:
    for root, dirs, files in os.walk(output_dir):
        for file in files:
            full_path = os.path.join(root, file)
            rel_path = os.path.relpath(full_path, output_base)
            zipf.write(full_path, arcname=rel_path)

# 📢 Jenkins Console Summary
print("\n====== 📢 Jenkins Build Summary ======")
print(f"Fail Count: {fail_count}")
print("Top Failures:")
with open(fail_txt_path, "r", encoding="utf-8") as f:
    lines = [line.strip() for line in f.readlines() if line.strip() and not line.startswith("[") and not line.startswith("==>")]
    for line in lines[:5]:
        print(f" - {line}")
print("📄 詳細見：failure_summary.html / Fail_item.txt")
print(f"🗂 Report zipped at: {zip_path}")
print("======================================\n")