#!/bin/bash
# Автоматическая сборка APK через Flutter

set -e

echo "========================================"
echo "🚀 Сборка VLESS VPN APK"
echo "========================================"

# Пути
FLUTTER_PATH="/tmp/flutter"
PROJECT_DIR="$HOME/fdroid-vless-vpn"
OUTPUT_DIR="$PROJECT_DIR/releases"

# Проверка Flutter
if [ ! -d "$FLUTTER_PATH" ]; then
    echo "❌ Flutter не найден! Установка..."
    cd /tmp
    wget -q --show-progress https://storage.googleapis.com/flutter_infra_release/releases/stable/linux/flutter_linux_3.19.0-stable.tar.xz
    tar xf flutter_linux_3.19.0-stable.tar.xz
fi

# Настройка PATH
export PATH="$PATH:$FLUTTER_PATH/bin:$HOME/Android/Sdk/cmdline-tools/latest/bin:$HOME/Android/Sdk/platform-tools"

echo ""
echo "✅ Flutter: $(flutter --version 2>&1 | head -1)"
echo "✅ Android SDK: $HOME/Android/Sdk"
echo ""

cd "$PROJECT_DIR"

# Создание директории output
mkdir -p "$OUTPUT_DIR"

# Получение зависимостей
echo "📦 Получение зависимостей..."
flutter pub get

# Сборка
echo ""
echo "🔨 Сборка APK..."
flutter build apk --release

# Копирование
echo ""
echo "📦 Копирование APK..."
cp build/app/outputs/flutter-apk/*.apk "$OUTPUT_DIR/" 2>/dev/null || true

# Информация
echo ""
echo "========================================"
echo "✅ Сборка завершена!"
echo "========================================"
echo ""
echo "📁 APK файлы:"
ls -lh "$OUTPUT_DIR"/*.apk 2>/dev/null || echo "❌ APK не найдены"
echo ""
echo "📊 Информация:"
flutter --version 2>&1 | head -3
echo ""
echo "========================================"
