#!/bin/bash

# 檢查是否提供 4 個參數（Token & 訊息）
if [ "$#" -ne 4 ]; then
    echo "Usage: $0 <LINE_BOT_TOKEN> <message> <PACKAGE_ID> <STICKER_ID>"
    exit 1
fi

# 讀取外部參數
TOKEN="{$1}"
TO="C9820ccb05eade1a38d188dce01dc98ed"
MESSAGE="$2"
PACKAGE_ID="$3"
STICKER_ID="$4"

# 檢查 TOKEN 是否設置
if [ -z "$TOKEN" ]; then
    echo "Error: LINE_BOT_TOKEN is missing!"
    exit 1
fi

# 發送 LINE 訊息
curl -v -X POST https://api.line.me/v2/bot/message/push \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $TOKEN" \
    -d "{
        \"to\": \"$TO\",
        \"messages\": [
            {
                \"type\": \"text\",
                \"text\": \"$MESSAGE\"
            },
            {
                \"type\": \"sticker\",
                \"packageId\": \"$PACKAGE_ID\",
                \"stickerId\": \"$STICKER_ID\"
            }
        ]
    }"