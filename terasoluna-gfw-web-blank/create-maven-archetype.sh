#!/bin/sh
rm -rf ./tmp
mkdir tmp
cp -r src pom.xml tmp
pushd tmp
sed -i -e "s/org\.terasoluna\.gfw\.blank/xxxxxx.yyyyyy.zzzzzz/g" pom.xml

rm -rf `find . -name '.svn' -type d`
mvn archetype:create-from-project

pushd target/generated-sources/archetype

sed -i -e "s/xxxxxx\.yyyyyy\.zzzzzz/org.terasoluna.gfw.blank/g" pom.xml
rm -f target/classes/archetype-resources/src/main/java/domain/model/Employee.java
mvn deploy
