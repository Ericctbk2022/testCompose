import sys
import os
import shutil
from bs4 import BeautifulSoup
from datetime import datetime
from urllib.parse import urlparse

# ✅ 參數處理
if len(sys.argv) < 2:
    print("❌ 請提供測試報告 HTML 路徑，例如：python parse_failed_tests.py <report_path> [output_dir]")
    sys.exit(1)

report_path = sys.argv[1]
output_dir = sys.argv[2] if len(sys.argv) > 2 else "test_failures_output"

if not os.path.exists(report_path):
    print(f"❌ 測試報告不存在：{report_path}")
    sys.exit(1)

print(f"📄 找到測試報告：{report_path}")

# 建立結果輸出資料夾
timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
output_path = os.path.join(output_dir, f"report_{timestamp}")
os.makedirs(output_path, exist_ok=True)

# 解析報告 HTML
with open(report_path, 'r', encoding='utf-8') as f:
    soup = BeautifulSoup(f, 'html.parser')

failed_tab = soup.find("div", id="tab0")
hrefs = [a['href'] for a in failed_tab.find_all('a', href=True)]

# ✔️ 整理出所有 HTML 檔案（忽略 #fragment, 去重複）
html_files = set()
for href in hrefs:
    path = href.split('#')[0]  # 去掉錨點
    if path.endswith('.html'):
        html_files.add(path)

# 儲存 failed_links.txt
failed_txt_path = os.path.join(output_path, "failed_links.txt")
with open(failed_txt_path, "w", encoding="utf-8") as f:
    f.write("---- ❌ Failed Test Links ----\n")
    for href in hrefs:
        f.write(f"{href}\n")

print(f"✅ Failed links 已儲存：{failed_txt_path}")

# 複製 index.html
shutil.copy2(report_path, os.path.join(output_path, "index.html"))
print("✅ 原始 index.html 已複製")

# 複製對應的 HTML 檔案
report_dir = os.path.dirname(report_path)

for html_rel_path in html_files:
    source_file = os.path.join(report_dir, html_rel_path)
    dest_file = os.path.join(output_path, html_rel_path)

    if os.path.exists(source_file):
        os.makedirs(os.path.dirname(dest_file), exist_ok=True)
        shutil.copy2(source_file, dest_file)
        print(f"📎 複製 {html_rel_path}")
    else:
        print(f"⚠️ 找不到檔案：{html_rel_path}")

print(f"\n📦 所有檔案已整理完成：{output_path}")