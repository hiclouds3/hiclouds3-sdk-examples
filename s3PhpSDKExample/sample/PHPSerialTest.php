<?php

/**
 * 此範例程式為示範如何使用hicloud S3 PHP SDK發送基本的request至hicloud S3
 *
 * 前提：必須先取得hicloud S3開發所需之AccessKey與SecretKey
 * 取得AccessKey與SecretKey之流程，請參考"hicloud S3 Quick Start"文件 :
 * https://userportal.hicloud.hinet.net/cloud/document/files/hicloud-S3-QuickStart.pdf
 *
 * 重要：在運行此範例程式前，請務必確認已將 hicloud S3 Access Key 與 Secret Key 填入 client.php 檔案中
 */
require 'Cleanup.php';
require 'BucketSerialTest.php';
require 'BucketCorsSerialTest.php';
require 'ObjectSerialTest.php';
require 'MPUSerialTest.php';
require 'LifecycleSerialTest.php';
require 'DeleteMultipleObjectsTest.php';
require 'PolicySerialTest.php';
require 'VersioningSerialTest.php';
require 'WebsiteSerialTest.php';
require 'BucketTaggingSerialTest.php';
require 'ACLSerialTest.php';
require 'BucketLoggingSerialTest.php';

date_default_timezone_set('Asia/Taipei');
error_reporting(E_ALL);

$buckets = ["testphpbucket1", "testphpbucket2", "testphpbucket3"];

echo "S3 PHP SDK Serial Test\nbucketname1: " . $buckets[0] . " ,bucketname2: " . $buckets[1] . " ,bucketname3: " . $buckets[2];
echo "\n-----------------------------------------------------------------------\n";

# clean up buckets
function cleanup()
{
    global $buckets;
    foreach ($buckets as $bucket) {
        cleanBucketSuppressed($bucket);
    }
    echo "\n";
}

# bucket serial test
cleanup();
bucketSerialTest($buckets[0]);
echo "\n";

# bucket cors serial test
cleanup();
bucketCorsSerialTest($buckets[1]);
echo "\n";

# object serial test
cleanup();
objectSerialTest($buckets[0], $buckets[1]);
echo "\n";

# mpu serial test
cleanup();
mpuSerialTest($buckets[0]);
echo "\n";

# lifecycle serial test
cleanup();
lifecycleSerialTest($buckets[0]);
echo "\n";

# delete multiple objects
cleanup();
deleteMultipleObjectsTest($buckets[1]);
echo "\n";

# policy serial test
cleanup();
policySerialTest($buckets[2]);
echo "\n";

# versioning serial test
cleanup();
versioningSerialTest($buckets[0]);
echo "\n";

# website serial test
cleanup();
websiteSerialTest($buckets[1]);
echo "\n";

# bucket tagging serial test
cleanup();
bucketTaggingSerialTest($buckets[2]);
echo "\n";

# acl serial test
cleanup();
aclSerialTest($buckets[0]);
echo "\n";

# bucket logging
cleanup();
bucketLoggingSerialTest($buckets[0], $buckets[1]);
echo "\n";
cleanup();
echo "\nS3 PHP SDK Serial Test Done!\n";
