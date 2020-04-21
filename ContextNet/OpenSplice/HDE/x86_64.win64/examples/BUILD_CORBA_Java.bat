@echo off
echo call dcps/HelloWorld/java/corba/BUILD_cj_helloworld_types.bat %*
call dcps/HelloWorld/java/corba/BUILD_cj_helloworld_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/HelloWorld/java/corba/BUILD_cj_helloworld_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/HelloWorld/java/corba/BUILD_cj_helloworld_pub.bat %*
call dcps/HelloWorld/java/corba/BUILD_cj_helloworld_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/HelloWorld/java/corba/BUILD_cj_helloworld_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/HelloWorld/java/corba/BUILD_cj_helloworld_sub.bat %*
call dcps/HelloWorld/java/corba/BUILD_cj_helloworld_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/HelloWorld/java/corba/BUILD_cj_helloworld_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/PingPong/java/corba/BUILD_cj_pingpong.bat %*
call dcps/PingPong/java/corba/BUILD_cj_pingpong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/PingPong/java/corba/BUILD_cj_pingpong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/PingPong/java/corba/BUILD_cj_pingpong_ping.bat %*
call dcps/PingPong/java/corba/BUILD_cj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/PingPong/java/corba/BUILD_cj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/PingPong/java/corba/BUILD_cj_pingpong_pong.bat %*
call dcps/PingPong/java/corba/BUILD_cj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/PingPong/java/corba/BUILD_cj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Tutorial/java/corba/BUILD_cj_tutorial_types.bat %*
call dcps/Tutorial/java/corba/BUILD_cj_tutorial_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Tutorial/java/corba/BUILD_cj_tutorial_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Tutorial/java/corba/BUILD_cj_tutorial_chatter.bat %*
call dcps/Tutorial/java/corba/BUILD_cj_tutorial_chatter.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Tutorial/java/corba/BUILD_cj_tutorial_chatter.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Tutorial/java/corba/BUILD_cj_tutorial_messageboard.bat %*
call dcps/Tutorial/java/corba/BUILD_cj_tutorial_messageboard.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Tutorial/java/corba/BUILD_cj_tutorial_messageboard.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Tutorial/java/corba/BUILD_cj_tutorial_userload.bat %*
call dcps/Tutorial/java/corba/BUILD_cj_tutorial_userload.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Tutorial/java/corba/BUILD_cj_tutorial_userload.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
