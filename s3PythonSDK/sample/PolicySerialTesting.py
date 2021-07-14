from botocore.exceptions import ClientError
from client import client
# Use http://awspolicygen.s3.amazonaws.com/policygen.html to generate policy String
# test 1.Put bucket
#      2.Put policy
#      3.Get policy
#      4.Delete policy


def main(arg):
    try:
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-northeast-1'},
            Bucket=arg[0]
        )
        #print("Put Deny Public-read policy to bucket")
        client.put_bucket_policy(
            Bucket=arg[0],
            Policy='{"Version":"2012-10-17","Statement":[{"Sid":"DenyPublicREAD","Effect":"Deny","Principal":{"AWS":"*"},"Action":"s3:GetObject","Resource":"arn:aws:s3:::'+arg[0]+'/*"}]}',
        )
        #print("Get Policy:")
        response = client.get_bucket_policy(
            Bucket=arg[0]
        )
        # print(repr(response['Policy']))
        client.delete_bucket_policy(
            Bucket=arg[0]
        )
        #print("\n Clean up..")
        result = client.list_objects_v2(
            Bucket=arg[0],
        )
        for v in result:
            if v == 'Contents':
                for r in result['Contents']:
                    client.delete_object(
                        Bucket=arg[0],
                        Key=r['Key']
                    )
        client.delete_bucket(
            Bucket=arg[0],
        )
        print("Policy Serial Test Done !")
    except ClientError as e:
        print("Error operation : " + e.operation_name)
        print("Error response : " + e.response['Error']['Message'])
