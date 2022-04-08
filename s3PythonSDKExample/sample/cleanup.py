from client import client
from botocore.exceptions import ClientError


def main():
    bucket = client.list_buckets()
    for i in bucket['Buckets']:
        try:
            #print("prepare clean bucket: " + i)
            result = client.list_objects_v2(
                Bucket=i['Name'],
            )
            if 'Contents' in result:
                for r in result['Contents']:
                    client.delete_object(
                        Bucket=i['Name'],
                        Key=r['Key']
                    )
            client.delete_bucket(
                Bucket=i['Name'],
            )
        except ClientError as e:
            print(e)
    print("Cleanup done!\n")
