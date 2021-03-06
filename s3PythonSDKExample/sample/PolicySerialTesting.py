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
            CreateBucketConfiguration={'LocationConstraint': 'ap-southeast-1'},
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
        client.delete_bucket(
            Bucket=arg[0],
        )
        print("Policy Serial Test Done !\n")
    except ClientError as e:
        print(e)
