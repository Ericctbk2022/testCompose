

#!/bin/bash

# 檢查是否提供兩個參數
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <to> <message>"
    exit 1
fi

# 讀取環境變數的 Token
TOKEN="{7ye5TfCfI5OUe/lmpRzU4lRdqnHU2iZvPF4dEoz7nKesgUOyW6fB2yenLR1GL6obS3NOg4ZOGk/6Bwc3vQyGCluHS0xHJmuXJGSr0zBRwADPVpXSlodqbESVHCdFu2j4MsXw68cEuRXq1xuKGphLyQdB04t89/1O/w1cDnyilFU=}"
TO="$1"
MESSAGE="$2"

if [ -z "$TOKEN" ]; then
    echo "Error: LINE_BOT_TOKEN is not set!"
    exit 1
fi

curl -v -X POST https://api.line.me/v2/bot/message/push \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $TOKEN" \
    -d "{
        \"to\": \"$TO\",
        \"messages\": [
            {
                \"type\": \"text\",
                \"text\": \"$MESSAGE\"
            }
        ]
    }"