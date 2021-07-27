import boto3
from botocore.config import Config
config=Config(
    signature_version='s3'        
)
client = boto3.client(
    service_name='s3',
    config=config,
    endpoint_url='http://s3.hicloud.net.tw',
    aws_access_key_id='',
    aws_secret_access_key=''
)
