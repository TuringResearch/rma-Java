@echo off
echo call BUILD_saj_tutorial_types.bat %*
call BUILD_saj_tutorial_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_saj_tutorial_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call BUILD_saj_tutorial_chatter.bat %*
call BUILD_saj_tutorial_chatter.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_saj_tutorial_chatter.bat
ECHO:
GOTO error
)
cd %~dp0
echo call BUILD_saj_tutorial_messageboard.bat %*
call BUILD_saj_tutorial_messageboard.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_saj_tutorial_messageboard.bat
ECHO:
GOTO error
)
cd %~dp0
echo call BUILD_saj_tutorial_userload.bat %*
call BUILD_saj_tutorial_userload.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building BUILD_saj_tutorial_userload.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
