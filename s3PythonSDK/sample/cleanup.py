from client import client
from botocore.exceptions import ClientError


def main(arg):

    for i in arg:
        try:
            client.create_bucket(
                ACL='private' or 'public-read' or 'public-read-write' or 'authenticated-read',
                CreateBucketConfiguration={
                    'LocationConstraint': 'ap-northeast-1'
                },
                Bucket=i,
            )

            #print("prepare clean bucket: " + i)
            result = client.list_objects_v2(
                Bucket=i,
            )
            if 'Contents' in result:
                for r in result['Contents']:
                    client.delete_object(
                        Bucket=i,
                        Key=r['Key']
                    )
            client.delete_bucket(
                Bucket=i,
            )
        except ClientError as e:
            print("Error operation : " + e.operation_name)
            print("Error response : " + e.response['Message'])
    print("Cleanup done!\n")