# VLESS VPN for F-Droid

**Open-source VPN client with DPI bypass capabilities**

## Description

VLESS VPN is a free and open-source VPN client designed for circumventing internet censorship through advanced DPI (Deep Packet Inspection) bypass techniques.

### Key Features

- 🔒 **DPI Bypass** - Multi-layer evasion including packet fragmentation, padding, and TLS 1.3 mimicry
- 🛡️ **Censorship Resistance** - Specifically designed to work in restricted networks
- 🌐 **Server Scanner** - Automatically discovers and tests public VLESS servers from open sources
- 🚀 **Auto-start** - Optional automatic connection on device boot
- 📱 **Privacy First** - No logs, no tracking, no telemetry, no personal data collection
- 🎨 **Modern UI** - Material Design 3 interface

## Technical Specifications

### Protocol
- **VLESS** with Reality encryption
- **TLS 1.3** fingerprint mimicry (Chrome 120+)
- **Packet Fragmentation** (50-200 bytes)
- **Padding** with random data
- **Adaptive Strategy Switching**

### Build Information
- **Minimum SDK:** 21 (Android 5.0)
- **Target SDK:** 34 (Android 14)
- **Language:** Kotlin
- **Build Tools:** Gradle 8.0+
- **Kotlin Version:** 1.9.0

### Dependencies (All Open-Source)
```gradle
androidx.core:core-ktx:1.12.0
androidx.appcompat:appcompat:1.6.1
com.google.android.material:material:1.11.0
androidx.constraintlayout:constraintlayout:2.1.4
androidx.lifecycle:lifecycle-runtime-ktx:2.7.0
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3
```

**No proprietary dependencies. No Google Play Services required.**

## Permissions

| Permission | Purpose |
|------------|---------|
| `INTERNET` | Required for VPN functionality |
| `ACCESS_NETWORK_STATE` | Monitor connection status |
| `FOREGROUND_SERVICE` | Run VPN service in background |
| `POST_NOTIFICATIONS` | Show connection status notifications |
| `RECEIVE_BOOT_COMPLETED` | Optional auto-start on boot |
| `WAKE_LOCK` | Keep VPN running during sleep |

## Privacy Policy

**This app does not collect, store, or transmit any personal data.**

- No analytics
- No tracking
- No telemetry
- No user identification
- All VPN traffic goes through user-selected servers
- No data shared with third parties

## Installation

### From F-Droid (Pending)
1. Open F-Droid app
2. Search for "VLESS VPN"
3. Install

### Build from Source
```bash
git clone https://github.com/zametkikostik/vless-vpn-client.git
cd fdroid-vless-vpn
./gradlew assembleRelease
```

APK will be in `app/build/outputs/apk/release/`

## Usage

1. **Open the app**
2. **Tap "Scan Servers"** to find available servers
3. **Select a server** from the list
4. **Tap "Connect"** to establish VPN connection
5. **Enable Auto-start** in settings for automatic connection on boot

## Server Sources

The app scans servers from public GitHub repositories:
- igareck/vpn-configs-for-russia (White/Black lists)
- Other public VLESS aggregators

**Note:** Server availability and performance may vary.

## DPI Bypass Techniques

### 1. Packet Fragmentation
- Splits packets into small fragments (50-200 bytes)
- Prevents DPI systems from analyzing complete packets

### 2. Padding
- Adds random data to packets
- Changes packet size and pattern

### 3. TLS Mimicry
- Mimics Chrome 120+ TLS fingerprint
- Uses standard TLS 1.3 handshake
- Indistinguishable from regular HTTPS traffic

### 4. Adaptive Switching
- Automatically switches strategies if blocked
- Multiple fallback mechanisms

## Contributing

Contributions are welcome! Please submit pull requests or issues on GitHub.

### Development Guidelines
- Follow Kotlin coding conventions
- No proprietary dependencies
- Maintain F-Droid compatibility
- Include tests for new features
- Update documentation

## Build Reproducibility

This app supports reproducible builds for F-Droid:

```bash
# Set up environment
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export ANDROID_HOME=/opt/android-sdk

# Build
./gradlew clean assembleRelease

# Verify hash
sha256sum app/build/outputs/apk/release/*.apk
```

## Known Issues

- Server scanning may be slow on older devices
- Some public servers may be unreliable
- Connection speed depends on server location

## Roadmap

- [ ] WireGuard protocol support
- [ ] Custom server import
- [ ] Split tunneling
- [ ] Connection statistics
- [ ] Multiple VPN profiles

## License

**GPL-3.0-only**

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

## Disclaimer

This app is for educational and research purposes only. Use at your own risk. The developers are not responsible for any misuse of this software or any consequences arising from its use.

## Support

- **Issues:** https://github.com/zametkikostik/vless-vpn-client/issues
- **Source:** https://github.com/zametkikostik/vless-vpn-client

## Version History

### 1.0.0 (Initial Release)
- VLESS protocol support
- DPI bypass functionality
- Server scanner
- Auto-start capability
- Material Design UI
- F-Droid compatible

---

**Built with ❤️ for privacy and freedom**
