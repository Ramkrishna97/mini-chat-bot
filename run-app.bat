@echo off
setlocal enabledelayedexpansion

:: Set console colors
color 0B

echo ========================================
echo    Local LLM Application Launcher
echo         Port: 8008
echo ========================================
echo.

:: Check Java version
echo [1/5] Checking Java installation...
java -version 2>&1 | findstr "version" > java_version.txt
set /p java_version=<java_version.txt
echo !java_version!
del java_version.txt

java -version 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH
    echo Please install Java 17 or higher from: https://adoptium.net/
    pause
    exit /b 1
)
echo [OK] Java is installed
echo.

:: Check Maven
echo [2/5] Checking Maven installation...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Maven is not installed or not in PATH
    echo Please install Maven from: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)
echo [OK] Maven is installed
echo.

:: Check if port 8008 is available
echo [3/5] Checking if port 8008 is available...
netstat -ano | findstr :8008 >nul
if %errorlevel% equ 0 (
    echo [WARNING] Port 8008 is already in use!
    echo.
    echo Please close the application using port 8008 or:
    echo - Change the port in application.properties
    echo - Stop the existing process
    echo.
    choice /C YN /M "Do you want to continue anyway"
    if !errorlevel! equ 2 exit /b 1
) else (
    echo [OK] Port 8008 is available
)
echo.

:: Run the model setup script
echo [4/5] Setting up LLM models...
echo.
call src\main\resources\scripts\setup-models.bat
echo.

:: Build and run the application
echo [5/5] Starting Spring Boot application on port 8008...
echo.
echo Application will be available at: http://localhost:8008
echo Swagger UI: http://localhost:8008/swagger-ui.html
echo.
echo Press Ctrl+C to stop the application
echo.

:: Clean and package
echo Building application...
call mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo [ERROR] Build failed!
    pause
    exit /b 1
)

:: Run the application
echo Starting application...
java -jar target\local-llm-app-1.0.0.jar

pause