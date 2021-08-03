# hicloud S3 Python SDK

## Environment setup
* Python3.7 ¥H¤W
* pip install boto3==1.17.105

## Python SDK Serial Test Read me
* This sample code use Signature Version 4 now. If you want to use Signature Version 2 ,you need to change the following.
	* Change 's3v4' to 's3' in¡usample/client.py¡v
		```python
	  	from botocore.config import Config
 	  	config = Config(
    	       	    signature_version= 's3' 
 	  	)
		```
* Fill in AccessKey, SecretKey in¡usample/client.py¡v
	```python
	import boto3
	client = boto3.client(
		aws_access_key_id='enter your Accesskey',
    	aws_secret_access_key='enter your secretKey',
	)
	```
* Fill in user information in¡usample/Config.ini¡v
	```sh
	[Section_A]
	userACanonicalID = enter your id
	userAMail = enter your email
	userBCanonicalID = enter your id
	userBMail = enter your email
	ownerCanonicalID = enter your id
	ownerMail = enter your email
	```
* Set bucketName and filePath information in¡usample/PythonSerialTest.py¡v
	```python
	buckets = ["enter your bucket name", "enter your bucket name", "enter your bucket name"]
	filePath = ["enter your filePath name", "enter your filePath name", "enter your filePath name"]
	```
* If you run¡usample/PythonSerialTest.py¡v,all the sample programs will be run once
	```sh
	$ python3 sample/PythonSerialTest.py
	```