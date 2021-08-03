# hicloud S3 Python SDK

## Environment setup
* Python3.7 以上
* pip install boto3==1.17.105

## Python SDK Serial Test Read me
* This sample code use Signature Version 4 now. If you want to use Signature Version 2 ,you need to change the following.
	* Change 's3v4' to 's3' in「sample/client.py」
		```python
	  	from botocore.config import Config
 	  	config = Config(
    	       	    signature_version= 's3' 
 	  	)
		```
* Fill in AccessKey, SecretKey in「sample/client.py」
	```python
	import boto3
	client = boto3.client(
		aws_access_key_id='enter your Accesskey',
    	aws_secret_access_key='enter your secretKey',
	)
	```
* Fill in user information in「sample/Config.ini」
	```sh
	[Section_A]
	userACanonicalID = enter your id
	userAMail = enter your email
	userBCanonicalID = enter your id
	userBMail = enter your email
	ownerCanonicalID = enter your id
	ownerMail = enter your email
	```
* Set bucketName and filePath information in「sample/PythonSerialTest.py」
	```python
	buckets = ["enter your bucket name", "enter your bucket name", "enter your bucket name"]
	filePath = ["enter your filePath name", "enter your filePath name", "enter your filePath name"]
	```
* If you run「sample/PythonSerialTest.py」,all the sample programs will be run once
	```sh
	$ python3 sample/PythonSerialTest.py
	```

## Additional Resource
* [開發指南](documentation/hicloudS3-python-sdk-開發指南.pdf)
* [Developer Guide](documentation/hicloudS3-python-sdk-DeveloperGuide.pdf)