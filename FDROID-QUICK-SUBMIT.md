# 🚀 F-Droid Submission — Быстрый Старт

## ✅ Всё готово!

Метаданные для F-Droid созданы и находятся в репозитории.

---

## 📋 3 шага до подачи заявки

### Шаг 1: Форк на GitLab (2 минуты)

1. Откройте: https://gitlab.com/fdroid/fdroiddata/-/forks
2. Нажмите **Fork**
3. Выберите ваш аккаунт

**Готово!** У вас есть копия репозитория.

---

### Шаг 2: Клонирование и копирование (3 минуты)

```bash
# Склонируйте ваш форк
cd ~
git clone https://gitlab.com/ВАШ_USERNAME/fdroiddata.git
cd fdroiddata

# Скопируйте метаданные
cp ~/fdroid-vless-vpn/org.fdroid.vlessvpn.yml \
   ~/fdroiddata/metadata/org.fdroid.vlessvpn.yml

# Проверьте
ls -la metadata/org.fdroid.vlessvpn.yml
```

---

### Шаг 3: Коммит и Merge Request (5 минут)

```bash
# Коммит
git add metadata/org.fdroid.vlessvpn.yml
git commit -m "Add VLESS VPN (org.fdroid.vlessvpn)

Free open-source VPN with DPI bypass for circumventing censorship.
License: GPL-3.0-only
Source: https://github.com/zametkikostik/vless-vpn-fdroid"

# Отправка
git push origin main
```

Теперь создайте Merge Request:

1. Откройте: https://gitlab.com/fdroid/fdroiddata/-/merge_requests
2. Нажмите **New Merge Request**
3. Выберите ваш форк и ветку `main`
4. Заполните описание (шаблон в GITLAB-SUBMISSION.md)
5. Нажмите **Create Merge Request**

---

## 📝 Шаблон для Merge Request

Скопируйте и вставьте в описание MR:

```markdown
## Add VLESS VPN (org.fdroid.vlessvpn)

### App Information
- **Name:** VLESS VPN
- **Package ID:** org.fdroid.vlessvpn
- **Version:** 1.0.0
- **License:** GPL-3.0-only
- **Source Code:** https://github.com/zametkikostik/vless-vpn-fdroid
- **Issue Tracker:** https://github.com/zametkikostik/vless-vpn-fdroid/issues

### Description
VLESS VPN is a free and open-source VPN client with DPI bypass capabilities
for circumventing internet censorship.

**Features:**
- DPI Bypass with packet fragmentation and TLS mimicry
- Server scanner from public sources
- Auto-start on boot
- No logs, no tracking, no telemetry
- Privacy focused design

### Compliance
- [x] Free and Open Source (GPL-3.0-only)
- [x] No proprietary dependencies
- [x] No tracking or analytics
- [x] No Google Play Services
- [x] Reproducible builds
- [x] Privacy focused

### Anti-Features
All anti-features are **disabled**:
- NonFreeNet: false
- Advertising: false
- Tracking: false
- Telemetry: false
- ProprietaryDependencies: false

### Build Info
- Min SDK: 21
- Target SDK: 34
- Language: Kotlin
- Build: Gradle

---
**Submitter:** Anonymous Developer
```

---

## ⏱️ Что дальше?

### Автоматические проверки (1-2 часа)
F-Droid CI проверит:
- ✅ Синтаксис YAML
- ✅ Доступность репозитория
- ✅ Версии и теги

### Ручная проверка (2-4 недели)
Команда F-Droid проверит:
- ✅ Требования включения
- ✅ Репродуцируемость
- ✅ Безопасность

---

## 📞 Если нужны изменения

```bash
# Внести правки
cd ~/fdroiddata
nano metadata/org.fdroid.vlessvpn.yml

# Отправить
git add metadata/org.fdroid.vlessvpn.yml
git commit -m "Update metadata per review"
git push origin main
```

MR обновится автоматически!

---

## 🔗 Ссылки

| Что | Где |
|-----|-----|
| Ваш MR | https://gitlab.com/fdroid/fdroiddata/-/merge_requests |
| Статус | https://gitlab.com/fdroid/fdroiddata/-/pipelines |
| Форум | https://forum.f-droid.org/ |
| Документация | https://f-droid.org/docs/ |

---

## ✅ Чеклист

- [ ] Аккаунт GitLab создан
- [ ] Форк fdroiddata сделан
- [ ] Методанные скопированы
- [ ] Коммит отправлен
- [ ] Merge Request создан
- [ ] Описание заполнено

---

## 🎉 Готово!

После создания MR остаётся ждать проверки.

**Ожидаемое время:** 2-4 недели

**Успехов! 🚀**
