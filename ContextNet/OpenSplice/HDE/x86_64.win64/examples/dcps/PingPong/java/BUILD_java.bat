@echo off
echo call corba/BUILD_cj_pingpong.bat %*
call corba/BUILD_cj_pingpong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_pingpong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call corba/BUILD_cj_pingpong_ping.bat %*
call corba/BUILD_cj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call corba/BUILD_cj_pingpong_pong.bat %*
call corba/BUILD_cj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_pingpong_types.bat %*
call standalone/BUILD_saj_pingpong_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_pingpong_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_pingpong_ping.bat %*
call standalone/BUILD_saj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_pingpong_pong.bat %*
call standalone/BUILD_saj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
