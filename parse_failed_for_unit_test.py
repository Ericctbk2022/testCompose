
import sys
import os
import shutil
import zipfile
from bs4 import BeautifulSoup

def main():
    # ✅ 檢查參數
    if len(sys.argv) < 2:
        print("❌ 請提供測試報告 HTML 路徑，例如：python parse_failed_for_unit_test.py <report_path> [output_dir]")
        return

    report_path = sys.argv[1]
    output_base = sys.argv[2] if len(sys.argv) > 2 else "output_report"
    output_dir = os.path.join(output_base, "report")

    # ✅ 讀取 index.html 並找出 failed 測試對應的 html 檔案
    with open(report_path, 'r', encoding='utf-8') as f:
        soup = BeautifulSoup(f, 'html.parser')

    failed_tab = soup.find("div", id="tab0")
    if not failed_tab:
        print("❌ 找不到失敗測試的標籤區塊 tab0")
        return

    href_tags = failed_tab.find_all('a', href=True)
    html_files = set(a['href'].split('#')[0] for a in href_tags if a['href'].endswith('.html'))

    # ✅ 如果沒有任何失敗測試，結束程式，不建立資料夾
    if not html_files:
        print("\n====== 📢 Jenkins Build Summary ======")
        print("✅ 沒有測試錯誤")
        return

    # 🔁 清空舊報告
    if os.path.exists(output_dir):
        shutil.rmtree(output_dir)
    os.makedirs(output_dir)

    # 📁 複製 failed 測試報告 HTML 檔案
    report_root = os.path.dirname(report_path)
    for html_rel in html_files:
        src = os.path.join(report_root, html_rel)
        dst = os.path.join(output_dir, html_rel)
        os.makedirs(os.path.dirname(dst), exist_ok=True)
        shutil.copy2(src, dst)

    print("\n====== 📢 Jenkins Build Summary ======")
    print(f"❗ 偵測到 {len(html_files)} 筆失敗測試，報告已輸出至：{output_dir}")

if __name__ == "__main__":
    main()
