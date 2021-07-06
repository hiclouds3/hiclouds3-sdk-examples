import sys
import boto
import boto.exception 
from boto.s3.key import Key
from boto.s3.connection import Location
from boto.s3.acl import CannedACLStrings
from client import conn 
from xml.dom import minidom

def percent_cb(complete, total):
    sys.stdout.write('.')
    sys.stdout.flush()

#
# 此範例程式為示範如何使用hicloud S3 PHP SDK發送基本的request至hicloud S3
#
# 前提：必須先取得hicloud S3開發所需之AccessKey與SecretKey
# 取得AccessKey與SecretKey之流程，請參考"hicloud S3 Quick Start"文件 :
# https://userportal.hicloud.hinet.net/cloud/document/files/hicloud-S3-QuickStart.pdf
#
# 重要：在運行此範例程式前，請務必確認已將 hicloud S3 Access Key 與 Secret Key 填入client.py 檔案中
#

print "S3 Python SDK Serial Test\n"
print "-----------------------------------------------------------------------"

bucketName="allentest1"
fileName = "test.txt"
#
# 創建Bucket - Bucket 名稱必須是唯一，若 Bucket 名稱已被其他使用者所使用時，將無法成功建立相同名稱的Bucket
#    
print "Create Bukcet\n"
bucket=conn.create_bucket(bucketName)

#
# 列出帳號下所有的Bucket
#
print "List All My Bukcet\n"
result = conn.get_all_buckets()
for b in result:
    print "-" + b.name
    
#
# 上傳Object到所建立的Bucket
# 在上傳檔案的同時，也能夠設定個人化metadata或ACL
#
print "Upload Object\n"
key = bucket.new_key(fileName)
key.set_contents_from_string('Hello World!')
key.set_canned_acl('public-read')
    
#
# 下載Object - 當下載Object時，會將Object相關metadata及Object內容都下載回來
#
# GetObjectRequest也提供條件下載的選項，如可選擇Object修改時間在某時間後的Object下載，或選擇特定ETags的Object下載，或只下載部分Object
#
# 此範例程式示範將Object載回，命名為download.test並存於D槽中
print "Download Object\n"
fp = open('download.test', 'w')
key.get_contents_to_file(fp,cb=percent_cb,num_cb=10,headers={"testdown":"load"},response_headers={"response-content-type":"text/plain"}) #,response_headers={"Content-Type":"text/plain"}
key.get_contents_to_filename('D:/download.test2',cb=percent_cb,num_cb=10,headers={"testdown":"load"},response_headers={"response-content-type":"text/plain"}) #,response_headers={"Content-Type":"text/plain"}
key.get_file(fp,cb=percent_cb,num_cb=10,headers={"testdown":"load"},override_num_retries=2,response_headers={"response-content-type":"text/plain"}) #,response_headers={"Content-Type":"text/plain"}
                      
#
# 列出Bucket中所有Prefix為"te"的Object
#
print "List Object start with prefix 'te':"
result=bucket.list(prefix="te")
for key in result:
    print " - "+key.name
      
#
# 刪除Bucket中所有Object - 除非在Versioning機制開啟的情況下，任何刪除Object的動作都是無法回復的，因此必須謹慎的選擇欲刪除的Object     
#
print "Delete All Objects in Bucket\n"
result = bucket.get_all_keys()
bucket.delete_keys(result,quiet=True)
             
#
# 刪除Bucket - 欲刪除Bucket，此Bucket必須是已清空的，因此欲刪除Bucket前請先確認Bucket中是否仍存在任何Object
#    
print "Delete Bukcet\n"
conn.delete_bucket(bucket)
        

print "echo S3 Python SDK Serial Test Done!\n"

        
