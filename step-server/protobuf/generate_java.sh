#! /bin/bash
CMD_PATH=`dirname $0`
out_src=$CMD_PATH/../src/main/gen-java
rm -rf $out_src
mkdir $out_src

cd $CMD_PATH
import=-I./proto
for i in `ls proto/*.proto`
do
./protoc3.1/bin/protoc $import --java_out=$out_src $i
done


