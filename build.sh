#!/bin/bash
# Exits if any command fails: http://stackoverflow.com/questions/821396/aborting-a-shell-script-if-any-command-returns-a-non-zero-value
set -e
set -o pipefail

_PWD=`pwd`
CopyLibs=$_PWD/lib/org-netbeans-modules-java-j2seproject-copylibstask.jar

CLASSPATH=$CLASSPATH:$_PWD/lib/ant-contrib-1.0b3.jar:$CopyLibs:$_PWD\glassfish3\glassfish\lib\appserv-rt.jar:$_PWD\glassfish3\glassfish\lib\gf-client.jar:$_PWD/lib/junit-4.12.jar
export CLASSPATH
echo "CLASSPATH:"$CLASSPATH

wget -nv -T 10 -t 0 "http://download.java.net/glassfish/3.1.2/release/glassfish-3.1.2.zip"
# já cria um diretório chamado glassfish3
unzip -q glassfish-3.1.2.zip

ant -buildfile substituicao.xml substituicao
curl -k https://www.jpm4j.org/install/script | sudo sh
sudo jpm install com.codacy:codacy-coverage-reporter:assembly
codacy-coverage-reporter -l Java -r build/test/results/jacoco/report.xml
