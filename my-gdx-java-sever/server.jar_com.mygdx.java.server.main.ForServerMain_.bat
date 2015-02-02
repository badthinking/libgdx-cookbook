@echo off
setlocal enabledelayedexpansion



for /f "tokens=1,2 delims=_" %%a in ("%~n0") do (
    set JAR_NAME=%%a
    set MAIN_CLASS=%%b
)


echo %JAR_NAME%
echo %MAIN_CLASS%

set WK_DIR=%~dp0

echo %WK_DIR%

set LIB_DIR=lib

set LIB_TMP_DIR=bin


goto jar

:unjar

mkdir %WK_DIR%%LIB_TMP_DIR%
cd /d %WK_DIR%%LIB_DIR%

for /R %%s in (*.jar) do (
    cd /d %WK_DIR%%LIB_TMP_DIR%
    echo %%s 
   "%JAVA_HOME%\bin\jar" xvf %%s
    if not %errorlevel% == 0 (
        echo jar xvf error!
        pause
        rd /q /s %WK_DIR%%LIB_TMP_DIR%
        exit -1
    )
    rd /q /s %WK_DIR%%LIB_TMP_DIR%\META-INF
    cd /d %WK_DIR%%LIB_DIR%
) 
exit


:jar
cd /d %WK_DIR%

if not "!MAIN_CLASS!" == "" (
    "%JAVA_HOME%\bin\jar" -cvfe %JAR_NAME% %MAIN_CLASS%  -C %LIB_TMP_DIR% .
    if not %errorlevel% == 0 (
        echo jar cvfe error!
        pause
        rd /q /s %WK_DIR%%LIB_TMP_DIR%
        exit -1
    )
) else (
    "%JAVA_HOME%\bin\jar" -cvf %JAR_NAME% -C %LIB_TMP_DIR% .
    if not %errorlevel% == 0 (
        echo jar cvf error!
        pause
        rd /q /s %WK_DIR%%LIB_TMP_DIR%
        exit -1
    )
)

cd /d %WK_DIR%
rem rd /q /s %WK_DIR%%LIB_TMP_DIR%

if not %errorlevel% == 0 (
    echo jar error!
    pause
    exit -1
)
exit