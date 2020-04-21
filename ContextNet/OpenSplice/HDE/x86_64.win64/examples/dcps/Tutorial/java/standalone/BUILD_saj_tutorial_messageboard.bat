@echo off
echo cd %~dp0
cd %~dp0
setlocal

set MAINCLASS=chatroom.MessageBoard
set JARFILE=saj_tutorial_messageboard.jar
set MANIFEST=saj_tutorial_messageboard.manifest

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
del /f/s/q classes\chatroom\MessageBoard.class classes\chatroom\ErrorHandler.class classes\chatroom\ExtDomainParticipant.class classes\chatroom\ExtDomainParticipantHelper.class classes\chatroom\DataReaderListenerImpl.class 2>nul

IF /I "%1"=="clean" GOTO end

:build

REM
REM Compile java code
REM
echo Creating class output dir classes\....
if not exist classes\ echo mkdir classes\
if not exist classes\ mkdir classes\
echo Compiling Java classes....
echo javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\MessageBoard.java
javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\MessageBoard.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\chatroom\MessageBoard.java failed
  ECHO:
  GOTO error
)
echo javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\ErrorHandler.java
javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\ErrorHandler.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\chatroom\ErrorHandler.java failed
  ECHO:
  GOTO error
)
echo javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\ExtDomainParticipant.java
javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\ExtDomainParticipant.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\chatroom\ExtDomainParticipant.java failed
  ECHO:
  GOTO error
)
echo javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\ExtDomainParticipantHelper.java
javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\ExtDomainParticipantHelper.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\chatroom\ExtDomainParticipantHelper.java failed
  ECHO:
  GOTO error
)
echo javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\DataReaderListenerImpl.java
javac -sourcepath ..\src -cp "saj_tutorial_types.jar;classes\;..\..\..\..\..\jar\dcpssaj.jar;" -d classes\ ..\src\chatroom\DataReaderListenerImpl.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of ..\src\chatroom\DataReaderListenerImpl.java failed
  ECHO:
  GOTO error
)

REM
REM Build a jar file
REM
set JARFLAGS=cvfm
echo Building a jar file....
echo echo Class-Path: saj_tutorial_types.jar ..\..\..\..\..\jar\dcpssaj.jar ^> classes\%MANIFEST%
echo Class-Path: saj_tutorial_types.jar ..\..\..\..\..\jar\dcpssaj.jar > classes\%MANIFEST%
echo echo Main-Class: %MAINCLASS%^>^> classes\%MANIFEST%
echo Main-Class: %MAINCLASS%>> classes\%MANIFEST%
echo pushd classes\ ^& jar %JARFLAGS% %JARFILE% %MANIFEST%  chatroom\MessageBoard.class chatroom\ErrorHandler.class chatroom\ExtDomainParticipant.class chatroom\ExtDomainParticipantHelper.class chatroom\DataReaderListenerImpl.class ^& popd
pushd classes\ & jar %JARFLAGS% %JARFILE% %MANIFEST%  chatroom\MessageBoard.class chatroom\ErrorHandler.class chatroom\ExtDomainParticipant.class chatroom\ExtDomainParticipantHelper.class chatroom\DataReaderListenerImpl.class & popd
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
