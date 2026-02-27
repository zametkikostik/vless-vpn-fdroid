#!/bin/bash
# Скрипт для создания репозитория и отправки кода в GitHub

set -e

REPO_NAME="vless-vpn-fdroid"
REPO_FULL="zametkikostik/$REPO_NAME"
PROJECT_DIR="$HOME/fdroid-vless-vpn"

echo "========================================"
echo "🚀 Отправка VLESS VPN для F-Droid в GitHub"
echo "========================================"
echo ""

cd "$PROJECT_DIR"

# Проверка Git
echo "✅ Проверка Git..."
git status

# Смена remote на SSH
echo ""
echo "🔧 Настройка SSH remote..."
git remote set-url origin git@github.com:$REPO_FULL.git
git remote -v

# Попытка push
echo ""
echo "📤 Отправка в GitHub..."
echo ""
echo "========================================"
echo "⚠️  ВАЖНО: Сначала создайте репозиторий!"
echo "========================================"
echo ""
echo "1. Откройте в браузере:"
echo "   https://github.com/new"
echo ""
echo "2. Введите:"
echo "   Repository name: $REPO_NAME"
echo "   Description: VLESS VPN for F-Droid - Open-source VPN with DPI bypass"
echo "   ✓ Public"
echo "   ✗ Не инициализировать README"
echo ""
echo "3. Нажмите 'Create repository'"
echo ""
echo "4. Затем выполните:"
echo "   git push -u origin main"
echo ""
echo "========================================"

# Проверка подключения
echo ""
echo "🔍 Проверка SSH подключения..."
ssh -T git@github.com 2>&1 | head -3

echo ""
echo "========================================"
echo "Готово к отправке!"
echo "========================================"
