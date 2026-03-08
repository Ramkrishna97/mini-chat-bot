@echo off
setlocal enabledelayedexpansion

echo ========================================
echo    Local LLM Models Setup Script
echo ========================================
echo.

:: Set console colors
color 0A

:: Check if Ollama is installed
echo [1/5] Checking if Ollama is installed...
where ollama >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Ollama is not installed!
    echo.
    echo Please install Ollama first:
    echo 1. Visit https://ollama.ai/download/windows
    echo 2. Download and run the installer
    echo 3. After installation, restart this script
    echo.
    pause
    exit /b 1
)
echo [OK] Ollama is installed
echo.

:: Check if Ollama service is running
echo [2/5] Checking if Ollama service is running...
tasklist /FI "IMAGENAME eq ollama.exe" 2>NUL | find /I /N "ollama.exe">NUL
if %errorlevel% neq 0 (
    echo [INFO] Starting Ollama service...
    start /B ollama serve
    echo Waiting for Ollama to start...
    timeout /t 10 /nobreak >nul
) else (
    echo [OK] Ollama service is already running
)
echo.

:: List of models to pull
echo [3/5] Preparing to pull models...
set models[0]=qwen2.5-coder:3b
set models[1]=deepcoder:1.5b
set models[2]=llama2:latest
set models[3]=llama3.2:1b
set models[4]=deepseek-r1:1.5b
set models[5]=llama3.2:latest

set model_count=6
echo Found !model_count! models to download
echo.

:: Pull each model
echo [4/5] Downloading models (this may take a while)...
echo.

for /l %%i in (0,1,5) do (
    set "model=!models[%%i]!"

    echo ----------------------------------------
    echo Downloading: !model!
    echo ----------------------------------------

    :: Check if model already exists
    ollama list | find "!model!" >nul
    if !errorlevel! equ 0 (
        echo [SKIP] Model !model! already exists
    ) else (
        echo [DOWNLOAD] Pulling !model!...
        ollama pull !model!
        if !errorlevel! equ 0 (
            echo [OK] Successfully downloaded !model!
        ) else (
            echo [ERROR] Failed to download !model!
        )
    )
    echo.
)

:: Final status
echo ========================================
echo [5/5] Final Status
echo ========================================
echo.
echo Listing all available models:
ollama list
echo.

echo ========================================
echo Setup Complete!
echo ========================================
echo.
echo Your Spring Boot application will be available at:
echo - Web Interface: http://localhost:8008
echo - Swagger UI: http://localhost:8008/swagger-ui.html
echo - API Docs: http://localhost:8008/api-docs
echo.
echo To test the API:
echo curl http://localhost:8008/api/llm/models
echo.
pause