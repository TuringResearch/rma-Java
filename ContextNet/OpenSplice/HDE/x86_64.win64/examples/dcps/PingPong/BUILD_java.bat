@echo off
echo call java/corba/BUILD_cj_pingpong.bat %*
call java/corba/BUILD_cj_pingpong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/corba/BUILD_cj_pingpong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/corba/BUILD_cj_pingpong_ping.bat %*
call java/corba/BUILD_cj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/corba/BUILD_cj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/corba/BUILD_cj_pingpong_pong.bat %*
call java/corba/BUILD_cj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/corba/BUILD_cj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/standalone/BUILD_saj_pingpong_types.bat %*
call java/standalone/BUILD_saj_pingpong_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_pingpong_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/standalone/BUILD_saj_pingpong_ping.bat %*
call java/standalone/BUILD_saj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/standalone/BUILD_saj_pingpong_pong.bat %*
call java/standalone/BUILD_saj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
