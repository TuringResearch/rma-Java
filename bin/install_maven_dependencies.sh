#! /bin/sh
mvn install:install-file -Dfile=../lib/contextnet-2.5.jar -DgroupId=br.pucrio.inf.lac -DartifactId=contextnet -Dversion=2.5  -Dpackaging=jar -DgeneratePom=true
