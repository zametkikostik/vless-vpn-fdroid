#!/bin/bash
# Создание иконок для Android приложения

set -e

APP_DIR="app/src/main/res"

echo "🎨 Создание иконок..."

# Создаём простые XML иконки для всех плотностей
for density in mdpi hdpi xhdpi xxhdpi xxxhdpi; do
    mkdir -p "$APP_DIR/mipmap-$density"
    
    # Создаём XML vector drawable
    cat > "$APP_DIR/mipmap-$density/ic_launcher.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path
        android:fillColor="#2E7D32"
        android:pathData="M54,54m-40,0a40,40 0,1 1,80 0a40,40 0,1 1,-80 0"/>
    <path
        android:fillColor="#FFFFFF"
        android:pathData="M54,36c-9.94,0 -18,8.06 -18,18s8.06,18 18,18 18,-8.06 18,-18 -8.06,-18 -18,-18zM54,48c3.31,0 6,2.69 6,6s-2.69,6 -6,6 -6,-2.69 -6,-6 2.69,-6 6,-6z"/>
</vector>
EOF

    cat > "$APP_DIR/mipmap-$density/ic_launcher_round.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path
        android:fillColor="#2E7D32"
        android:pathData="M54,54m-40,0a40,40 0,1 1,80 0a40,40 0,1 1,-80 0"/>
    <path
        android:fillColor="#FFFFFF"
        android:pathData="M54,36c-9.94,0 -18,8.06 -18,18s8.06,18 18,18 18,-8.06 18,-18 -8.06,-18 -18,-18zM54,48c3.31,0 6,2.69 6,6s-2.69,6 -6,6 -6,-2.69 -6,-6 2.69,-6 6,-6z"/>
</vector>
EOF

    echo "✅ $density"
done

# Обновляем anydpi-v26
cat > "$APP_DIR/mipmap-anydpi-v26/ic_launcher.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background"/>
    <foreground android:drawable="@drawable/ic_launcher_foreground"/>
</adaptive-icon>
EOF

cat > "$APP_DIR/mipmap-anydpi-v26/ic_launcher_round.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background"/>
    <foreground android:drawable="@drawable/ic_launcher_foreground"/>
</adaptive-icon>
EOF

# Создаём drawable
mkdir -p "$APP_DIR/drawable"
cat > "$APP_DIR/drawable/ic_launcher_foreground.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path
        android:fillColor="#FFFFFF"
        android:pathData="M54,36c-9.94,0 -18,8.06 -18,18s8.06,18 18,18 18,-8.06 18,-18 -8.06,-18 -18,-18zM54,48c3.31,0 6,2.69 6,6s-2.69,6 -6,6 -6,-2.69 -6,-6 2.69,-6 6,-6z"/>
</vector>
EOF

# Создаём цвета
cat > "$APP_DIR/values/colors.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="ic_launcher_background">#2E7D32</color>
    <color name="primary">#2E7D32</color>
    <color name="primary_dark">#1B5E20</color>
    <color name="accent">#4CAF50</color>
</resources>
EOF

echo "✅ Иконки созданы!"
