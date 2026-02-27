# 🚀 Как скачать и установить VLESS VPN APK

## ✅ Статус APK

**Версия:** 1.0.0  
**Статус:** 📦 Готовится к сборке  
**Где:** GitHub Releases

---

## 📥 Варианты получения APK

### Вариант 1: GitHub Releases (Когда будет готов)

1. Откройте: https://github.com/zametkikostik/vless-vpn-fdroid/releases
2. Скачайте последний релиз
3. Установите на Android

### Вариант 2: GitHub Actions (Автоматическая сборка)

1. Откройте: https://github.com/zametkikostik/vless-vpn-fdroid/actions
2. Выберите последний запуск "Build APK"
3. Скачайте артефакт
4. Установите

### Вариант 3: Собрать самостоятельно

```bash
# Установите Flutter
cd /tmp
wget https://storage.googleapis.com/flutter_infra_release/releases/stable/linux/flutter_linux_3.19.0-stable.tar.xz
tar xf flutter_linux_3.19.0-stable.tar.xz
export PATH="$PATH:/tmp/flutter/bin"

# Соберите APK
cd ~/fdroid-vless-vpn
flutter pub get
flutter build apk --release

# APK будет в:
ls -lh build/app/outputs/flutter-apk/app-release.apk
```

---

## ⏱️ Когда будет готов?

APK автоматически собирается при:
- Создании тега версии (например, `v1.0.0`)
- Push в ветку `main`
- Ручном запуске GitHub Actions

**Проверить статус:** https://github.com/zametkikostik/vless-vpn-fdroid/actions

---

## 📱 Установка APK

### Через USB:

```bash
adb install ~/fdroid-vless-vpn/build/app/outputs/flutter-apk/app-release.apk
```

### Через файловый менеджер:

1. Скопируйте APK на телефон
2. Откройте файловый менеджер
3. Нажмите на APK
4. Разрешите установку
5. Установите

---

## 🔍 Проверка

После установки:
1. Откройте приложение VLESS VPN
2. Нажмите "Scan Servers"
3. Выберите сервер
4. Нажмите "Connect"

---

## 📊 Информация об APK

```
Package: org.fdroid.vlessvpn
Version: 1.0.0 (1)
Min SDK: 21 (Android 5.0)
Target SDK: 34 (Android 14)
Size: ~30-50 MB
Architecture: arm64-v8a, armeabi-v7a, x86_64
```

---

## ⚠️ Проблемы и решения

### "App not installed"

- Удалите старую версию
- Разрешите установку из неизвестных источников

### "Parse error"

- Проверьте версию Android (требуется 5.0+)
- Скачайте APK заново

### "Build failed"

- Проверьте что Flutter установлен
- Запустите `flutter doctor`
- Исправьте ошибки

---

## 📞 Поддержка

- **Репозиторий:** https://github.com/zametkikostik/vless-vpn-fdroid
- **Issues:** https://github.com/zametkikostik/vless-vpn-fdroid/issues
- **Actions:** https://github.com/zametkikostik/vless-vpn-fdroid/actions

---

**Успехов! 🎉**
