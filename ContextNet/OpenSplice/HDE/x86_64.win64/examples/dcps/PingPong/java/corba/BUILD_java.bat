@echo off
echo call BUILD_cj_pingpong.bat %*
call BUILD_cj_pingpong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_cj_pingpong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call BUILD_cj_pingpong_ping.bat %*
call BUILD_cj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_cj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call BUILD_cj_pingpong_pong.bat %*
call BUILD_cj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_cj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
