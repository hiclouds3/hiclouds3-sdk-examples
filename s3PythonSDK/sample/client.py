from boto.s3.connection import S3Connection
from boto.s3.connection import OrdinaryCallingFormat

#Initiate connection without using boto.cfg
conn = S3Connection(aws_access_key_id='Enter Your AccessKey Here',
                    aws_secret_access_key='Enter Your SecretKey Here',
                    is_secure=False,
                    host='s3.hicloud.net.tw',
                    calling_format=OrdinaryCallingFormat(),
                    )