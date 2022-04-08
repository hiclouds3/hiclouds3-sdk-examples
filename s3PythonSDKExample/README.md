# hicloud S3 Python SDK Examples

## Prerequisites

* Python >= 3.6

* Install python package dependencies
	
	```$ pip install -r requirements.txt```

## Getting Started

### Setup

* Signature Version 4 is used as default. If you want to use Signature Version 2,you need to change the following lines.
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

### Running

- S3Sample: Demo of simple S3 operations

    ```sh
	$ python3 sample/S3Sample.py
	```

- SerialTest

	```sh
	$ python3 sample/PythonSerialTest.py
	```

## Additional Resource
* [開發指南](documentation/hicloudS3-python-sdk-開發指南.pdf)
* [Developer Guide](documentation/hicloudS3-python-sdk-DeveloperGuide.pdf)
