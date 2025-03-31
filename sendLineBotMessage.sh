#!/bin/bash

# 檢查是否提供 2 個參數（Token & 訊息）
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <LINE_BOT_TOKEN> <message>"
    exit 1
fi

# 讀取外部參數
TOKEN="{$1}"
TO="C9820ccb05eade1a38d188dce01dc98ed"
MESSAGE="$2"

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
        \"type\": \"textV2\",
        \"text\": \"$MESSAGE\",
        \"substitution\": {
          \"A\": { \"type\": \"emoji\", \"productId\": \"5ac21a13031a6752fb806d57\", \"emojiId\": \"001\" },
          \"B\": { \"type\": \"emoji\", \"productId\": \"5ac21a13031a6752fb806d57\", \"emojiId\": \"002\" },
          \"C\": { \"type\": \"emoji\", \"productId\": \"5ac21a13031a6752fb806d57\", \"emojiId\": \"003\" },
          \"team\": { \"type\": \"emoji\", \"productId\": \"5ac21a18040ab15980c9b43e\", \"emojiId\": \"097\" },
          \"arrow\": { \"type\": \"emoji\", \"productId\": \"5ac21b4f031a6752fb806d59\", \"emojiId\": \"142\" },
          \"trumpetL\": { \"type\": \"emoji\", \"productId\": \"5ac21a18040ab15980c9b43e\", \"emojiId\": \"012\" },
          \"hint\": { \"type\": \"emoji\", \"productId\": \"5ac21a18040ab15980c9b43e\", \"emojiId\": \"030\" },
          \"new\": { \"type\": \"emoji\", \"productId\": \"5ac21a18040ab15980c9b43e\", \"emojiId\": \"187\" },
          \"environment\": { \"type\": \"emoji\", \"productId\": \"5ac21542031a6752fb806d55\", \"emojiId\": \"211\" },
          \"version\": { \"type\": \"emoji\", \"productId\": \"5ac2216f040ab15980c9b448\", \"emojiId\": \"150\" },
          \"note\": { \"type\": \"emoji\", \"productId\": \"5ac21542031a6752fb806d55\", \"emojiId\": \"215\" },
          \"test\": { \"type\": \"emoji\", \"productId\": \"5ac21a18040ab15980c9b43e\", \"emojiId\": \"025\" }
        }
      }
    ]
  }"