#!/bin/bash

# 檢查至少提供 2 個參數（Token & 訊息），可選提供 PACKAGE_ID & STICKER_ID
if [ "$#" -lt 2 ] || [ "$#" -gt 4 ]; then
    echo "Usage: $0 <LINE_BOT_TOKEN> <message> [PACKAGE_ID] [STICKER_ID]"
    exit 1
fi

# 讀取外部參數
TOKEN="$1"
TO="C9820ccb05eade1a38d188dce01dc98ed"
MESSAGE="$2"
PACKAGE_ID="$3"
STICKER_ID="$4"

# 檢查 TOKEN 是否設置
if [ -z "$TOKEN" ]; then
    echo "Error: LINE_BOT_TOKEN is missing!"
    exit 1
fi

# 開始構建 JSON 訊息
JSON_PAYLOAD="{
    \"to\": \"$TO\",
    \"messages\": [
        {
            \"type\": \"text\",
            \"text\": \"$MESSAGE\"
        }"

# 確保 `PACKAGE_ID` 和 `STICKER_ID` 都有值，才加入貼圖訊息
if [[ -n "$PACKAGE_ID" && -n "$STICKER_ID" ]]; then
    JSON_PAYLOAD+=",{
        \"type\": \"sticker\",
        \"packageId\": \"$PACKAGE_ID\",
        \"stickerId\": \"$STICKER_ID\"
    }"
fi

# 關閉 JSON 結尾
JSON_PAYLOAD+="]}"

echo "====================== Send line notify ======================\n"
echo "$JSON_PAYLOAD"  

# 發送 LINE 訊息
curl -v -X POST https://api.line.me/v2/bot/message/push \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $TOKEN" \
    -d "$JSON_PAYLOAD"