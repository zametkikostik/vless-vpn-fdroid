# 📝 F-Droid Forum Submission — Без GitLab

## ✅ Самый простой способ!

Не нужно создавать аккаунт на GitLab или работать с метаданными.

---

## 🚀 Подача заявки через форум (5 минут)

### Шаг 1: Откройте форум

Перейдите: https://forum.f-droid.org/

### Шаг 2: Создайте аккаунт

1. Нажмите **Sign Up** (вверху)
2. Введите email
3. Подтвердите email
4. Готово!

### Шаг 3: Создайте тему

1. Раздел: **App Requests**
2. Нажмите **New Topic**
3. Заполните форму ниже

---

## 📋 Шаблон для форума

**Название темы:**
```
VLESS VPN - Open-source VPN with DPI bypass (GPL-3.0-only)
```

**Текст темы:**
```markdown
## App Information

**App Name:** VLESS VPN
**Source Code:** https://github.com/zametkikostik/vless-vpn-fdroid
**License:** GPL-3.0-only
**Category:** Internet, Security
**Current Version:** 1.0.0

## Description

VLESS VPN is a free and open-source VPN client with DPI bypass capabilities 
for circumventing internet censorship.

### Features

- 🔒 DPI Bypass with packet fragmentation and TLS mimicry
- 🌐 Server scanner from public sources
- 🚀 Auto-start on boot
- 🛡️ No logs, no tracking, no telemetry
- 📱 Privacy focused design

### Technical Details

- **Protocol:** VLESS with Reality encryption
- **DPI Evasion:** Packet fragmentation (50-200 bytes), padding, TLS 1.3 mimicry
- **Minimum SDK:** 21 (Android 5.0)
- **Target SDK:** 34 (Android 14)
- **Language:** Kotlin
- **Build:** Gradle

### Compliance Checklist

- [x] Free and Open Source (GPL-3.0-only)
- [x] No proprietary dependencies
- [x] No tracking or analytics
- [x] No Google Play Services required
- [x] Reproducible builds supported
- [x] Privacy focused (no data collection)
- [x] Source code publicly available

### Anti-Features

All anti-features are **disabled**:
- NonFreeNet: false
- Advertising: false
- Tracking: false
- Telemetry: false
- ProprietaryDependencies: false

## Build Instructions

```bash
cd ~
git clone https://github.com/zametkikostik/vless-vpn-fdroid.git
cd vless-vpn-fdroid
./gradlew assembleRelease
```

APK will be in: `app/build/outputs/apk/release/`

## Why This App Should Be in F-Droid

This app provides important functionality for users in regions with internet 
censorship, helping them access information freely while maintaining privacy 
and security.

Unlike many VPN apps that collect user data or include proprietary components, 
VLESS VPN is:
- 100% free and open-source
- Zero data collection
- No ads or tracking
- Works without Google Play Services

## Additional Notes

- All dependencies are from F-Droid compatible repositories
- No proprietary code or libraries are used
- Developer is committed to maintaining the app and responding to issues

---

**Submitted by:** Anonymous Developer
**Date:** 2026-02-27
```

---

## ⏱️ Что дальше?

### После публикации темы:

1. **Автоматическая проверка** (1-2 часа)
   - Бот проверит доступность репозитория
   - Проверит лицензию

2. **Первичный просмотр** (1-3 дня)
   - Модератор посмотрит тему
   - Может задать вопросы

3. **Тестирование** (1-2 недели)
   - F-Droid команда соберёт приложение
   - Проверят на воспроизводимость

4. **Включение** (3-7 дней)
   - Добавят в репозиторий
   - Опубликуют на сайте

**Итого:** 2-4 недели

---

## 📞 Если будут вопросы

Команда F-Droid может спросить:

### ❓ "Can you provide metadata file?"

**Ответ:**
```
Yes! I've prepared the metadata file. Here it is:

[Скопируйте содержимое org.fdroid.vlessvpn.yml]

I can submit it via GitLab MR if needed.
```

### ❓ "Is there any proprietary dependency?"

**Ответ:**
```
No, all dependencies are open-source and F-Droid compatible:
- AndroidX libraries
- Kotlin coroutines
- Material Design components

No Google Play Services, no Firebase, no proprietary SDKs.
```

### ❓ "Can you confirm no user data is collected?"

**Ответ:**
```
Confirmed. This app does not collect, store, or transmit any personal data.
- No analytics
- No tracking
- No telemetry
- All processing is done locally on device
```

---

## 🔗 Полезные ссылки

| Что | Где |
|-----|-----|
| Форум F-Droid | https://forum.f-droid.org/ |
| App Requests | https://forum.f-droid.org/c/app-requests/ |
| Политика включения | https://f-droid.org/docs/Inclusion_Policy/ |
| Требования | https://f-droid.org/docs/Inclusion_Policy/ |

---

## ✅ Преимущества форума

- ✅ Не нужен GitLab аккаунт
- ✅ Проще чем Merge Request
- ✅ Можно обсудить детали
- ✅ Команда F-Droid поможет с метаданными

---

## 🎯 Альтернатива: Email

Если не хотите использовать форум:

**Email:** data@f-droid.org

**Тема:** F-Droid Inclusion Request: VLESS VPN

**Тело письма:** Используйте тот же шаблон что и для форума.

---

## 📊 Сравнение способов

| Способ | GitLab | Сложность | Рекомендация |
|--------|--------|-----------|--------------|
| **Форум** | ❌ | ⭐ | ✅ Лучший |
| **Email** | ❌ | ⭐⭐ | ✅ Хорошо |
| **GitLab MR** | ✅ | ⭐⭐⭐ | Для продвинутых |

---

**Успехов с подачей! 🚀**
