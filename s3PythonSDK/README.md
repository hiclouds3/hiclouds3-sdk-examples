## Sample Code

## Environment setup
* Python3.7 ¥H¤W
* pip install boto3==1.17.105

## Python SDK Serial Test Read me
* This sample code use Signature Version 4 now. If you want to use Signature Version 2 ,you need to change the following.
	* Change 's3v4' to 's3' in¡usample/client.py¡v
	* example code  
	  	*from botocore.config import Config
 	  	*config = Config(
    	       		*signature_version= 's3' 
 	  	*)
* Fill in AccessKey, SecretKey in¡usample/client.py¡v
* Fill in user information in¡usample/PythonSerialTest.py¡v
* Set bucketName and filePath information in¡usample/PythonSerialTest.py¡v
* If you run¡usample/PythonSerialTest.py¡v,all the sample programs will be run once