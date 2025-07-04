#!/bin/bash

AVD_NAME="jenkins-avd"
SYSTEM_IMAGE="system-images;android-30;google_apis;arm64-v8a"
DEVICE="pixel_4"

# 1. 安裝 system-image
$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "$SYSTEM_IMAGE"

# 2. 判斷 AVD 是否已存在，沒有才建立
if ! $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager list avd | grep -q "$AVD_NAME"; then
  echo "==> 建立新的 AVD：$AVD_NAME"
  echo "no" | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd \
    -n "$AVD_NAME" \
    -k "$SYSTEM_IMAGE" \
    --device "$DEVICE"
else
  echo "==> AVD $AVD_NAME 已存在"
fi

# 3. 檢查並關閉所有已啟動的 emulator
echo "==> 檢查並關閉所有已啟動的 emulator..."
EMULATORS=$($ANDROID_HOME/platform-tools/adb devices | grep emulator | cut -f1)

if [ -z "$EMULATORS" ]; then
  echo "  無已啟動的 emulator"
else
  for line in $EMULATORS; do
    echo "  關閉 emulator: $line"
    $ANDROID_HOME/platform-tools/adb -s $line emu kill
  done
  sleep 5
fi

# 4. 啟動 emulator（headless 模式）
echo "==> 啟動 emulator $AVD_NAME"
nohup $ANDROID_HOME/emulator/emulator -avd "$AVD_NAME" -no-window -no-audio -no-snapshot > /tmp/emulator.log 2>&1 &

# 5. 等待 emulator 完全開機
$ANDROID_HOME/platform-tools/adb wait-for-device
BOOT_COMPLETED=""
while [ "$BOOT_COMPLETED" != "1" ]; do
  BOOT_COMPLETED=$($ANDROID_HOME/platform-tools/adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')
  echo "等待 emulator 開機中...（狀態：$BOOT_COMPLETED）"
  sleep 2
done

echo "==> Emulator $AVD_NAME 已啟動，準備執行測試"