# 📦 F-Droid Submission Guide

## VLESS VPN - F-Droid Inclusion Request

### ✅ Pre-submission Checklist

Before submitting to F-Droid, ensure:

- [x] **No proprietary dependencies** - All dependencies are F-Droid compatible
- [x] **No tracking/analytics** - No Google Play Services, Firebase, etc.
- [x] **Open source license** - GPL-3.0-only
- [x] **Reproducible builds** - Build process is deterministic
- [x] **Privacy focused** - No user data collection
- [x] **Clean code** - Well-documented, follows Kotlin conventions
- [x] **Metadata complete** - fastlane metadata structure ready

---

## 📋 Step-by-Step Submission Process

### Step 1: Prepare Your Repository

Ensure your Git repository is clean and public:

```bash
# Verify repository is public
git remote -v

# Check for any sensitive files
git ls-files | grep -E "(keystore|password|secret)"

# Should return nothing!
```

### Step 2: Create F-Droid Metadata

Metadata files are already in place:
```
fastlane/metadata/android/
├── en-US/
│   ├── title.txt
│   ├── short_description.txt
│   └── full_description.txt
└── contact.txt
```

### Step 3: Test Build Locally

```bash
cd /home/kostik/fdroid-vless-vpn

# Clean build
./gradlew clean

# Build release
./gradlew assembleRelease

# Verify APK
ls -lh app/build/outputs/apk/release/
```

### Step 4: Submit to F-Droid

#### Option A: Request via F-Droid Forum

1. Visit: https://forum.f-droid.org/
2. Create account
3. Go to "App Requests" category
4. Create new topic with:
   - App name: VLESS VPN
   - Description: Open-source VPN with DPI bypass
   - Source code URL: Your GitHub repo
   - License: GPL-3.0-only

#### Option B: Submit via F-Droid Data Repository

1. Fork: https://gitlab.com/fdroid/fdroiddata
2. Add metadata file: `metadata/org.fdroid.vlessvpn.yml`
3. Submit merge request

Example metadata file:

```yaml
Categories:
  - Internet
  - Security

License: GPL-3.0-only
Author: Anonymous Developer
SourceCode: https://github.com/zametkikostik/vless-vpn-client
IssueTracker: https://github.com/zametkikostik/vless-vpn-client/issues

AutoName: VLESS VPN

RepoType: git
Repo: https://github.com/zametkikostik/vless-vpn-client

Builds:
  - versionName: 1.0.0
    versionCode: 1
    commit: v1.0.0
    subdir: fdroid-vless-vpn
    gradle:
      - yes
    output: app/build/outputs/apk/release/app-release-unsigned.apk

AutoUpdateMode: Version
UpdateCheckMode: Tags
UpdateCheckData: app/build.gradle|versionCode\s+(\d+)|.|versionName ['"]([0-9.]+)['"]

CurrentVersion: 1.0.0
CurrentVersionCode: 1
```

#### Option C: Open Source App with Inclusion Request

1. Email: `data@f-droid.org`
2. Subject: "Inclusion Request: VLESS VPN"
3. Include:
   - App description
   - Source code URL
   - License information
   - Confirmation of no proprietary dependencies

---

## 📝 F-Droid Metadata Template

Create file: `metadata/org.fdroid.vlessvpn.yml`

```yaml
Categories:
  - Internet
  - Security

License: GPL-3.0-only
Author: Anonymous Developer
SourceCode: https://github.com/zametkikostik/vless-vpn-client
IssueTracker: https://github.com/zametkikostik/vless-vpn-client/issues
Changelog: https://github.com/zametkikostik/vless-vpn-client/blob/main/CHANGELOG.md

AutoName: VLESS VPN

Description: |
  VLESS VPN is a free and open-source VPN client with DPI bypass capabilities
  for circumventing internet censorship.

  Features:
  - DPI Bypass with packet fragmentation and TLS mimicry
  - Server scanner from public sources
  - Auto-start on boot
  - No logs, no tracking, no telemetry
  - Privacy focused design

  Technical Details:
  - VLESS protocol with Reality encryption
  - TLS 1.3 fingerprint mimicry
  - Adaptive strategy switching
  - No proprietary dependencies

Summary: Open-source VPN with DPI bypass

Description: |
  VLESS VPN - Circumvent censorship with advanced DPI bypass

  Multi-layer evasion including packet fragmentation, padding, and TLS mimicry.

  Privacy Policy: This app does not collect any personal data.

RepoType: git
Repo: https://github.com/zametkikostik/vless-vpn-client

Builds:
  - versionName: 1.0.0
    versionCode: 1
    commit: v1.0.0
    subdir: fdroid-vless-vpn
    gradle:
      - yes
    output: app/build/outputs/apk/release/app-release-unsigned.apk
    rm:
      - fastlane/metadata/android/contact.txt

AutoUpdateMode: Version
UpdateCheckMode: Tags
UpdateCheckData: app/build.gradle|versionCode\s+(\d+)|.|versionName ['"]([0-9.]+)['"]

CurrentVersion: 1.0.0
CurrentVersionCode: 1

AllowedAPKSigningKeys: <your-signing-key-fingerprint>

ManifestEntries:
  - UsesCleartextTraffic: false
  - RequestedPermissions:
    - android.permission.INTERNET
    - android.permission.ACCESS_NETWORK_STATE
    - android.permission.FOREGROUND_SERVICE
    - android.permission.POST_NOTIFICATIONS
    - android.permission.RECEIVE_BOOT_COMPLETED
    - android.permission.WAKE_LOCK

Provides:
  - org.fdroid.vlessvpn

AntiFeatures:
  - NonFreeNet: false
  - Advertising: false
  - Tracking: false
  - Telemetry: false
  - ProprietaryDependencies: false

Screenshots:
  - https://raw.githubusercontent.com/zametkikostik/vless-vpn-client/main/fastlane/metadata/android/en-US/images/phoneScreens/1.png
  - https://raw.githubusercontent.com/zametkikostik/vless-vpn-client/main/fastlane/metadata/android/en-US/images/phoneScreens/2.png

Icon: https://raw.githubusercontent.com/zametkikostik/vless-vpn-client/main/app/src/main/res/mipmap-hdpi/ic_launcher.png

FeaturedGraphic: https://raw.githubusercontent.com/zametkikostik/vless-vpn-client/main/fastlane/metadata/android/en-US/images/featuredGraphic.png
```

---

## 🔐 Signing Key for F-Droid

F-Droid will sign the app with their own key. For development:

```bash
# Generate signing key (keep secure!)
keytool -genkey -v \
  -keystore vless-vpn-fdroid.keystore \
  -alias fdroid \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000

# Get key fingerprint for F-Droid metadata
keytool -list -v -keystore vless-vpn-fdroid.keystore -alias fdroid
```

**Important:** Never commit keystore files to Git!

---

## 📸 Screenshots

Add screenshots to:
```
fastlane/metadata/android/en-US/images/phoneScreens/
├── 1.png
├── 2.png
└── 3.png
```

Requirements:
- PNG or JPG format
- Minimum 320px width
- Maximum 3840px width
- No text overlays
- Show actual app UI

---

## ✅ F-Droid Requirements Compliance

### No Proprietary Dependencies
✅ All dependencies are open-source:
- AndroidX libraries
- Kotlin coroutines
- Material Design components

### No Tracking/Analytics
✅ No Google Play Services
✅ No Firebase
✅ No analytics SDKs
✅ No crash reporting services

### Reproducible Builds
✅ Gradle build process
✅ Deterministic version codes
✅ No timestamps in builds
✅ Clean build environment

### Privacy
✅ No user data collection
✅ No internet permission except for VPN
✅ No external API calls for tracking
✅ All processing done locally

### Open Source License
✅ GPL-3.0-only
✅ License file included
✅ Copyright notices in source files

---

## 📧 Contact Information for F-Droid

**For F-Droid team:**

- **App Name:** VLESS VPN
- **Package:** org.fdroid.vlessvpn
- **Source:** https://github.com/zametkikostik/vless-vpn-client
- **License:** GPL-3.0-only
- **Maintainer:** Anonymous (privacy-focused)

**Note:** Personal contact information is intentionally minimal to protect developer privacy. This is consistent with the app's privacy-focused design.

For issues and support, please use GitHub Issues.

---

## 🚀 After Submission

1. **Wait for review** - F-Droid team will review your submission
2. **Address feedback** - They may request changes
3. **Build testing** - F-Droid will test reproducible builds
4. **Inclusion** - Once approved, app will be added to F-Droid repo

Typical timeline: 2-4 weeks

---

## 📞 Support

For questions about F-Droid submission:
- Forum: https://forum.f-droid.org/
- Email: data@f-droid.org
- Documentation: https://f-droid.org/docs/

---

**Good luck with your F-Droid submission! 🎉**
