import sys
from botocore.exceptions import ClientError
from client import client

# test 1.Basic put bucket
#      2.put Object with header & custom metadata
#      3.head object
#      4.get object
#      5.copy object (with preserved ACL)


def percent_cb(complete, total):
    sys.stdout.write('.')
    sys.stdout.flush()


def main(arg):
    try:
        content = "abcdefghijklmnopqrstuvwxyz<br>01234567890112345678901234<br>!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234<br>abcdefghijklmnopqrstuvwxyz<br>"

        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-northeast-1'},
            Bucket=arg[0],
        )
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-northeast-1'},
            Bucket=arg[1],
        )
        print("Put Object 'test.txt' with Headers & custom metadata..")

        client.put_object(
            Body=content,
            ACL='public-read',
            Bucket=arg[0],
            CacheControl='no-cache',
            ContentDisposition='attachment;filename=\'default.txt\'',
            ContentEncoding='UTF-8',
            ContentType='text/plain',
            Key='test.txt',
            Metadata={
                'x-amz-meta-flower': 'lily',
                'x-amz-meta-color': 'pink'
            },
        )
        response = client.get_object(
            Bucket=arg[0],
            Key='test.txt',
        )
        #print("Cache-Control: " + repr(response['ResponseMetadata']['HTTPHeaders']['cache-control']))
        # print("Content-Type"+repr(response['ResponseMetadata']['HTTPHeaders']['content-type']))
        #print("Content-Encoding: "+repr(response['ResponseMetadata']['HTTPHeaders']['content-encoding']))
        #print("Content-Disposition: "+repr(response['ResponseMetadata']['HTTPHeaders']['content-disposition']))
        #print("x-amz-meta-x-amz-meta-flower: "+repr(response['ResponseMetadata']['HTTPHeaders']['x-amz-meta-x-amz-meta-flower']))
        #print("x-amz-meta-x-amz-meta-color: "+repr(response['ResponseMetadata']['HTTPHeaders']['x-amz-meta-x-amz-meta-color']))
        #print("\nCopy yuyuman1://test.txt to yuyuman2 as 'cptest' and preserve_acl..")
        url = client.generate_presigned_url(
            'get_object',
            Params={
                'Bucket': arg[0],
                'Key': 'test.txt'
            },
            ExpiresIn=120,
        )
        # print(url)
        client.copy_object(
            ACL='public-read',
            Bucket=arg[1],
            CopySource=arg[0]+'/test.txt',
            Key='cptest',
        )
        response = client.get_object(
            Bucket=arg[1],
            Key='cptest',
        )
        #print("\nGet file content as string..\n    " +response['Body'].read().decode("utf-8") )
        fp = open('download.test', 'wb+')
        client.download_fileobj(arg[0], 'test.txt', fp)
        client.download_file(arg[0], 'test.txt', 'download.test2')
        client.upload_fileobj(fp, arg[0], 'test.txt')
        fp.close()
        f2 = open('testwrite', 'wb+')
        client.upload_fileobj(f2, arg[0], 'test.txt')
        f2.close()
        client.upload_file('download.test2', arg[0], 'test.txt')
        #print("\nClean up..")
        # clear bucket
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
        result = client.list_objects_v2(
            Bucket=arg[1],
        )
        if 'Contents' in result:
            for r in result['Contents']:
                client.delete_object(
                    Bucket=arg[1],
                    Key=r['Key']
                )
        client.delete_bucket(
            Bucket=arg[1],
        )

        print("Object Serial Test done!\n")
    except ClientError as e:
        print("Error operation : " + e.operation_name)
        print("Error response : " + e.response['Error']['Message'])
