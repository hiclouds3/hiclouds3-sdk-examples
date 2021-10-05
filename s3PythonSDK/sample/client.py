import configparser

import boto3
from botocore.config import Config

config = configparser.ConfigParser()
config.read('Config.ini')
access_key = config.get('Credentials', 'AccessKey')
secret_key = config.get('Credentials', 'SecretKey')

client = boto3.client(
    service_name='s3',
    config=Config(
        signature_version='s3v4',
        region_name='us-east-1'
    ),
    endpoint_url='http://s3.hicloud.net.tw',
    aws_access_key_id=access_key,
    aws_secret_access_key=secret_key
)
