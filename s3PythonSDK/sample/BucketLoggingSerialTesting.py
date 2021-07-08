import boto3
from botocore.exceptions import ClientError
from client import client


# test 1. Basic putBucket
#      2. put BucketACL
#      3. put BucketLogging (put log Native & to target bucket)
#      4. get BucketLogging
#      5. Delete Bucket
def main(arg, ownerInfo):
    try:
        for i in arg:
            client.create_bucket(
                CreateBucketConfiguration={
                    'LocationConstraint': 'ap-northeast-1'
                },
                Bucket=i,
            )

            client.put_bucket_acl(
                Bucket=i,
                ACL='log-delivery-write'
            )
        # the following both way can give log-delievery group WRITE & READ_ACP permission
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
        print("")
        print("Logging List & Give full permission by user ID:")
        print(repr(client.get_bucket_logging(
            Bucket=arg[0])['LoggingEnabled']) + "\n")
        # print "Put log to another bucket:\n"+repr(bucket.get_logging_status())
        # print "Disable logging:\n"+repr(bucket.get_logging_status())
        print("Clean up..\n")
        for i in arg:
            result = client.list_objects_v2(
                Bucket=i,
            )
            for v in result:
                if v == 'Contents':
                    for r in result['Contents']:
                        client.delete_object(
                            Bucket=i,
                            Key=r['Key']
                        )
            client.delete_bucket(
                Bucket=i,
            )
        print("Bucket logging Serial test done!")
    except ClientError as e:
        print(e.operation_name)
        print(e.response['Error']['Message'])
