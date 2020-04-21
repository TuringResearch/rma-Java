setx /m OSPL_HOME %~dp0
set OSPL_HOME=%~dp0
setx /m PATH "%OSPL_HOME%bin;%OSPL_HOME%lib;%OSPL_HOME%examples\lib;%PATH%"
setx /m OSPL_TMPL_PATH %OSPL_HOME%etc\idlpp
setx /m OSPL_URI file://%OSPL_HOME%etc\config\ospl.xml