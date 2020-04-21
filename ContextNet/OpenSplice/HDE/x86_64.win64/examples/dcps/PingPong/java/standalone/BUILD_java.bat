@echo off
echo call BUILD_saj_pingpong_types.bat %*
call BUILD_saj_pingpong_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_saj_pingpong_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call BUILD_saj_pingpong_ping.bat %*
call BUILD_saj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_saj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call BUILD_saj_pingpong_pong.bat %*
call BUILD_saj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_saj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
