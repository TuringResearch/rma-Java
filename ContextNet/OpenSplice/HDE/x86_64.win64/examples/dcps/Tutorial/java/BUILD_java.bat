@echo off
echo call corba/BUILD_cj_tutorial_types.bat %*
call corba/BUILD_cj_tutorial_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_tutorial_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call corba/BUILD_cj_tutorial_chatter.bat %*
call corba/BUILD_cj_tutorial_chatter.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_tutorial_chatter.bat
ECHO:
GOTO error
)
cd %~dp0
echo call corba/BUILD_cj_tutorial_messageboard.bat %*
call corba/BUILD_cj_tutorial_messageboard.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_tutorial_messageboard.bat
ECHO:
GOTO error
)
cd %~dp0
echo call corba/BUILD_cj_tutorial_userload.bat %*
call corba/BUILD_cj_tutorial_userload.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_tutorial_userload.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_tutorial_types.bat %*
call standalone/BUILD_saj_tutorial_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_tutorial_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_tutorial_chatter.bat %*
call standalone/BUILD_saj_tutorial_chatter.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_tutorial_chatter.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_tutorial_messageboard.bat %*
call standalone/BUILD_saj_tutorial_messageboard.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_tutorial_messageboard.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_tutorial_userload.bat %*
call standalone/BUILD_saj_tutorial_userload.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_tutorial_userload.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
