<pre>
<?php

/**
 * 此範例程式為示範如何使用hicloud S3 PHP SDK發送基本的request至hicloud S3
 *
 * 前提：必須先取得hicloud S3開發所需之AccessKey與SecretKey
 * 取得AccessKey與SecretKey之流程，請參考"hicloud S3 Quick Start"文件 :
 * https://userportal.hicloud.hinet.net/cloud/document/files/hicloud-S3-QuickStart.pdf
 *
 * 重要：在運行此範例程式前，請務必確認已將 hicloud S3 Access Key 與 Secret Key 填入 test/client.php 檔案中
 */
require 'config.php';

error_reporting(0);

//Change test buckets' name here
$bucket = array("allentest1","allentest2","allentest");
$userAInfo = array($config[userACanonicalID],$config[userAMail]);
$userBInfo = array($config[userBCanonicalID],$config[userBMail]);
$ownerInfo = array($config[ownerCanonicalID],$config[ownerMail]);

echo "S3 PHP SDK Serial Test-\nbucketname1: ".$bucket[0]." ,bucketname2: ".$bucket[1]." ,bucketname3: ".$bucket[2];
echo "\n-----------------------------------------------------------------------\n";

system("php sample/Cleanup.php $bucket[0]");
system("php sample/Cleanup.php $bucket[1]");
system("php sample/Cleanup.php $bucket[2]");
sleep(5);

/*system("php sample/ACLSerialTesting.php $bucket[0] $bucket[1] $bucket[2] $userAInfo[1] $ownerInfo[0]");
sleep(5);

system("php sample/BucketLoggingSerialTesting.php $bucket[0] $bucket[1] $bucket[2] $ownerInfo[0]");
sleep(5);

system("php sample/BucketSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php sample/LifecycleSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php sample/ObjectSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php sample/PolicySerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php sample/VersioningSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php sample/WebsiteSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php sample/MPUSerialTesting.php $bucket[0] $bucket[1] $bucket[2] $userAInfo[1] $userBInfo[0] $ownerInfo[0]");
sleep(5);

system("php sample/DeleteMultipleObjects.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php sample/BucketCorsSerialTest.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);*/

system("php sample/BucketTaggingSerialTest.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

echo "\nS3 PHP SDK Serial Test Done!";
?>
</pre>