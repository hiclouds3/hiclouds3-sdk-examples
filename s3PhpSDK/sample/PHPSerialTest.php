<?php

/**
 * ���d�ҵ{�����ܽd�p��ϥ�hicloud S3 PHP SDK�o�e�򥻪�request��hicloud S3
 *
 * �e���G���������ohicloud S3�}�o�һݤ�AccessKey�PSecretKey
 * ���oAccessKey�PSecretKey���y�{�A�аѦ�"hicloud S3 Quick Start"��� :
 * https://userportal.hicloud.hinet.net/cloud/document/files/hicloud-S3-QuickStart.pdf
 *
 * ���n�G�b�B�榹�d�ҵ{���e�A�аȥ��T�{�w�N hicloud S3 Access Key �P Secret Key ��J test/client.php �ɮפ�
 */
require 'config.php';

error_reporting(0);

//Change test buckets' name here
$bucket = array("testphpbucket1","testphpbucket2","testphpbucket3");
$userAInfo = array($config[userACanonicalID],$config[userAMail]);
$userBInfo = array($config[userBCanonicalID],$config[userBMail]);
$ownerInfo = array($config[ownerCanonicalID],$config[ownerMail]);

echo "S3 PHP SDK Serial Test\nbucketname1: ".$bucket[0]." ,bucketname2: ".$bucket[1]." ,bucketname3: ".$bucket[2];
echo "\n-----------------------------------------------------------------------\n";

system("php ./Cleanup.php $bucket[0]");
system("php ./Cleanup.php $bucket[1]");
system("php ./Cleanup.php $bucket[2]");
sleep(5);

system("php ./ACLSerialTesting.php $bucket[0] $bucket[1] $bucket[2] $userAInfo[1] $ownerInfo[0]");
sleep(5);

system("php ./BucketLoggingSerialTesting.php $bucket[0] $bucket[1] $bucket[2] $ownerInfo[0]");
sleep(5);

system("php ./BucketSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php ./LifecycleSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php ./ObjectSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php ./PolicySerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

system("php ./VersioningSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
sleep(5);

// system("php ./WebsiteSerialTesting.php $bucket[0] $bucket[1] $bucket[2]");
// sleep(5);

// system("php ./MPUSerialTesting.php $bucket[0] $bucket[1] $bucket[2] $userAInfo[1] $userBInfo[0] $ownerInfo[0]");
// sleep(5);

// system("php ./DeleteMultipleObjects.php $bucket[0] $bucket[1] $bucket[2]");
// sleep(5);

// system("php ./BucketCorsSerialTest.php $bucket[0] $bucket[1] $bucket[2]");
// sleep(5);

// system("php ./BucketTaggingSerialTest.php $bucket[0] $bucket[1] $bucket[2]");
// sleep(5);

echo "\nS3 PHP SDK Serial Test Done!\n";
?>
