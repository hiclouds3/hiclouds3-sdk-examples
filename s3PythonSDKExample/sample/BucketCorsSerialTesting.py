from botocore.exceptions import ClientError
from client import client


# test 1. Basic putBucket
#      2. put BucketLogging (put log Native & to target bucket)
#      3. get BucketLogging
#      4. Delete Bucket
def main(arg):
    try:
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-southeast-1'},
            Bucket=arg[0]
        )
        # print("SetBucketCORS")
        client.put_bucket_cors(
            Bucket=arg[0],
            CORSConfiguration={
                'CORSRules': [
                    {
                        'AllowedHeaders': [
                            '*',
                        ],
                        'AllowedMethods': [
                            'PUT',
                            'DELETE'
                        ],
                        'AllowedOrigins': [
                            'https://www.example.com',
                        ],
                        'ExposeHeaders': [
                            'x-amz-server-side-encryption',
                        ],
                        'MaxAgeSeconds': 3000
                    },
                    {
                        'AllowedHeaders': [
                            'Authorization',
                        ],
                        'AllowedMethods': [
                            'GET',
                            'HEAD'
                        ],
                        'AllowedOrigins': [
                            '*',
                        ],
                        'MaxAgeSeconds': 3000,
                    },
                ]
            },
        )
        # print("GetBucketCORS")
        response = client.get_bucket_cors(
            Bucket=arg[0],
        )
        # for i in response['CORSRules']:
        #    print('AllowedMethod : ' + repr (i))
        # print("DeleteBucketCORS")
        client.delete_bucket_cors(
            Bucket=arg[0],
        )
        #print("Clean up..")
        client.delete_bucket(
            Bucket=arg[0],
        )
        print("Cors Serial test done!\n")
    except ClientError as e:
        print(e)
