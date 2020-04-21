@echo off
echo call standalone/BUILD_saj_durability_types.bat %*
call standalone/BUILD_saj_durability_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_durability_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_durability_pub.bat %*
call standalone/BUILD_saj_durability_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_durability_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_durability_sub.bat %*
call standalone/BUILD_saj_durability_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_durability_sub.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
