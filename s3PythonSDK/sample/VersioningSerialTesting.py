from botocore.exceptions import ClientError
from client import client


def main(arg):
    try:
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-northeast-1'},
            Bucket=arg[0],
        )
        #print("\nEnable Versioning :")
        client.put_bucket_versioning(
            Bucket=arg[0],
            VersioningConfiguration={
                'MFADelete': 'Disabled',
                'Status': 'Enabled'
            },
        )
        response = client.get_bucket_versioning(
            Bucket=arg[0]
        )
        #print('Status : ' + response['Status'])
        client.put_object(
            Bucket=arg[0],
            Key='/photo/cht2.jpg',
        )
        client.put_object(
            Body='Hello',
            Bucket=arg[0],
            Key='/photo/cht2.jpg',
        )
        client.put_object(
            Body='Hello Python',
            Bucket=arg[0],
            Key='/photo/cht2.jpg',
        )
        client.put_object(
            Body='Hello Python World',
            Bucket=arg[0],
            Key='/photo/cht2.jpg',
        )
        client.put_object(
            Bucket=arg[0],
            Key='cht1.jpg',
        )
        client.put_object(
            Body='Hello',
            Bucket=arg[0],
            Key='cht1.jpg',
        )
        client.put_object(
            Body='Hello Python',
            Bucket=arg[0],
            Key='cht1.jpg',
        )
        client.put_object(
            Body='Hello Python World',
            Bucket=arg[0],
            Key='cht1.jpg',
        )
        result = client.list_object_versions(
            Bucket=arg[0],
        )
        #print("\nList all Versions:\n  File         |   Version-ID")
        # for v in result['Versions']:
        #    print(v['Key'] + " | " + v['VersionId'])
        result = client.list_object_versions(
            Bucket=arg[0],
            MaxKeys=2
        )
        #print("\nList Versions with max_keys=2:\n  File         |   Version-ID")
        # for v in result['Versions']:
        #    print(v['Key'] + " | " + v['VersionId'])
        result = client.list_object_versions(
            Bucket=arg[0],
            Delimiter='/',
        )
        #print("\nList Versions with delemeter '/':\n  File         |   Version-ID")
        # for v in result['Versions']:
        #    print(v['Key'] + " | " + v['VersionId'])
        result = client.list_object_versions(
            Bucket=arg[0],
            Prefix='/photo/',
        )
        #print("\nList Versions with prefix '/photo/':\n  File         |   Version-ID")
        # for v in result['Versions']:
        #    print(v['Key'] + " | " + v['VersionId'])
        result = client.list_object_versions(
            Bucket=arg[0],
            Prefix='/photo/',
            MaxKeys=2
        )
        #print("\nList Versions with prefix ='/photo/' & max_keys=2:\n  File         |   Version-ID")
        # for v in result['Versions']:
        #    print(v['Key'] + " | " + v['VersionId'])

        #print("\nSuspend Versioning :")
        client.put_bucket_versioning(
            Bucket=arg[0],
            VersioningConfiguration={
                'MFADelete': 'Disabled',
                'Status': 'Suspended'
            },
        )
        response = client.get_bucket_versioning(
            Bucket=arg[0]
        )
        #print('Status : ' + response['Status'])
        #print("\nClean up..")
        result = client.list_object_versions(
            Bucket=arg[0],
        )
        for v in result['Versions']:
            client.delete_object(
                Bucket=arg[0],
                Key=v['Key'],
                VersionId=v['VersionId'],
            )
        client.delete_bucket(
            Bucket=arg[0],
        )
        print("Versioning Serial Test Done !")

    except ClientError as e:
        print("Error operation : " + e.operation_name)
        print("Error response : " + e.response['Error']['Message'])
