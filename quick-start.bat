@echo off
title Local LLM App - Port 8008
color 0A

echo Starting Local LLM Application on port 8008...
echo.

:: Start Ollama if not running
tasklist /FI "IMAGENAME eq ollama.exe" 2>NUL | find /I /N "ollama.exe">NUL
if %errorlevel% neq 0 (
    echo Starting Ollama service...
    start /B ollama serve
    timeout /t 5 /nobreak >nul
)

:: Run the application
echo Launching Spring Boot...
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8008

pause