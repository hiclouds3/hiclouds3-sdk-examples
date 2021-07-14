from botocore.exceptions import ClientError
from client import client


def main(arg):
    try:
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-northeast-1'},
            Bucket=arg[0],
        )
        # print("SetBucketTags")
        client.put_bucket_tagging(
            Bucket=arg[0],
            Tagging={
                'TagSet': [
                    {
                        'Key': 'key',
                        'Value': 'value'
                    },
                ]
            },
        )
        # print("get_tags")
        response = client.get_bucket_tagging(
            Bucket=arg[0],
        )

        #print(repr (response['TagSet']))
        # print("delete_tags")
        client.delete_bucket_tagging(
            Bucket=arg[0]
        )
        #print("Clean up..")
        client.delete_bucket(
            Bucket=arg[0],
        )
        print("Bucket Tagging Serial test done!\n")
    except ClientError as e:
        print("Error operation : " + e.operation_name)
        print("Error response : " + e.response['Error']['Message'])
