---
name: 📦 APK Build Request
about: Request to build APK for release
title: '📦 Build APK v1.0.0'
labels: 'build, release, apk'
---

## APK Build Request

### App Information
- **Name:** VLESS VPN
- **Version:** 1.0.0
- **Package:** org.fdroid.vlessvpn
- **License:** GPL-3.0-only

### Build Status

- [ ] Flutter environment setup
- [ ] Dependencies installed
- [ ] APK built successfully
- [ ] APK tested
- [ ] Uploaded to GitHub Releases
- [ ] Uploaded to F-Droid

### Build Instructions

```bash
cd ~/fdroid-vless-vpn

# Setup Flutter (if needed)
export PATH="$PATH:/tmp/flutter/bin"

# Get dependencies
flutter pub get

# Build release APK
flutter build apk --release

# Copy to releases folder
cp build/app/outputs/flutter-apk/app-release.apk releases/vless-vpn-1.0.0.apk
```

### Expected Output

```
releases/
└── vless-vpn-1.0.0.apk  (~30-50 MB)
```

### GitHub Release

After build, create release:

```bash
gh release create v1.0.0 \
  releases/vless-vpn-1.0.0.apk \
  --title "VLESS VPN v1.0.0" \
  --notes "Production release with DPI bypass"
```

### Checklist

- [x] Source code ready
- [x] Documentation complete
- [x] Metadata for F-Droid ready
- [ ] APK built
- [ ] Release published
- [ ] F-Droid submission

### Notes

This APK will be used for:
1. Direct download from GitHub Releases
2. F-Droid submission
3. Testing on Android devices

---

**Submitter:** Anonymous Developer  
**Date:** 2026-02-27
