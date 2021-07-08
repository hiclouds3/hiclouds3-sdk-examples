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
        '''print("Try putBucket with region 'ap-northeast-1':")
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
        print("Bucket permission: " + repr(result['Grants']))'''

        fileName = "apple.jpg"
        fileName2 = "photos/2006/January/sample.jpg"
        fileName3 = "photos/2006/February/sample2.jpg"
        fileName4 = "asset.txt"

        client.put_object(
            Body= 'Hello World!' ,
            Bucket=arg[0],
            Key=fileName
        )
        client.put_object(
            ACL='public-read',
            Body= 'Hello World!' ,
            Bucket=arg[0],
            Key=fileName2
        )
        '''key = bucket.new_key(fileName)
        key.set_contents_from_string('Hello World!')

        key2 = bucket.new_key(fileName2)
        key2.set_contents_from_string('Hello World!')
        key2.set_canned_acl('public-read')

        key3 = bucket.copy_key(fileName3, bucket.name, fileName2, preserve_acl=True, headers={
                               "Content-Disposition": "attachment; filename=\"default.txt\""})
        key3 = bucket.get_key(fileName3, response_headers={
                              "response-content-type": "text/plain"})
        # print repr(key3.content_disposition)

        key4 = bucket.new_key(fileName4)
        key4.set_contents_from_string('Hello World!')

        # print "Get bucket normally:"
    #     for key in bucket.list():
        # print " - "+key.name

        # print "Get bucket with prefix 'photos/':"
        result = bucket.list(prefix="photos/")
    #     for key in result:
    #         print " - "+key.name

        # print "Get bucket with delimiter '/':"
        result = bucket.list(delimiter="/")
    #     for key in result:
    #         print " - "+key.name

        # print "Get bucket with max_keys=2:"
        result = bucket.get_all_versions(max_keys=2)
    #     for key in result:
    #         print " - "+key.name
    #
        # print "Get bucket with delimiter '/' & prefix 'photos/2006/:"
        result = bucket.list(prefix="photos/2006/", delimiter="/")
    #     for key in result:
    #         print " - "+key.name
    #
        # print "Get bucket with marker 'apple.jpg':"
        result = bucket.list(marker='apple.jpg')
    #     for key in result:
    #         print " - "+key.name

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
        print(" - Bucket Serial Test done!\n")'''

    except ClientError as e:
        print(e.operation_name)
        print(e.response['Error']['Message'])
