@echo off
echo call java/standalone/BUILD_saj_builtintopics_sub.bat %*
call java/standalone/BUILD_saj_builtintopics_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_builtintopics_sub.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
