#!/bin/sh
printf 'Waiting for staas server.'
until $(curl --output /dev/null --silent --head --fail http://s3.hicloud.net.tw); do
    printf '.'
    sleep 5
done
echo ''
echo 'Staas Server started!'

echo 'Started running PythonSerialTest...'
cd sample/
python PythonSerialTest.py

echo "Finished all tests... (infinite loop)"
while true; do sleep 15 ; printf "."; done
