@echo off
echo call dcps/BuiltInTopics/java/standalone/BUILD_saj_builtintopics_sub.bat %*
call dcps/BuiltInTopics/java/standalone/BUILD_saj_builtintopics_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/BuiltInTopics/java/standalone/BUILD_saj_builtintopics_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_types.bat %*
call dcps/ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_pub.bat %*
call dcps/ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_sub.bat %*
call dcps/ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/ContentFilteredTopic/java/standalone/BUILD_saj_contentfilteredtopic_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Durability/java/standalone/BUILD_saj_durability_types.bat %*
call dcps/Durability/java/standalone/BUILD_saj_durability_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Durability/java/standalone/BUILD_saj_durability_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Durability/java/standalone/BUILD_saj_durability_pub.bat %*
call dcps/Durability/java/standalone/BUILD_saj_durability_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Durability/java/standalone/BUILD_saj_durability_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Durability/java/standalone/BUILD_saj_durability_sub.bat %*
call dcps/Durability/java/standalone/BUILD_saj_durability_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Durability/java/standalone/BUILD_saj_durability_sub.bat
ECHO:
GOTO error
)
cd %~dp0
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
echo call dcps/HelloWorld/java/standalone/BUILD_saj_helloworld_types.bat %*
call dcps/HelloWorld/java/standalone/BUILD_saj_helloworld_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/HelloWorld/java/standalone/BUILD_saj_helloworld_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/HelloWorld/java/standalone/BUILD_saj_helloworld_pub.bat %*
call dcps/HelloWorld/java/standalone/BUILD_saj_helloworld_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/HelloWorld/java/standalone/BUILD_saj_helloworld_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/HelloWorld/java/standalone/BUILD_saj_helloworld_sub.bat %*
call dcps/HelloWorld/java/standalone/BUILD_saj_helloworld_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/HelloWorld/java/standalone/BUILD_saj_helloworld_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Lifecycle/java/standalone/BUILD_saj_lifecycle_types.bat %*
call dcps/Lifecycle/java/standalone/BUILD_saj_lifecycle_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Lifecycle/java/standalone/BUILD_saj_lifecycle_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Lifecycle/java/standalone/BUILD_saj_lifecycle_pub.bat %*
call dcps/Lifecycle/java/standalone/BUILD_saj_lifecycle_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Lifecycle/java/standalone/BUILD_saj_lifecycle_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Lifecycle/java/standalone/BUILD_saj_lifecycle_sub.bat %*
call dcps/Lifecycle/java/standalone/BUILD_saj_lifecycle_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Lifecycle/java/standalone/BUILD_saj_lifecycle_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Listener/java/standalone/BUILD_saj_listener_types.bat %*
call dcps/Listener/java/standalone/BUILD_saj_listener_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Listener/java/standalone/BUILD_saj_listener_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Listener/java/standalone/BUILD_saj_listener_pub.bat %*
call dcps/Listener/java/standalone/BUILD_saj_listener_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Listener/java/standalone/BUILD_saj_listener_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Listener/java/standalone/BUILD_saj_listener_sub.bat %*
call dcps/Listener/java/standalone/BUILD_saj_listener_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Listener/java/standalone/BUILD_saj_listener_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Ownership/java/standalone/BUILD_saj_ownership_types.bat %*
call dcps/Ownership/java/standalone/BUILD_saj_ownership_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Ownership/java/standalone/BUILD_saj_ownership_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Ownership/java/standalone/BUILD_saj_ownership_pub.bat %*
call dcps/Ownership/java/standalone/BUILD_saj_ownership_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Ownership/java/standalone/BUILD_saj_ownership_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Ownership/java/standalone/BUILD_saj_ownership_sub.bat %*
call dcps/Ownership/java/standalone/BUILD_saj_ownership_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Ownership/java/standalone/BUILD_saj_ownership_sub.bat
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
echo call dcps/PingPong/java/standalone/BUILD_saj_pingpong_types.bat %*
call dcps/PingPong/java/standalone/BUILD_saj_pingpong_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/PingPong/java/standalone/BUILD_saj_pingpong_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/PingPong/java/standalone/BUILD_saj_pingpong_ping.bat %*
call dcps/PingPong/java/standalone/BUILD_saj_pingpong_ping.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/PingPong/java/standalone/BUILD_saj_pingpong_ping.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/PingPong/java/standalone/BUILD_saj_pingpong_pong.bat %*
call dcps/PingPong/java/standalone/BUILD_saj_pingpong_pong.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/PingPong/java/standalone/BUILD_saj_pingpong_pong.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/QueryCondition/java/standalone/BUILD_saj_querycondition_types.bat %*
call dcps/QueryCondition/java/standalone/BUILD_saj_querycondition_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/QueryCondition/java/standalone/BUILD_saj_querycondition_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/QueryCondition/java/standalone/BUILD_saj_querycondition_pub.bat %*
call dcps/QueryCondition/java/standalone/BUILD_saj_querycondition_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/QueryCondition/java/standalone/BUILD_saj_querycondition_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/QueryCondition/java/standalone/BUILD_saj_querycondition_sub.bat %*
call dcps/QueryCondition/java/standalone/BUILD_saj_querycondition_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/QueryCondition/java/standalone/BUILD_saj_querycondition_sub.bat
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
echo call dcps/Tutorial/java/standalone/BUILD_saj_tutorial_types.bat %*
call dcps/Tutorial/java/standalone/BUILD_saj_tutorial_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Tutorial/java/standalone/BUILD_saj_tutorial_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Tutorial/java/standalone/BUILD_saj_tutorial_chatter.bat %*
call dcps/Tutorial/java/standalone/BUILD_saj_tutorial_chatter.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Tutorial/java/standalone/BUILD_saj_tutorial_chatter.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Tutorial/java/standalone/BUILD_saj_tutorial_messageboard.bat %*
call dcps/Tutorial/java/standalone/BUILD_saj_tutorial_messageboard.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Tutorial/java/standalone/BUILD_saj_tutorial_messageboard.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/Tutorial/java/standalone/BUILD_saj_tutorial_userload.bat %*
call dcps/Tutorial/java/standalone/BUILD_saj_tutorial_userload.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/Tutorial/java/standalone/BUILD_saj_tutorial_userload.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/WaitSet/java/standalone/BUILD_saj_waitset_types.bat %*
call dcps/WaitSet/java/standalone/BUILD_saj_waitset_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/WaitSet/java/standalone/BUILD_saj_waitset_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/WaitSet/java/standalone/BUILD_saj_waitset_pub.bat %*
call dcps/WaitSet/java/standalone/BUILD_saj_waitset_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/WaitSet/java/standalone/BUILD_saj_waitset_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call dcps/WaitSet/java/standalone/BUILD_saj_waitset_sub.bat %*
call dcps/WaitSet/java/standalone/BUILD_saj_waitset_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building dcps/WaitSet/java/standalone/BUILD_saj_waitset_sub.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
