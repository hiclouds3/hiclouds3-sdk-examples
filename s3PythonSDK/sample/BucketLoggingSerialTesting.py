from botocore.exceptions import ClientError
from client import client

# test 1. Basic putBucket
#      2. put BucketACL
#      3. put BucketLogging (put log Native & to target bucket)
#      4. get BucketLogging
#      5. Delete Bucket


def main(arg, ownerInfo):
    try:
        for bucket_name in arg:
            client.create_bucket(
                CreateBucketConfiguration={
                    'LocationConstraint': 'ap-southeast-1'
                },
                Bucket=bucket_name,
            )

            client.put_bucket_acl(
                Bucket=bucket_name,
                ACL='log-delivery-write'
            )
        client.put_bucket_logging(
            Bucket=arg[0],
            BucketLoggingStatus={
                'LoggingEnabled': {
                    'TargetBucket': arg[1],
                    'TargetGrants': [
                        {
                            'Grantee': {
                                'ID': ownerInfo[0],
                                'Type': 'CanonicalUser'
                            },
                            'Permission': 'FULL_CONTROL',
                        },
                    ],
                    'TargetPrefix': '',
                },
            },
        )
        #print("Logging List & Give full permission by user ID:")
        #print(repr(client.get_bucket_logging(Bucket=arg[0])['LoggingEnabled']) + "\n")
        #print("Clean up..\n")
        for bucket_name in arg:
            result = client.list_objects_v2(
                Bucket=bucket_name,
            )
            if 'Contents' in result:
                for r in result['Contents']:
                    client.delete_object(
                        Bucket=bucket_name,
                        Key=r['Key']
                    )
            client.delete_bucket(
                Bucket=bucket_name,
            )
        print("Bucket logging Serial test done!\n")
    except ClientError as e:
        print("Error operation : " + e.operation_name)
        print("Error code : " + e.response['Code'])
        print("Error response : " + e.response['Message'])
