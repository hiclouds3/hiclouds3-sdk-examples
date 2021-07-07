import boto3
from botocore.config import Config
#Initiate connection without using boto.cfg
my_config = Config(
    region_name = 'ap-northeast-1',
)
client = boto3.client(
    's3',
    config=my_config,
    aws_access_key_id='??',
    aws_secret_access_key='??'
    )
#is_secure=False,
#calling_format=OrdinaryCallingFormat(),