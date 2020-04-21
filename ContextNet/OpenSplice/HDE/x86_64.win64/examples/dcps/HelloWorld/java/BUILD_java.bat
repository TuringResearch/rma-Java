@echo off
echo call corba/BUILD_cj_helloworld_types.bat %*
call corba/BUILD_cj_helloworld_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_helloworld_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call corba/BUILD_cj_helloworld_pub.bat %*
call corba/BUILD_cj_helloworld_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_helloworld_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call corba/BUILD_cj_helloworld_sub.bat %*
call corba/BUILD_cj_helloworld_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building corba/BUILD_cj_helloworld_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_helloworld_types.bat %*
call standalone/BUILD_saj_helloworld_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_helloworld_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_helloworld_pub.bat %*
call standalone/BUILD_saj_helloworld_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_helloworld_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call standalone/BUILD_saj_helloworld_sub.bat %*
call standalone/BUILD_saj_helloworld_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building standalone/BUILD_saj_helloworld_sub.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
