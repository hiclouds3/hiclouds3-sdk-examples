import boto3
from botocore.exceptions import ClientError
from client import client

# test 1.Put bucket with region
#      2.Put Bucket with canned ACL
#      3.Basic putObject
#      4.Get bucket with parameters
#      5.Basic delete Objects
#      6.Basic delete Bucket


def main(arg):
    try:
        # putBucket with region
        print("Try putBucket with region 'ap-northeast-1':")
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-northeast-1'},
            Bucket=arg[2],
        )
        result = client.get_bucket_location(
            Bucket=arg[2],
        )
        print("Bucket server location: " + repr(result['LocationConstraint']))
        client.delete_bucket(
            Bucket=arg[2],
        )
        print(" - Put bukcet with region done\n---------------------------------------------------")
        print("put Bucket with 'public-read' permission..")
        client.create_bucket(
            ACL='public-read',
            CreateBucketConfiguration={'LocationConstraint': 'ap-northeast-1'},
            Bucket=arg[0],
        )
        result = client.get_bucket_acl(
            Bucket=arg[0],
        )
        print("Bucket permission: " + repr(result['Grants']))

        fileName = "apple.jpg"
        fileName2 = "photos/2006/January/sample.jpg"
        fileName3 = "photos/2006/February/sample2.jpg"
        fileName4 = "asset.txt"

        client.put_object(
            Body='Hello World!',
            Bucket=arg[0],
            Key=fileName
        )
        client.put_object(
            ACL='public-read',
            Body='Hello World!',
            Bucket=arg[0],
            Key=fileName2
        )
        client.copy_object(
                Bucket=arg[0],
                CopySource=fileName2,
                Key=fileName3,
                )
        client.put_object(
            Body='Hello World!',
            Bucket=arg[0],
            Key=fileName4
        )



        print("\nClean up..")
        # clear bucket

        result = client.list_objects_v2(
            Bucket=arg[0],
        )
        print(result)
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
        print(" - Bucket Serial Test done!\n")

    except ClientError as e:
        print(e.operation_name)
        print(e.response['Error']['Message'])