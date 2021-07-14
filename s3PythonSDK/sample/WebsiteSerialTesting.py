from botocore.exceptions import ClientError
from client import client


def main(arg):
    try:
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-northeast-1'},
            Bucket=arg[0],
        )
        client.put_object(
            ACL='public-read',
            Body='404testchttl<br><title>chttl</title>',
            Bucket=arg[0],
            Key='error.html',
        )
        client.put_object(
            ACL='public-read',
            Body='<title>chttl</title>Hello World!',
            Bucket=arg[0],
            Key='index.html',
        )
        #print("Put Website Configurations..")
        client.put_bucket_website(
            Bucket=arg[0],
            WebsiteConfiguration={
                'ErrorDocument': {
                    'Key': 'error.html'
                },
                'IndexDocument': {
                    'Suffix': 'index.html'
                },

            },
        )
        #print("Get Website Configurations..")
        response = client.get_bucket_website(
            Bucket=arg[0],
        )
        #print('IndexDocument : ' + repr (response['IndexDocument']))
        #print('ErrorDocument : ' + repr (response['ErrorDocument']))
        #print("Delete Website Configurations..\n")
        client.delete_bucket_website(
            Bucket=arg[0],
        )
        client.put_bucket_website(
            Bucket=arg[0],
            WebsiteConfiguration={
                'IndexDocument': {
                    'Suffix': 'index.html'
                },
                'RoutingRules': [
                    {
                        'Redirect': {
                            'HostName': 'www.google.com',
                            'Protocol': 'https',
                        }
                    },
                ]
            },
        )
        #print("Put Website Configurations with redirect all request")
        #print("Get Website Configurations..")
        response = client.get_bucket_website(
            Bucket=arg[0],
        )
        #print('IndexDocument : ' + repr (response['IndexDocument']))
        #print('RoutingRules : ' + repr (response['RoutingRules']))
        #print("Delete Website Configurations..\n")
        client.delete_bucket_website(
            Bucket=arg[0],
        )

        #print("\nClean up..")
        result = client.list_objects_v2(
            Bucket=arg[0],
        )
        if 'Contents' in result:
            for r in result['Contents']:
                client.delete_object(
                    Bucket=arg[0],
                    Key=r['Key']
                )
        client.delete_bucket(
            Bucket=arg[0],
        )
        print("Website Serial Test Done ! \n")
    except ClientError as e:
        print("Error operation : " + e.operation_name)
        print("Error response : " + e.response['Error']['Message'])
