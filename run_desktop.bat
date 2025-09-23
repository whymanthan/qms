@echo off
echo Starting Queue Management Desktop Application...
echo.

REM Try to find Maven repository
set MAVEN_REPO=%USERPROFILE%\.m2\repository

REM Check if Maven repository exists
if not exist "%MAVEN_REPO%" (
    echo Maven repository not found at %MAVEN_REPO%
    echo Please ensure Maven is installed and dependencies are downloaded.
    echo You can run: mvn dependency:resolve
    pause
    exit /b 1
)

REM Build classpath with all necessary JARs
set CLASSPATH=target\classes
for /f %%i in ('dir /b /s "%MAVEN_REPO%\*.jar" 2^>nul') do set CLASSPATH=!CLASSPATH!;%%i

REM Run the desktop application
echo Starting application...
java -cp "%CLASSPATH%" com.queue.desktop.DesktopApp

if %ERRORLEVEL% neq 0 (
    echo.
    echo Application failed to start. Error code: %ERRORLEVEL%
    echo.
    echo Possible solutions:
    echo 1. Install Maven and run: mvn spring-boot:run
    echo 2. Use your IDE to run the application
    echo 3. Check if all dependencies are downloaded
)

pause

