#!/bin/bash

_PWD=`pwd`
CopyLibs=$_PWD/lib/org-netbeans-modules-java-j2seproject-copylibstask.jar

CLASSPATH=$CLASSPATH:$_PWD/lib/ant-contrib-1.0b3.jar:$CopyLibs:$_PWD\glassfish3\glassfish\lib\appserv-rt.jar:$_PWD\glassfish3\glassfish\lib\gf-client.jar
export CLASSPATH
echo "CLASSPATH:"$CLASSPATH

wget -nv -T 10 -t 0 "http://download.java.net/glassfish/3.1.2/release/glassfish-3.1.2.zip"
# já cria um diretório chamado glassfish3
unzip -q glassfish-3.1.2.zip

echo " "
ant
exit $?

