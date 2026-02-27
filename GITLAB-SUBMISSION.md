# 📝 F-Droid GitLab Submission - Пошаговая инструкция

## Цель
Добавить VLESS VPN в официальный репозиторий F-Droid через GitLab Merge Request.

---

## 📋 Шаг 1: Создание аккаунта на GitLab

Если у вас ещё нет аккаунта:

1. Откройте: https://gitlab.com/users/sign_up
2. Зарегистрируйтесь (можно через GitHub)
3. Подтвердите email

---

## 📋 Шаг 2: Форк репозитория fdroiddata

1. Откройте: https://gitlab.com/fdroid/fdroiddata
2. Нажмите **Fork** (в правом верхнем углу)
3. Выберите ваш аккаунт
4. Дождитесь завершения форка

URL вашего форка:
```
https://gitlab.com/ВАШ_USERNAME/fdroiddata
```

---

## 📋 Шаг 3: Клонирование форка

```bash
cd ~

# Клонировать ваш форк
git clone https://gitlab.com/ВАШ_USERNAME/fdroiddata.git
cd fdroiddata

# Или через SSH (рекомендуется)
git clone git@gitlab.com:ВАШ_USERNAME/fdroiddata.git
cd fdroiddata
```

---

## 📋 Шаг 4: Добавление метаданных

### Вариант A: Ручное копирование

```bash
# Скопируйте файл метаданных
cp ~/fdroid-vless-vpn/org.fdroid.vlessvpn.yml \
   ~/fdroiddata/metadata/org.fdroid.vlessvpn.yml
```

### Вариант B: Создать файл вручную

```bash
# Создайте файл
nano ~/fdroiddata/metadata/org.fdroid.vlessvpn.yml

# Вставьте содержимое из org.fdroid.vlessvpn.yml
```

---

## 📋 Шаг 5: Проверка метаданных

```bash
cd ~/fdroiddata

# Проверьте синтаксис YAML
python3 -c "import yaml; yaml.safe_load(open('metadata/org.fdroid.vlessvpn.yml'))"

# Если ошибок нет - всё хорошо
```

---

## 📋 Шаг 6: Коммит изменений

```bash
cd ~/fdroiddata

# Добавить файл
git add metadata/org.fdroid.vlessvpn.yml

# Коммит
git commit -m "Add VLESS VPN (org.fdroid.vlessvpn)

VLESS VPN is a free and open-source VPN client with DPI bypass
capabilities for circumventing internet censorship.

Features:
- DPI Bypass with packet fragmentation and TLS mimicry
- Server scanner from public sources
- Auto-start on boot
- No logs, no tracking, no telemetry
- Privacy focused design

License: GPL-3.0-only
Source: https://github.com/zametkikostik/vless-vpn-fdroid"
```

---

## 📋 Шаг 7: Отправка в GitLab

```bash
# Отправить изменения
git push origin main
```

---

## 📋 Шаг 8: Создание Merge Request

1. Откройте ваш форк: https://gitlab.com/ВАШ_USERNAME/fdroiddata

2. Нажмите **Merge Request** (или **New Merge Request**)

3. Заполните:
   ```
   Source branch: main (ваш форк)
   Target branch: main (fdroid/fdroiddata)
   ```

4. Название MR:
   ```
   Add VLESS VPN (org.fdroid.vlessvpn)
   ```

5. Описание MR (используйте шаблон):

---

### 📝 Шаблон описания Merge Request

```markdown
## App Addition Request

### App Information
- **Name:** VLESS VPN
- **Package ID:** org.fdroid.vlessvpn
- **Version:** 1.0.0
- **License:** GPL-3.0-only
- **Source:** https://github.com/zametkikostik/vless-vpn-fdroid

### Description
VLESS VPN is a free and open-source VPN client with DPI bypass capabilities
for circumventing internet censorship.

### Features
- ✅ DPI Bypass with packet fragmentation and TLS mimicry
- ✅ Server scanner from public sources
- ✅ Auto-start on boot
- ✅ No logs, no tracking, no telemetry
- ✅ Privacy focused design

### Compliance Checklist
- [x] Free and Open Source (GPL-3.0-only)
- [x] No proprietary dependencies
- [x] No tracking or analytics
- [x] No Google Play Services required
- [x] Reproducible builds supported
- [x] Privacy focused (no data collection)
- [x] Source code publicly available
- [x] Metadata follows F-Droid format

### Anti-Features Check
- [x] NonFreeNet: false
- [x] Advertising: false
- [x] Tracking: false
- [x] Telemetry: false
- [x] ProprietaryDependencies: false
- [x] UpstreamNonFree: false

### Build Information
- **Minimum SDK:** 21
- **Target SDK:** 34
- **Build System:** Gradle
- **Language:** Kotlin

### Testing
- [x] App builds successfully
- [x] No obvious bugs or crashes
- [x] Permissions are appropriate for functionality

### Additional Notes
This app provides important functionality for users in regions with
internet censorship, helping them access information freely while
maintaining privacy and security.

All dependencies are from F-Droid compatible repositories.
No proprietary code or libraries are used.

### Related Issue
(если есть issue на forum.f-droid.org, укажите ссылку)

---

**Submitter:** Anonymous Developer  
**Date:** 2026-02-27
```

---

6. Нажмите **Create Merge Request**

---

## 📋 Шаг 9: После создания MR

### Автоматические проверки

F-Droid CI автоматически проверит:
- ✅ Синтаксис метаданных
- ✅ Доступность репозитория
- ✅ Версии и теги
- ✅ Структуру проекта

### Ручная проверка

Команда F-Droid проверит:
- ✅ Соответствие требованиям
- ✅ Отсутствие проприетарных зависимостей
- ✅ Репродуцируемость сборок
- ✅ Безопасность кода

---

## 📋 Шаг 10: Ответы на замечания

Если будут комментарии в MR:

1. Отвечайте оперативно
2. Вносите запрошенные изменения
3. Обновляйте метаданные при необходимости

```bash
# Внести изменения
cd ~/fdroiddata
nano metadata/org.fdroid.vlessvpn.yml

# Закоммитить и отправить
git add metadata/org.fdroid.vlessvpn.yml
git commit -m "Update metadata per review comments"
git push origin main
```

MR обновится автоматически!

---

## ⏱️ Ожидаемое время

| Этап | Время |
|------|-------|
| Автоматические проверки | 1-2 часа |
| Первичный просмотр | 1-3 дня |
| Тестирование сборки | 1-2 недели |
| Финальная проверка | 3-7 дней |
| **Итого** | **2-4 недели** |

---

## 🔗 Полезные ссылки

- **Ваш MR:** https://gitlab.com/fdroid/fdroiddata/-/merge_requests
- **F-Droid Data:** https://gitlab.com/fdroid/fdroiddata
- **Документация:** https://f-droid.org/docs/
- **Требования:** https://f-droid.org/docs/Inclusion_Policy/

---

## 📞 Поддержка

### Вопросы по MR
- Комментарии в самом Merge Request
- Email: data@f-droid.org

### Технические вопросы
- Форум: https://forum.f-droid.org/
- IRC: #fdroid on Libera.Chat

---

## ✅ Чеклист готовности

- [x] Аккаунт на GitLab создан
- [ ] Форк fdroiddata сделан
- [ ] Методанные скопированы
- [ ] YAML проверен
- [ ] Коммит создан
- [ ] Изменения отправлены
- [ ] Merge Request создан
- [ ] Описание заполнено по шаблону

---

## 🎉 После принятия MR

Ваше приложение появится:
1. В репозитории F-Droid
2. На сайте https://f-droid.org/
3. В приложении F-Droid для Android

Обновления будут приходить автоматически через систему тегов Git.

---

**Успехов с подачей в F-Droid! 🚀**
