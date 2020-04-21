@echo off
echo call java/standalone/BUILD_saj_waitset_types.bat %*
call java/standalone/BUILD_saj_waitset_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_waitset_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/standalone/BUILD_saj_waitset_pub.bat %*
call java/standalone/BUILD_saj_waitset_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_waitset_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/standalone/BUILD_saj_waitset_sub.bat %*
call java/standalone/BUILD_saj_waitset_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_waitset_sub.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
