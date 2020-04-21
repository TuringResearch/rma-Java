@echo off
echo call BuiltInTopics/java/standalone/BUILD_saj_builtintopics_sub.bat %*
call BuiltInTopics/java/standalone/BUILD_saj_builtintopics_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BuiltInTopics/java/standalone/BUILD_saj_builtintopics_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_types.bat %*
call ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_pub.bat %*
call ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_sub.bat %*
call ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Durability/java/standalone/BUILD_saj_durability_types.bat %*
call Durability/java/standalone/BUILD_saj_durability_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Durability/java/standalone/BUILD_saj_durability_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Durability/java/standalone/BUILD_saj_durability_pub.bat %*
call Durability/java/standalone/BUILD_saj_durability_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Durability/java/standalone/BUILD_saj_durability_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Durability/java/standalone/BUILD_saj_durability_sub.bat %*
call Durability/java/standalone/BUILD_saj_durability_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Durability/java/standalone/BUILD_saj_durability_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call HelloWorld/java/corba/BUILD_cj_helloworld_types.bat %*
call HelloWorld/java/corba/BUILD_cj_helloworld_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building HelloWorld/java/corba/BUILD_cj_helloworld_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call HelloWorld/java/corba/BUILD_cj_helloworld_pub.bat %*
call HelloWorld/java/corba/BUILD_cj_helloworld_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building HelloWorld/java/corba/BUILD_cj_helloworld_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call HelloWorld/java/corba/BUILD_cj_helloworld_sub.bat %*
call HelloWorld/java/corba/BUILD_cj_helloworld_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building HelloWorld/java/corba/BUILD_cj_helloworld_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call HelloWorld/java/standalone/BUILD_saj_helloworld_types.bat %*
call HelloWorld/java/standalone/BUILD_saj_helloworld_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building HelloWorld/java/standalone/BUILD_saj_helloworld_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call HelloWorld/java/standalone/BUILD_saj_helloworld_pub.bat %*
call HelloWorld/java/standalone/BUILD_saj_helloworld_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building HelloWorld/java/standalone/BUILD_saj_helloworld_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call HelloWorld/java/standalone/BUILD_saj_helloworld_sub.bat %*
call HelloWorld/java/standalone/BUILD_saj_helloworld_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building HelloWorld/java/standalone/BUILD_saj_helloworld_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Lifecycle/java/standalone/BUILD_saj_lifecycle_types.bat %*
call Lifecycle/java/standalone/BUILD_saj_lifecycle_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Lifecycle/java/standalone/BUILD_saj_lifecycle_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Lifecycle/java/standalone/BUILD_saj_lifecycle_pub.bat %*
call Lifecycle/java/standalone/BUILD_saj_lifecycle_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Lifecycle/java/standalone/BUILD_saj_lifecycle_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Lifecycle/java/standalone/BUILD_saj_lifecycle_sub.bat %*
call Lifecycle/java/standalone/BUILD_saj_lifecycle_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Lifecycle/java/standalone/BUILD_saj_lifecycle_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Listener/java/standalone/BUILD_saj_listener_types.bat %*
call Listener/java/standalone/BUILD_saj_listener_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Listener/java/standalone/BUILD_saj_listener_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Listener/java/standalone/BUILD_saj_listener_pub.bat %*
call Listener/java/standalone/BUILD_saj_listener_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Listener/java/standalone/BUILD_saj_listener_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Listener/java/standalone/BUILD_saj_listener_sub.bat %*
call Listener/java/standalone/BUILD_saj_listener_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Listener/java/standalone/BUILD_saj_listener_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Ownership/java/standalone/BUILD_saj_ownership_types.bat %*
call Ownership/java/standalone/BUILD_saj_ownership_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Ownership/java/standalone/BUILD_saj_ownership_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Ownership/java/standalone/BUILD_saj_ownership_pub.bat %*
call Ownership/java/standalone/BUILD_saj_ownership_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Ownership/java/standalone/BUILD_saj_ownership_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Ownership/java/standalone/BUILD_saj_ownership_sub.bat %*
call Ownership/java/standalone/BUILD_saj_ownership_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Ownership/java/standalone/BUILD_saj_ownership_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call PingPong/java/corba/BUILD_cj_pingpong.bat %*
call PingPong/java/corba/BUILD_cj_pingpong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building PingPong/java/corba/BUILD_cj_pingpong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call PingPong/java/corba/BUILD_cj_pingpong_ping.bat %*
call PingPong/java/corba/BUILD_cj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building PingPong/java/corba/BUILD_cj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call PingPong/java/corba/BUILD_cj_pingpong_pong.bat %*
call PingPong/java/corba/BUILD_cj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building PingPong/java/corba/BUILD_cj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call PingPong/java/standalone/BUILD_saj_pingpong_types.bat %*
call PingPong/java/standalone/BUILD_saj_pingpong_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building PingPong/java/standalone/BUILD_saj_pingpong_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call PingPong/java/standalone/BUILD_saj_pingpong_ping.bat %*
call PingPong/java/standalone/BUILD_saj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building PingPong/java/standalone/BUILD_saj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call PingPong/java/standalone/BUILD_saj_pingpong_pong.bat %*
call PingPong/java/standalone/BUILD_saj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building PingPong/java/standalone/BUILD_saj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call QueryCondition/java/standalone/BUILD_saj_querycondition_types.bat %*
call QueryCondition/java/standalone/BUILD_saj_querycondition_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building QueryCondition/java/standalone/BUILD_saj_querycondition_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call QueryCondition/java/standalone/BUILD_saj_querycondition_pub.bat %*
call QueryCondition/java/standalone/BUILD_saj_querycondition_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building QueryCondition/java/standalone/BUILD_saj_querycondition_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call QueryCondition/java/standalone/BUILD_saj_querycondition_sub.bat %*
call QueryCondition/java/standalone/BUILD_saj_querycondition_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building QueryCondition/java/standalone/BUILD_saj_querycondition_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Tutorial/java/corba/BUILD_cj_tutorial_types.bat %*
call Tutorial/java/corba/BUILD_cj_tutorial_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Tutorial/java/corba/BUILD_cj_tutorial_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Tutorial/java/corba/BUILD_cj_tutorial_chatter.bat %*
call Tutorial/java/corba/BUILD_cj_tutorial_chatter.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Tutorial/java/corba/BUILD_cj_tutorial_chatter.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Tutorial/java/corba/BUILD_cj_tutorial_messageboard.bat %*
call Tutorial/java/corba/BUILD_cj_tutorial_messageboard.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Tutorial/java/corba/BUILD_cj_tutorial_messageboard.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Tutorial/java/corba/BUILD_cj_tutorial_userload.bat %*
call Tutorial/java/corba/BUILD_cj_tutorial_userload.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Tutorial/java/corba/BUILD_cj_tutorial_userload.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Tutorial/java/standalone/BUILD_saj_tutorial_types.bat %*
call Tutorial/java/standalone/BUILD_saj_tutorial_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Tutorial/java/standalone/BUILD_saj_tutorial_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Tutorial/java/standalone/BUILD_saj_tutorial_chatter.bat %*
call Tutorial/java/standalone/BUILD_saj_tutorial_chatter.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Tutorial/java/standalone/BUILD_saj_tutorial_chatter.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Tutorial/java/standalone/BUILD_saj_tutorial_messageboard.bat %*
call Tutorial/java/standalone/BUILD_saj_tutorial_messageboard.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Tutorial/java/standalone/BUILD_saj_tutorial_messageboard.bat
ECHO:
GOTO error
)
cd %~dp0
echo call Tutorial/java/standalone/BUILD_saj_tutorial_userload.bat %*
call Tutorial/java/standalone/BUILD_saj_tutorial_userload.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building Tutorial/java/standalone/BUILD_saj_tutorial_userload.bat
ECHO:
GOTO error
)
cd %~dp0
echo call WaitSet/java/standalone/BUILD_saj_waitset_types.bat %*
call WaitSet/java/standalone/BUILD_saj_waitset_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building WaitSet/java/standalone/BUILD_saj_waitset_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call WaitSet/java/standalone/BUILD_saj_waitset_pub.bat %*
call WaitSet/java/standalone/BUILD_saj_waitset_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building WaitSet/java/standalone/BUILD_saj_waitset_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call WaitSet/java/standalone/BUILD_saj_waitset_sub.bat %*
call WaitSet/java/standalone/BUILD_saj_waitset_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building WaitSet/java/standalone/BUILD_saj_waitset_sub.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
