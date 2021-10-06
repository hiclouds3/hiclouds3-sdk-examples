from botocore.exceptions import ClientError
from client import client
# test 1.Basic put bucket
#      2.put lifecycle rules(expire in days & on date)
#      3.get lifecycle
#      4.delete lifecycle
#      5.put lifecycle rules(transistion in day)
#      6.get lifecycle
#      7.delete lifecycle
#      8.delete bucket


def main(arg):
    try:
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-southeast-1'},
            Bucket=arg[0],
        )
        client.put_bucket_lifecycle(
            Bucket=arg[0],
            LifecycleConfiguration={
                'Rules': [
                    {
                        'Expiration': {
                            'Date': '20210710T00:00:00.000Z',
                        },
                        'Prefix': '/',
                        'ID': 'testLC1',
                        'Status': 'Enabled'
                    },
                    {
                        'Expiration': {
                            'Days': 1,
                        },
                        'Prefix': 'test.txt',
                        'ID': 'testLC2',
                        'Status': 'Enabled'
                    },
                ],
            },
        )
        result = client.get_bucket_lifecycle(
            Bucket=arg[0],
        )
        # for rules in result['Rules']:
        #    print("Rule ID: "+rules['ID'])
        #    print("prefix: "+rules['Prefix'])
        #    print("Expiration: "+repr(rules['Expiration']))
        #print("Clean up..\n")
        client.delete_bucket_lifecycle(
            Bucket=arg[0],
        )
        client.put_bucket_lifecycle(
            Bucket=arg[0],
            LifecycleConfiguration={
                'Rules': [
                    {
                        'Prefix': '/',
                        'ID': 'testLC3',
                        'Status': 'Enabled',
                        'Transition': {
                            'Days': 1,
                            'StorageClass': 'GLACIER'
                        },
                    },
                ],
            },
        )
        result = client.get_bucket_lifecycle(
            Bucket=arg[0],
        )
        # for rules in result['Rules']:
        #    print("Rule ID: "+rules['ID'])
        #    print("prefix: "+repr (rules['Prefix']))
        #    print("Transition: "+repr(rules['Transition']))
        #print("Clean up..\n")
        client.delete_bucket_lifecycle(
            Bucket=arg[0],
        )
        client.delete_bucket(
            Bucket=arg[0],
        )
        print("Lifecycle Serial Test Done ! \n")
    except ClientError as e:
        print(e)
