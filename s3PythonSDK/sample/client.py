import boto3
from botocore.config import Config
# Initiate connection without using boto.cfg
session = boto3.session.Session()
client = session.client(
    service_name='s3',
    endpoint_url='http://s3.hicloud.net.tw',
    aws_access_key_id='',
    aws_secret_access_key=''
)
