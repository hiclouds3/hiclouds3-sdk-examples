import os
import time
import configparser

import ACLSerialTesting
import BucketLoggingSerialTesting
import BucketSerialTesting
import LifecycleSerialTesting
import MPUSerialTesting
import ObjectSerialTesting
import PolicySerialTesting
import VersioningSerialTesting
import WebsiteSerialTesting
import BucketCorsSerialTesting
import BucketTaggingSerialTesting
import cleanup

# Change test buckets' name here
buckets = ["testpythonbucket1", "testpythonbucket2", "testpythonbucket3"]

config = configparser.ConfigParser()
config.read('Config.ini')
userAInfo = [config.get('Section_A', 'userACanonicalID'),
             config.get('Section_A', 'userAMail')]
userBInfo = [config.get('Section_A', 'userBCanonicalID'),
             config.get('Section_A', 'userBMail')]
ownerInfo = [config.get('Section_A', 'ownerCanonicalID'),
             config.get('Section_A', 'ownerMail')]
# Change test filePath's name here
filePath = ["castle.jpg.001","castle.jpg.002","castle.jpg.003"]

print("S3 Python SDK Serial Test-\nbucketname1:" +
      buckets[0] + " ,bucketname2: " + buckets[1])
print("-----------------------------------------------------------------------")

print('cleanup') 
cleanup.main()
time.sleep(5)

print('ACLSerialTesting') 
ACLSerialTesting.main(buckets, userAInfo, userBInfo, ownerInfo)
time.sleep(5)

print('BucketLoggingSerialTesting') 
BucketLoggingSerialTesting.main(buckets, ownerInfo)
time.sleep(5)

print('BucketSerialTesting')
BucketSerialTesting.main(buckets)
time.sleep(5)

print('LifecycleSerialTesting') 
LifecycleSerialTesting.main(buckets)
time.sleep(5)
    
print('MPUSerialTesting') 
MPUSerialTesting.main(buckets, filePath)
time.sleep(5)
   
print('ObjectSerialTesting') 
ObjectSerialTesting.main(buckets)
time.sleep(5)
    
print('PolicySerialTesting') 
PolicySerialTesting.main(buckets)
time.sleep(5)
    
print('VersioningSerialTesting')
VersioningSerialTesting.main(buckets)
time.sleep(5)
  
print('WebsiteSerialTesting') 
WebsiteSerialTesting.main(buckets)
time.sleep(5)
  
print('CorsSerialTesting') 
BucketCorsSerialTesting.main(buckets)
time.sleep(5)

print('BucketTaggingSerialTesting')
BucketTaggingSerialTesting.main(buckets)
time.sleep(5)

print('S3 Python SDK Serial Test Done!')
