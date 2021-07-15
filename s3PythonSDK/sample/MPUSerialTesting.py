from botocore.exceptions import ClientError
from client import client

# test 1.Basic Initiate MPU
#      2.Upload part from file
#      3.Complete MPU
#      4.Abort MPU
#      5.Copy part from Object
#      6.Basic list MPU & with parameter
#      7.Basci list parts & with parameters


def main(arg, filePath):
    try:
        eTag = []
        client.create_bucket(
            CreateBucketConfiguration={'LocationConstraint': 'ap-southeast-1'},
            Bucket=arg[1],
        )
        # Basic MPU
        # print('Initiate MPU for castle.jpg')
        responed = client.create_multipart_upload(
            ACL="public-read",
            Bucket=arg[1],
            ContentEncoding='UTF-8',
            Key='castle.jpg',
        )
        # print("uploading part1")
        fp = open(filePath[0], 'rb')
        response = client.upload_part(
            Body=fp,
            Bucket=arg[1],
            Key='castle.jpg',
            PartNumber=1,
            UploadId=responed['UploadId'],
        )
        eTag.append(response['ETag'])
        fp.close()
        # print("uploading part2")
        fp = open(filePath[1], 'rb')
        response = client.upload_part(
            Body=fp,
            Bucket=arg[1],
            Key='castle.jpg',
            PartNumber=2,
            UploadId=responed['UploadId'],
        )
        eTag.append(response['ETag'])
        fp.close()
        # print("uploading part3")
        fp = open(filePath[2], 'rb')
        response = client.upload_part(
            Body=fp,
            Bucket=arg[1],
            Key='castle.jpg',
            PartNumber=3,
            UploadId=responed['UploadId'],
        )
        eTag.append(response['ETag'])
        fp.close()
        # print("Complete MPU..")
        client.complete_multipart_upload(
            Bucket=arg[1],
            Key='castle.jpg',
            MultipartUpload={
                'Parts': [
                            {
                                'ETag': eTag[0],
                                'PartNumber': 1,
                            },
                    {
                                'ETag': eTag[1],
                                'PartNumber': 2,
                            },
                    {
                                'ETag': eTag[2],
                                'PartNumber': 3,
                            },
                ],
            },
            UploadId=responed['UploadId'],
        )
        # print("Show Object & Clean up..")
        result = client.list_objects_v2(
            Bucket=arg[1],
        )
        if 'Contents' in result:
            for r in result['Contents']:
                client.delete_object(
                    Bucket=arg[1],
                    Key=r['Key']
                )
        # print("Basic MPU test done\n-------------------------------------------------")

        # Abort MPU
        # print("Test Abort MPU")
        responed = client.create_multipart_upload(
            Bucket=arg[1],
            Key='testAbort.jpg',
        )
        result = client.list_multipart_uploads(
            Bucket=arg[1],
        )
        # print("Bucket :" + arg[1])
        uploaddata = result['Uploads']
        # for i in uploaddata:
        #    print("Key :" + repr(i['Key']))
        client.abort_multipart_upload(
            Bucket=arg[1],
            Key='testAbort.jpg',
            UploadId=responed['UploadId'],
        )

        # print("Basic Abort MPU test done\n-------------------------------------------------")



        # Copy parts from file
        # print("\nTest copy part from Obj")
        client.put_object(
            Body = 'Hello World!',
            Bucket = arg[1],
            Key = 'test1.txt',
        )
        responed=client.create_multipart_upload(
            Bucket = arg[1],
            Key = 'test2.txt',
        )
        responed2=client.create_multipart_upload(
            Bucket = arg[1],
            Key = 'test3.txt',
        )
        client.upload_part_copy(
            Bucket = arg[1],
            CopySource = arg[1]+'/test1.txt',
            Key = 'test2.txt',
            PartNumber = 2,
            UploadId = responed['UploadId'],
        )
        client.upload_part_copy(
            Bucket = arg[1],
            CopySource = arg[1]+'/test1.txt',
            Key = 'test2.txt',
            PartNumber = 3,
            UploadId = responed['UploadId'],
        )
        client.upload_part_copy(
            Bucket = arg[1],
            CopySource = arg[1]+'/test1.txt',
            Key = 'test3.txt',
            PartNumber = 2,
            UploadId = responed2['UploadId'],
        )
        # print("\nget MPU with max_uploads=2:")
        result=client.list_multipart_uploads(
            Bucket=arg[1],
            MaxUploads=2,
        )
        # print("Bucket :" + arg[1])
        uploaddata = result['Uploads']
        # for i in uploaddata:
        #    print("Key :" + repr(i['Key']))
        # print("\nget MPU with key_marker=test2.txt:")
        result = client.list_multipart_uploads(
            Bucket=arg[1],
            KeyMarker='test2.txt',
        )
        # print("Bucket :" + arg[1])
        uploaddata = result['Uploads']
        # for i in uploaddata:
        #    print("Key :" + repr(i['Key']))
        # print("\nGet part with max_parts=2 :")
        result = client.list_parts(
            Bucket=arg[1],
            MaxParts=2,
            Key='test2.txt',
            UploadId=responed['UploadId'],
        )
        # for i in result['Parts']:
        #    print(result['Key']+".part"+repr (i['PartNumber']))
        # print("Get part with part_number_marker=2:")
        result = client.list_parts(
            Bucket=arg[1],
            PartNumberMarker=2,
            Key='test2.txt',
            UploadId=responed['UploadId'],
        )
        # for i in result['Parts']:
        #    print(result['Key']+".part"+repr (i['PartNumber']))
        # print("\nClean up..")
        # clear bucket
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
        print("MPU Serial Test Done!\n")

    except ClientError as e:
        print("Error operation : " + e.operation_name)
        print("Error code : " + e.response['Code'])
        print("Error response : " + e.response['Message'])
