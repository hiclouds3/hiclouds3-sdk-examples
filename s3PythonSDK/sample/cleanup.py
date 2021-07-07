from client import client 
from botocore.exceptions import ClientError
#from boto3.s3.key import Key
def Expectexception():
    print ("Cleanup" ,"Error")
def main(arg):
    
    for i in arg:
        try:
            client.create_bucket(
                    ACL='private' or 'public-read' or 'public-read-write' or'authenticated-read',
                    CreateBucketConfiguration={
                            'LocationConstraint': 'ap-northeast-1'
                            },
                    Bucket=i,
                    )
            
            print( "prepare clean bucket: " + i)
            result =client.list_objects_v2(
                    Bucket=i,
                    )
            for v in result:
                if v=='Contents':
                    for r in result['Contents']:
                        client.delete_object(
                            Bucket=i,
                            Key=r['Key']
                        )
            client.delete_bucket(
                    Bucket=i,
            )
        except ClientError :
            Expectexception()
    print("Cleanup done!")
#except S3ResponseError,e:
#Expectexception(e,404)
