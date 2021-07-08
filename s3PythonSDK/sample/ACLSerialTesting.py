from client import client
#from boto.s3.key import Key
from botocore.exceptions import ClientError


def main(arg, userAInfo, userBInfo, ownerInfo):
    try:
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-northeast-1'},
            Bucket=arg[0]
        )
        client.put_bucket_acl(
            Bucket=arg[0],
            ACL='public-read'
        )
        result = repr(client.get_bucket_acl(
            Bucket=arg[0],
        ))
        #print ("Set canned ACL 'public-read':\n" + result)

        client.put_bucket_acl(
            Bucket=arg[0],
            ACL='public-read-write'
        )
        result = repr(client.get_bucket_acl(
            Bucket=arg[0],
        ))
        #print("Make bucket public-read-write :\n" + result)

        client.put_bucket_acl(
            Bucket=arg[0],
            AccessControlPolicy={
                'Grants': [
                    {
                        'Grantee': {
                            'ID': userBInfo[0],
                            'Type': 'CanonicalUser',
                        },
                        'Permission': 'FULL_CONTROL'
                    },
                    {
                        'Grantee': {
                            'ID': userAInfo[0],
                            'Type': 'CanonicalUser',
                        },
                        'Permission': 'FULL_CONTROL'
                    }
                ],
                'Owner': {
                    'DisplayName': 'yoyoman0817',
                    'ID': ownerInfo[0]
                }
            },
        )
        result = repr(client.get_bucket_acl(
            Bucket=arg[0],
        ))
        #print ("Add grant by user id:\n" + result)

        client.put_bucket_acl(
            Bucket=arg[0],
            AccessControlPolicy={
                'Grants': [
                    {
                        'Grantee': {
                            'EmailAddress': userBInfo[1],
                            'Type': 'AmazonCustomerByEmail',
                        },
                        'Permission': 'FULL_CONTROL'
                    },
                    {
                        'Grantee': {
                            'EmailAddress': userAInfo[1],
                            'Type': 'AmazonCustomerByEmail',
                        },
                        'Permission': 'FULL_CONTROL'
                    }
                ],
                'Owner': {
                    'DisplayName': 'yoyoman0817',
                    'ID': ownerInfo[0]
                }
            },
        )
        result = repr(client.get_bucket_acl(
            Bucket=arg[0],
        ))
        #print ("Add grant by email :\n" + result)

        client.delete_bucket(
            Bucket=arg[0],
        )

        print("ACL Serial Test done!")

    except ClientError as e:
        print(e.operation_name)
        print(e.response['Error']['Message'])
