@echo off
cd /d "%~dp0"
echo Compiling RPG Game...
javac -d bin src/rpg/*.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)
echo Compilation successful!
echo.
echo Starting RPG Game...
echo Current directory: %CD%
echo.
java -cp bin rpg.RPGGame
pause

