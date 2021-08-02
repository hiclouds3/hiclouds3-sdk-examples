## Sample Code

## Environment setup
* Python3.7 以上
* pip install boto3==1.17.105

## Python SDK Serial Test Read me
* 目前sample code是使用v4簽證 如果需要使用v2則需更改以下內容
	* 在「sample/client.py」把's3v4'改成's3'
* 在「sample/client.py」填入AccessKey, SecretKey資訊
* 在「sample/PythonSerialTest.py」填入使用者資訊
* 在「sample/PythonSerialTest.py」設定bucketName/filePath等資訊
* 執行「sample/PythonSerialTest.py」將會將所有範例程式執行過一遍

