# PrismOS 1.0 — Infinix Hot 10i PR652B launcher preview

**What is implemented:** Android launcher/home intent, iOS-inspired gradient clock card, rounded quick-launch tiles, application list with launch actions, dock, light/dark switch, shortcuts to actual Android settings. No root required. Android 11 compatible by minimum SDK.

**What is NOT implemented:** A flashable ROM, replacement lock screen, iOS, real glass blur, persistent theme settings, hardware toggles, XOS removal, system settings replacement, bootloader unlocking. No APK has been compiled or tested on the device in this delivery.

## Easiest Windows 11 build: Android Studio
1. Install Android Studio (https://developer.android.com/studio).
2. Extract this ZIP. In Android Studio choose **Open** and select the project folder.
3. Allow Gradle sync, Android SDK 35 installation, and JDK 17 setup (Android Studio bundled JDK may be suitable).
4. Choose **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. Find `app/build/outputs/apk/debug/app-debug.apk` and copy it to your phone.
6. Install the APK. On Android choose PrismOS as your Home app when prompted (or Settings > Default apps > Home app).
7. To go back to XOS, change the default Home app to the original launcher, then uninstall PrismOS if desired.

## Alternative: GitHub Actions (no local Gradle installation)
1. Create a GitHub repository and upload the *contents* of this project (including `.github/workflows/build-apk.yml`).
2. In GitHub open **Actions > Build PrismOS APK > Run workflow**.
3. After successful completion, download the `PrismOS-1.0-debug-APK` artifact and extract the APK.

## Windows batch file
`BUILD_WINDOWS11.bat` uses an already installed Gradle 8.7 and JDK 17. It writes `build-log.txt` if Gradle starts. Android SDK 35 must also be installed/configured. The batch file does not silently install third-party programs.

## Safety
This is an installable Android application project, **not** a flashable ROM. Do not flash the APK to any system partition. The PR652B bootloader is reported locked; OEM unlocking being enabled does not change that.
