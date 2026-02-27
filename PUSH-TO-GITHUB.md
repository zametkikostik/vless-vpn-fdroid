# 🚀 Инструкция по отправке в GitHub

## Проблема
GitHub больше не поддерживает аутентификацию по паролю. Нужно использовать:
- **SSH ключи** (рекомендуется) ✅
- **Personal Access Token**

---

## ✅ Способ 1: Через веб-интерфейс (Простой)

### Шаг 1: Создайте репозиторий на GitHub

1. Откройте: https://github.com/new

2. Заполните:
   ```
   Repository name: vless-vpn-fdroid
   Description: VLESS VPN for F-Droid - Open-source VPN with DPI bypass
   Visibility: ✓ Public
   ✗ Не инициализировать README, .gitignore, или лицензию
   ```

3. Нажмите **"Create repository"**

### Шаг 2: Отправьте код

```bash
cd /home/kostik/fdroid-vless-vpn

# Убедитесь что remote настроен на SSH
git remote set-url origin git@github.com:zametkikostik/vless-vpn-fdroid.git

# Отправьте код
git push -u origin main
```

**Готово!** ✅

---

## 🔧 Способ 2: Через GitHub CLI (Автоматический)

### Авторизация GitHub CLI

```bash
# Запустить авторизацию
gh auth login

# Выбрать:
# 1. GitHub.com
# 2. SSH
# 3. Использовать существующий SSH ключ
# 4. Следовать инструкциям в браузере
```

### Создание репозитория

```bash
cd /home/kostik/fdroid-vless-vpn

# Создать и отправить
gh repo create zametkikostik/vless-vpn-fdroid \
  --public \
  --description "VLESS VPN for F-Droid - Open-source VPN with DPI bypass" \
  --source=. \
  --remote=origin \
  --push
```

---

## 🔑 Проверка SSH ключа

Ваш SSH ключ уже настроен:
```
~/.ssh/id_ed25519_git
```

Проверка:
```bash
ssh -T git@github.com
```

Должно вывести:
```
Hi zametkikostik! You've successfully authenticated...
```

---

## 📝 Быстрая команда

Просто выполните:

```bash
cd /home/kostik/fdroid-vless-vpn
./push-to-github.sh
```

Скрипт покажет пошаговую инструкцию.

---

## 🎯 После создания репозитория

1. **Проверьте что код отправлен:**
   ```bash
   git status
   ```

2. **Откройте репозиторий:**
   https://github.com/zametkikostik/vless-vpn-fdroid

3. **Убедитесь что файлы на месте:**
   - app/src/main/kotlin/
   - fastlane/metadata/android/
   - README.md
   - build.gradle

---

## 📞 Если возникли проблемы

### Ошибка: "Permission denied (publickey)"

```bash
# Проверьте SSH ключ
ssh-add ~/.ssh/id_ed25519_git

# Добавьте ключ в GitHub:
# https://github.com/settings/keys
cat ~/.ssh/id_ed25519_git.pub
```

### Ошибка: "Repository not found"

Создайте репозиторий вручную:
https://github.com/new

### Ошибка: "Authentication failed"

Используйте SSH вместо HTTPS:
```bash
git remote set-url origin git@github.com:USERNAME/REPO.git
```

---

## ✅ Проверка успеха

После успешной отправки:

1. Откройте https://github.com/zametkikostik/vless-vpn-fdroid
2. Вы должны увидеть ваш код
3. Включите GitHub Actions для сборок

---

**Успехов! 🎉**
