# 🚀 Инструкция по сборке APK

## ⚡ Быстрая сборка (если есть Flutter)

### 1. Установите Flutter (если не установлен)

```bash
# Скачайте Flutter
cd /tmp
wget https://storage.googleapis.com/flutter_infra_release/releases/stable/linux/flutter_linux_3.19.0-stable.tar.xz

# Распакуйте
tar xf flutter_linux_3.19.0-stable.tar.xz

# Добавьте в PATH
export PATH="$PATH:/tmp/flutter/bin"

# Проверьте
flutter --version
```

### 2. Соберите APK

```bash
cd ~/fdroid-vless-vpn

# Получите зависимости
flutter pub get

# Соберите release APK
flutter build apk --release

# APK будет в:
ls -lh build/app/outputs/flutter-apk/app-release.apk
```

---

## 🎯 Готовый APK (Альтернатива)

Если нет времени собирать APK, используйте готовый шаблон:

### Вариант 1: GitHub Actions (Автоматически)

1. Откройте: https://github.com/zametkikostik/vless-vpn-fdroid/actions
2. Выберите последний запуск "Build APK"
3. Скачайте артефакт `vless-vpn-apks.zip`
4. Распакуйте и установите на Android

### Вариант 2: Локальная сборка через Android Studio

1. Откройте Android Studio
2. File → Open → Выберите `~/fdroid-vless-vpn`
3. Build → Build Bundle(s) / APK(s) → Build APK(s)
4. APK будет в: `app/build/outputs/apk/release/`

---

## 📦 Структура APK

После сборки:

```
fdroid-vless-vpn/build/app/outputs/flutter-apk/
├── app-release.apk              # Universal APK (~30-50 MB)
├── app-arm64-v8a-release.apk    # Для 64-bit устройств (~15-25 MB)
├── app-armeabi-v7a-release.apk  # Для 32-bit устройств
└── app-x86_64-release.apk       # Для эмуляторов
```

---

## 🔧 Проверка APK

```bash
# Проверить подпись
apksigner verify --verbose build/app/outputs/flutter-apk/app-release.apk

# Информация об APK
aapt dump badging build/app/outputs/flutter-apk/app-release.apk | grep -E "package:|sdkVersion:"
```

---

## 📤 Отправка в Git

```bash
# Создайте директорию для APK
mkdir -p ~/fdroid-vless-vpn/releases

# Скопируйте APK
cp build/app/outputs/flutter-apk/app-release.apk releases/vless-vpn-1.0.0.apk

# Добавьте в Git
git add releases/vless-vpn-1.0.0.apk
git commit -m "release: Add APK v1.0.0"
git push origin main

# Или создайте релиз на GitHub
gh release create v1.0.0 releases/vless-vpn-1.0.0.apk --title "VLESS VPN v1.0.0"
```

---

## 🎯 GitHub Releases

Для создания релиза с APK:

```bash
# Установите GitHub CLI
sudo apt install gh

# Авторизуйтесь
gh auth login

# Создайте релиз
cd ~/fdroid-vless-vpn
gh release create v1.0.0 \
  build/app/outputs/flutter-apk/app-release.apk \
  --title "VLESS VPN v1.0.0" \
  --notes "Production release with DPI bypass"
```

---

## 📊 Размер APK

| Тип | Размер | Для кого |
|-----|--------|----------|
| Universal | ~30-50 MB | Все устройства |
| arm64-v8a | ~15-25 MB | Современные телефоны |
| armeabi-v7a | ~12-20 MB | Старые телефоны |
| x86_64 | ~20-30 MB | Эмуляторы |

---

## ⚠️ Проблемы и решения

### Ошибка: "SDK not found"

```bash
export ANDROID_HOME=~/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
```

### Ошибка: "License not accepted"

```bash
sdkmanager --licenses
y # Нажмите y для всех лицензий
```

### Ошибка: "Flutter not found"

```bash
export PATH="$PATH:/tmp/flutter/bin"
```

---

## 🎉 Готово!

После сборки APK можно:
1. Установить на Android через USB
2. Опубликовать на GitHub Releases
3. Отправить в F-Droid

**Успехов! 🚀**
