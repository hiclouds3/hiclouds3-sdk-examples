import boto3
from botocore.config import Config
config=Config(
    signature_version='s3'        
)
client = boto3.client(
    service_name='s3',
    config=config,
    endpoint_url='http://s3.hicloud.net.tw',
    aws_access_key_id='IgZvbove5gZJHfzX8BUyUC0irS2ENDZ',
    aws_secret_access_key='3u22C1gb0p1P0Nwg2iQSausvihuSBClcH2F1BCovdaSuKdAZH9FgBlf0Fq1Gs1Etu'
)
