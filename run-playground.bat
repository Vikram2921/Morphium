@echo off
REM Morphium Playground Server
REM Professional JSON Transformation DSL Development Environment

echo ================================================================================
echo  Morphium Playground Server
echo  Starting professional development environment...
echo ================================================================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven from https://maven.apache.org/
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK 11 or higher
    pause
    exit /b 1
)

echo Compiling and starting server...
echo.

mvn compile exec:java -Dexec.mainClass="com.morphium.playground.PlaygroundServer" -Dexec.args="%*"

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Server failed to start
    pause
    exit /b 1
)
