import os
import time
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
import configparser

# Change test buckets' name here
buckets = ["yuyuman1", "yuyuman2", "yuyuman3"]

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

os.system('echo cleanup ') 
cleanup.main()
time.sleep(5)

os.system('echo ACLSerialTesting ') 
ACLSerialTesting.main(buckets, userAInfo, userBInfo, ownerInfo)
time.sleep(5)

os.system('echo BucketLoggingSerialTesting ') 
BucketLoggingSerialTesting.main(buckets, ownerInfo);
time.sleep(5)

os.system('echo BucketSerialTesting ')
BucketSerialTesting.main(buckets)
time.sleep(5)

os.system('echo LifecycleSerialTesting ') 
LifecycleSerialTesting.main(buckets);
time.sleep(5)
    
os.system('echo MPUSerialTesting ') 
MPUSerialTesting.main(buckets, filePath);
time.sleep(5)
   
os.system('echo ObjectSerialTesting ') 
ObjectSerialTesting.main(buckets);
time.sleep(5)
    
os.system('echo PolicySerialTesting ') 
PolicySerialTesting.main(buckets);
time.sleep(5)
    
os.system('echo VersioningSerialTesting ')
VersioningSerialTesting.main(buckets);
time.sleep(5)
  
os.system('echo WebsiteSerialTesting ') 
WebsiteSerialTesting.main(buckets);
time.sleep(5)
  
os.system('echo CorsSerialTesting ') 
BucketCorsSerialTesting.main(buckets);
time.sleep(5)

os.system('echo BucketTaggingSerialTesting')
BucketTaggingSerialTesting.main(buckets);
time.sleep(5)

os.system('echo S3 Python SDK Serial Test Done!')
