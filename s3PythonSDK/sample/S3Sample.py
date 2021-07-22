from client import client
#
# 此範例程式為示範如何使用hicloud S3 PHP SDK發送基本的request至hicloud S3
#
# 前提：必須先取得hicloud S3開發所需之AccessKey與SecretKey
# 取得AccessKey與SecretKey之流程，請參考"hicloud S3 Quick Start"文件 :
# https://userportal.hicloud.hinet.net/cloud/document/files/hicloud-S3-QuickStart.pdf
#
# 重要：在運行此範例程式前，請務必確認已將 hicloud S3 Access Key 與 Secret Key 填入client.py 檔案中
#
print("S3 Python SDK Serial Test\n")
print("-----------------------------------------------------------------------")

bucketName = "yuyuman"
fileName = 'test.txt'
#
# 創建Bucket - Bucket 名稱必須是唯一，若 Bucket 名稱已被其他使用者所使用時，將無法成功建立相同名稱的Bucket
#
print("Create Bukcet\n")
client.create_bucket(
    CreateBucketConfiguration={'LocationConstraint': 'ap-southeast-1'},
    Bucket=bucketName,
)
#
# 列出帳號下所有的Bucket
#
print("List All My Bukcet")
result = client.list_buckets()
for bucket in result['Buckets']:
    print(bucket['Name'] + '\n')

#
# 上傳Object到所建立的Bucket
# 在上傳檔案的同時，也能夠設定個人化metadata或ACL
#
print("Upload Object\n")
client.put_object(
    Body='Hello World!',
    ACL='public-read',
    Bucket=bucketName,
    Key=fileName,
)

#
# 下載Object - 當下載Object時，會將Object相關metadata及Object內容都下載回來
#
# GetObjectRequest也提供條件下載的選項，如可選擇Object修改時間在某時間後的Object下載，或選擇特定ETags的Object下載，或只下載部分Object
#
print("Download Object\n")
fp = open('download.test', 'wb+')
client.download_fileobj(bucketName, fileName, fp)
client.download_file(bucketName, fileName, 'download.test2')
client.upload_fileobj(fp, bucketName, fileName)
fp.close()
#
# 列出Bucket中所有Prefix為"te"的Object
#
print("List Object start with prefix 'te':")
result = client.list_objects_v2(
    Bucket=bucketName,
    Prefix="te"
)
for key in result['Contents']:
    print(key['Key']+'\n')

#
# 刪除Bucket中所有Object - 除非在Versioning機制開啟的情況下，任何刪除Object的動作都是無法回復的，因此必須謹慎的選擇欲刪除的Object
#
print("Delete All Objects in Bucket\n")
result = client.list_objects_v2(
    Bucket=bucketName,
)
if 'Contents' in result:
    for r in result['Contents']:
        client.delete_object(
            Bucket=bucketName,
            Key=r['Key']
        )

#
# 刪除Bucket - 欲刪除Bucket，此Bucket必須是已清空的，因此欲刪除Bucket前請先確認Bucket中是否仍存在任何Object
#
print("Delete Bukcet\n")
client.delete_bucket(
    Bucket=bucketName,
)


print("echo S3 Python SDK Serial Test Done!\n")
