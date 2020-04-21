@echo off
echo cd %~dp0
cd %~dp0
setlocal

set MAINCLASS=pong
set JARFILE=saj_pingpong_pong.jar
set MANIFEST=saj_pingpong_pong.manifest

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
del /f/s/q classes\pong.class classes\ponger.class 2>nul

IF /I "%1"=="clean" GOTO end

:build

REM
REM Compile java code
REM
echo Creating class output dir classes\....
if not exist classes\ echo mkdir classes\
if not exist classes\ mkdir classes\
echo Compiling Java classes....
echo javac -sourcepath ..\src -cp "saj_pingpong_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\pong.java
javac -sourcepath ..\src -cp "saj_pingpong_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\pong.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\pong.java failed
  ECHO:
  GOTO error
)
echo javac -sourcepath ..\src -cp "saj_pingpong_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\ponger.java
javac -sourcepath ..\src -cp "saj_pingpong_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\ponger.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\ponger.java failed
  ECHO:
  GOTO error
)

REM
REM Build a jar file
REM
set JARFLAGS=cvfm
echo Building a jar file....
echo echo Class-Path: saj_pingpong_types.jar ..\..\..\..\..\jar\dcpssaj.jar ^> classes\%MANIFEST%
echo Class-Path: saj_pingpong_types.jar ..\..\..\..\..\jar\dcpssaj.jar > classes\%MANIFEST%
echo echo Main-Class: %MAINCLASS%^>^> classes\%MANIFEST%
echo Main-Class: %MAINCLASS%>> classes\%MANIFEST%
echo pushd classes\ ^& jar %JARFLAGS% %JARFILE% %MANIFEST%  pong.class ponger.class ^& popd
pushd classes\ & jar %JARFLAGS% %JARFILE% %MANIFEST%  pong.class ponger.class & popd
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
