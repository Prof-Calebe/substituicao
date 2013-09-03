#!/bin/bash

_PWD=`pwd`
CopyLibs=$_PWD/lib/org-netbeans-modules-java-j2seproject-copylibstask.jar

CLASSPATH=$CLASSPATH:$_PWD/lib/ant-contrib-1.0b3.jar:$CopyLibs:$_PWD\glassfish3\glassfish\lib\appserv-rt.jar:$_PWD\glassfish3\glassfish\lib\gf-client.jar
export CLASSPATH
echo "CLASSPATH:"$CLASSPATH

wget -nv -T 10 -t 0 "http://dlc.sun.com.edgesuite.net/glassfish/3.1.2/release/glassfish-3.1.2.zip"
# já cria um diretório chamado glassfish3
unzip -q glassfish-3.1.2.zip

echo " "
echo "Compiling these projects:"
for i in * ; do
  if [ -d "$i" ] && [ ! "$i" = "lib" ] && [ ! "$i" = "glassfish3" ]; then
    cd "$i"

    if [ -f "manifest.mf" ]; then
        TARGET=jar
    else
        TARGET=dist
    fi
    TARGET=""

    echo " "
    echo "----- $i -----"
    ant -Dlibs.CopyLibs.classpath="$CopyLibs" \
        -Dj2ee.server.home="$_PWD\glassfish3\glassfish" $TARGET
    RET=$?
    if [ ! "$RET" = "0" ]; then
        exit $RET
    fi
    echo " "
    cd ..
  fi
done

