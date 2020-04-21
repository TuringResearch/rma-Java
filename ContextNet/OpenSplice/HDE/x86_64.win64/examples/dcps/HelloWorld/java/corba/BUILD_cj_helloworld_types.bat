@echo off
echo cd %~dp0
cd %~dp0
setlocal

set JARFILE=cj_helloworld_types.jar
set MANIFEST=cj_helloworld_types.manifest

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
del /f/s/q HelloWorldDataDcps.idl HelloWorldData\*.java  2>nul
del /f/s/q classes\HelloWorldData\*.class 2>nul

IF /I "%1"=="clean" GOTO end

:build

REM
REM Generate java classes from IDL
REM
echo Processing ..\..\idl\HelloWorldData.idl....
echo "..\..\..\..\..\bin\idlpp" -I "..\..\..\..\..\etc\idl" -C -l java ..\..\idl\HelloWorldData.idl
"..\..\..\..\..\bin\idlpp" -I "..\..\..\..\..\etc\idl" -C -l java ..\..\idl\HelloWorldData.idl
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Compilation of ..\..\idl\HelloWorldData.idl failed
  ECHO:
  GOTO error
)
echo java -classpath "%JACORB_HOME%\lib\idl.jar;%JACORB_HOME%\lib\endorsed\logkit.jar" org.jacorb.idl.parser -I..\..\..\..\..\etc\idl ..\..\idl\HelloWorldData.idl
java -classpath "%JACORB_HOME%\lib\idl.jar;%JACORB_HOME%\lib\endorsed\logkit.jar" org.jacorb.idl.parser -I..\..\..\..\..\etc\idl ..\..\idl\HelloWorldData.idl
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Post compilation step java -classpath "%JACORB_HOME%\lib\idl.jar;%JACORB_HOME%\lib\endorsed\logkit.jar" org.jacorb.idl.parser -I..\..\..\..\..\etc\idl ..\..\idl\HelloWorldData.idl failed
  ECHO:
  GOTO error
)
echo Processing HelloWorldDataDcps.idl....
echo java -classpath "%JACORB_HOME%\lib\idl.jar;%JACORB_HOME%\lib\endorsed\logkit.jar" org.jacorb.idl.parser -I..\..\..\..\..\etc\idl -I../../idl HelloWorldDataDcps.idl
java -classpath "%JACORB_HOME%\lib\idl.jar;%JACORB_HOME%\lib\endorsed\logkit.jar" org.jacorb.idl.parser -I..\..\..\..\..\etc\idl -I../../idl HelloWorldDataDcps.idl
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Compilation of HelloWorldDataDcps.idl failed
  ECHO:
  GOTO error
)


REM
REM Compile java code
REM
echo Creating class output dir classes\....
if not exist classes\ echo mkdir classes\
if not exist classes\ mkdir classes\
echo Compiling Java classes....
echo javac -endorseddirs "%JACORB_HOME%\lib\endorsed" -cp "classes\;..\..\..\..\..\jar\dcpscj.jar;" -d classes\ HelloWorldData\*.java
javac -endorseddirs "%JACORB_HOME%\lib\endorsed" -cp "classes\;..\..\..\..\..\jar\dcpscj.jar;" -d classes\ HelloWorldData\*.java
IF NOT %ERRORLEVEL% == 0 (
  ECHO:
  ECHO *** Java compilation of HelloWorldData\*.java failed
  ECHO:
  GOTO error
)

REM
REM Build a jar file
REM
set JARFLAGS=cvfm
echo Building a jar file....
echo echo Class-Path: ..\..\..\..\..\jar\dcpscj.jar ^> classes\%MANIFEST%
echo Class-Path: ..\..\..\..\..\jar\dcpscj.jar > classes\%MANIFEST%
echo pushd classes\ ^& jar %JARFLAGS% %JARFILE% %MANIFEST%  HelloWorldData\*.class ^& popd
pushd classes\ & jar %JARFLAGS% %JARFILE% %MANIFEST%  HelloWorldData\*.class & popd
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
