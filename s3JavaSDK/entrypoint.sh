#!/bin/bash
printf 'Waiting for staas server.'
until $(curl --output /dev/null --silent --head --fail http://s3.hicloud.net.tw); do
    printf '.'
    sleep 5
done
echo ''
echo 'Staas Server started!'

echo 'Started running java serial test...'
cd sample/JAVA_SDK_Sample/
java -classpath ./target/s3sample-0.0.1-SNAPSHOT-jar-with-dependencies.jar hicloud.s3.sample.SerialTest

echo "Finished all tests... (infinite loop)"
while true; do sleep 15 ; printf "."; done
