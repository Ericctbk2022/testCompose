import sys
import os
import shutil
from bs4 import BeautifulSoup
from datetime import datetime

# --- 🧩 檢查輸入參數 ---
if len(sys.argv) < 2:
    print("❌ 請提供測試報告 HTML 路徑，例如：python parse_failed_tests.py <report_path> [output_dir]")
    sys.exit(1)

report_path = sys.argv[1]
output_base = sys.argv[2] if len(sys.argv) > 2 else "test_failures_output"

if not os.path.exists(report_path):
    print(f"❌ 測試報告不存在：{report_path}")
    sys.exit(1)

print(f"📄 找到測試報告：{report_path}")

# --- 🗂 建立輸出資料夾 ---
timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
output_dir = os.path.join(output_base, f"report_{timestamp}")
os.makedirs(output_dir, exist_ok=True)

# --- 🔎 解析主報告 index.html 中的 failed test hrefs ---
with open(report_path, 'r', encoding='utf-8') as f:
    soup = BeautifulSoup(f, 'html.parser')

failed_tab = soup.find("div", id="tab0")
hrefs = [a['href'] for a in failed_tab.find_all('a', href=True)]

html_files = set(href.split('#')[0] for href in hrefs if href.endswith('.html'))

# --- 📝 儲存 failed links ---
with open(os.path.join(output_dir, "failed_links.txt"), "w", encoding="utf-8") as f:
    f.write("---- ❌ Failed Test Links ----\n")
    for href in hrefs:
        f.write(f"{href}\n")
print("✅ Failed links 已儲存")

# --- 📁 複製原始 index.html 報告 ---
shutil.copy2(report_path, os.path.join(output_dir, "index.html"))
print("✅ index.html 已複製")

# --- 📥 複製每個子 class 的 html 報告 ---
report_root = os.path.dirname(report_path)
copied_html_paths = []

for html_rel in html_files:
    src = os.path.join(report_root, html_rel)
    dst = os.path.join(output_dir, html_rel)

    if os.path.exists(src):
        os.makedirs(os.path.dirname(dst), exist_ok=True)
        shutil.copy2(src, dst)
        copied_html_paths.append(dst)
        print(f"📎 已複製 {html_rel}")
    else:
        print(f"⚠️ 找不到檔案：{html_rel}")

# --- 🧪 生成 summary HTML 報告 ---
summary_rows = []
for file_path in copied_html_paths:
    with open(file_path, "r", encoding="utf-8") as f:
        class_soup = BeautifulSoup(f, "html.parser")

    class_name = class_soup.find("h1").text.replace("Class ", "").strip()
    failed_section = class_soup.find("div", id="tab0")

    if not failed_section:
        continue  # 沒有 failed 區塊

    for test in failed_section.find_all("div", class_="test"):
        test_name = test.find("h3").text.strip()
        trace_lines = test.find("pre").text.strip().splitlines()[:10]
        trace_html = "<br>".join(trace_lines)

        summary_rows.append(f"""
            <tr>
                <td>{class_name}</td>
                <td>{test_name}</td>
                <td><pre>{trace_html}</pre></td>
            </tr>
        """)

# --- 🧾 組裝 summary HTML ---
summary_html = f"""
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Failed Test Summary</title>
    <style>
        body {{
            font-family: Arial, sans-serif;
            margin: 20px;
        }}

        table {{
            border-collapse: collapse;
            width: 100%;
        }}

        th, td {{
            border: 1px solid #444;
            padding: 10px;
            vertical-align: top;
            text-align: left;
        }}

        th {{
            background-color: #f0f0f0;
            font-weight: bold;
        }}

        tr:nth-child(even) {{
            background-color: #f9f9f9;
        }}

        tr:hover {{
            background-color: #eef;
        }}

        pre {{
            margin: 0;
            font-family: Consolas, monospace;
            font-size: 13px;
            line-height: 1.4;
            white-space: pre-wrap;
            word-break: break-word;
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

print(f"📄 測試錯誤摘要 HTML 已輸出到：{output_dir}/failure_summary.html")