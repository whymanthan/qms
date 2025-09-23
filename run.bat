@echo off
echo 🏥 Hospital Queue Management System
echo ===================================
echo.
echo Compiling Java files...
javac *.java
if %errorlevel% neq 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)
echo ✅ Compilation successful!
echo.
echo Starting application...
java HospitalQueueApp
pause
