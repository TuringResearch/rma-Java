@echo off
echo call java/corba/BUILD_cj_helloworld_types.bat %*
call java/corba/BUILD_cj_helloworld_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/corba/BUILD_cj_helloworld_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/corba/BUILD_cj_helloworld_pub.bat %*
call java/corba/BUILD_cj_helloworld_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/corba/BUILD_cj_helloworld_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/corba/BUILD_cj_helloworld_sub.bat %*
call java/corba/BUILD_cj_helloworld_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/corba/BUILD_cj_helloworld_sub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/standalone/BUILD_saj_helloworld_types.bat %*
call java/standalone/BUILD_saj_helloworld_types.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_helloworld_types.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/standalone/BUILD_saj_helloworld_pub.bat %*
call java/standalone/BUILD_saj_helloworld_pub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_helloworld_pub.bat
ECHO:
GOTO error
)
cd %~dp0
echo call java/standalone/BUILD_saj_helloworld_sub.bat %*
call java/standalone/BUILD_saj_helloworld_sub.bat %*
IF NOT %ERRORLEVEL% == 0 (
ECHO:
ECHO *** Error building java/standalone/BUILD_saj_helloworld_sub.bat
ECHO:
GOTO error
)
cd %~dp0
GOTO end
:error
ECHO An error occurred, exiting now
:end
