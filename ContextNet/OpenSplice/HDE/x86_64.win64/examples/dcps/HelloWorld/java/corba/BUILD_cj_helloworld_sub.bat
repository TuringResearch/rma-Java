@echo off
echo cd %~dp0
cd %~dp0
setlocal

set MAINCLASS=HelloWorldDataSubscriber
set JARFILE=cj_helloworld_sub.jar
set MANIFEST=cj_helloworld_sub.manifest

IF [%1]==[] GOTO build
IF /I "%1"=="clean" GOTO clean
IF /I "%1"=="rebuild" GOTO clean
ECHO Error: unrecognised option: %1
GOTO error

:clean
REM
REM Clean any previous output
REM
echo Cleaning...
del /f/s/q classes\%MANIFEST% 2>nul
del /f/s/q classes\HelloWorldDataSubscriber.class classes\DDSEntityManager.class classes\ErrorHandler.class 2>nul

IF /I "%1"=="clean" GOTO end

:build

REM
REM Compile java code
REM
echo Creating class output dir classes\....
if not exist classes\ echo mkdir classes\
if not exist classes\ mkdir classes\
echo Compiling Java classes....
echo javac -endorseddirs "%JACORB_HOME%\lib\endorsed" -sourcepath ..\src -cp "cj_helloworld_types.jar;classes\;..\..\..\..\..\jar\dcpscj.jar;" -d classes\ ..\src\HelloWorldDataSubscriber.java
javac -endorseddirs "%JACORB_HOME%\lib\endorsed" -sourcepath ..\src -cp "cj_helloworld_types.jar;classes\;..\..\..\..\..\jar\dcpscj.jar;" -d classes\ ..\src\HelloWorldDataSubscriber.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\HelloWorldDataSubscriber.java failed
  ECHO:
  GOTO error
)
echo javac -endorseddirs "%JACORB_HOME%\lib\endorsed" -sourcepath ..\src -cp "cj_helloworld_types.jar;classes\;..\..\..\..\..\jar\dcpscj.jar;" -d classes\ ..\src\DDSEntityManager.java
javac -endorseddirs "%JACORB_HOME%\lib\endorsed" -sourcepath ..\src -cp "cj_helloworld_types.jar;classes\;..\..\..\..\..\jar\dcpscj.jar;" -d classes\ ..\src\DDSEntityManager.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\DDSEntityManager.java failed
  ECHO:
  GOTO error
)
echo javac -endorseddirs "%JACORB_HOME%\lib\endorsed" -sourcepath ..\src -cp "cj_helloworld_types.jar;classes\;..\..\..\..\..\jar\dcpscj.jar;" -d classes\ ..\src\ErrorHandler.java
javac -endorseddirs "%JACORB_HOME%\lib\endorsed" -sourcepath ..\src -cp "cj_helloworld_types.jar;classes\;..\..\..\..\..\jar\dcpscj.jar;" -d classes\ ..\src\ErrorHandler.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\ErrorHandler.java failed
  ECHO:
  GOTO error
)

REM
REM Build a jar file
REM
set JARFLAGS=cvfm
echo Building a jar file....
echo echo Class-Path: cj_helloworld_types.jar ..\..\..\..\..\jar\dcpscj.jar ^> classes\%MANIFEST%
echo Class-Path: cj_helloworld_types.jar ..\..\..\..\..\jar\dcpscj.jar > classes\%MANIFEST%
echo echo Main-Class: %MAINCLASS%^>^> classes\%MANIFEST%
echo Main-Class: %MAINCLASS%>> classes\%MANIFEST%
echo pushd classes\ ^& jar %JARFLAGS% %JARFILE% %MANIFEST%  HelloWorldDataSubscriber.class DDSEntityManager.class ErrorHandler.class ^& popd
pushd classes\ & jar %JARFLAGS% %JARFILE% %MANIFEST%  HelloWorldDataSubscriber.class DDSEntityManager.class ErrorHandler.class & popd
echo move /y classes\%JARFILE% .
move /y classes\%JARFILE% .
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Building jar file %JARFILE% failed
  ECHO:
  GOTO error
)

GOTO end

:error
ECHO An error occurred, exiting now
:end
