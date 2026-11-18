@echo off
setlocal
cd /d "%~dp0"
echo ==================================
echo    PrismOS 1.0 APK Builder
 echo ==================================
where java >nul 2>nul
if errorlevel 1 (echo ERROR: Install JDK 17 and set JAVA_HOME. & pause & exit /b 1)
where gradle >nul 2>nul
if errorlevel 1 (echo ERROR: Gradle is not installed. Use Android Studio or GitHub Actions; see README. & pause & exit /b 1)
call gradle --version
call gradle :app:assembleDebug --stacktrace > build-log.txt 2>&1
if errorlevel 1 (echo BUILD FAILED. Open build-log.txt & pause & exit /b 1)
echo BUILD SUCCESS: app\build\outputs\apk\debug\app-debug.apk
pause
