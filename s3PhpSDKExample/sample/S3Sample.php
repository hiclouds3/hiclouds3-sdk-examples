<?php
require './vendor/autoload.php';
require './client.php';

use Aws\S3\S3Client;
use Aws\S3\Exception\S3Exception;
use Aws\Exception\AwsException;
use GuzzleHttp\Exception\RequestException;

/**
 * 此範例程式為示範如何使用hicloud S3 PHP SDK發送基本的request至hicloud S3
 *
 * 前提：必須先取得hicloud S3開發所需之AccessKey與SecretKey
 * 取得AccessKey與SecretKey之流程，請參考"hicloud S3 Quick Start"文件 :
 * https://userportal.hicloud.hinet.net/cloud/document/files/hicloud-S3-QuickStart.pdf
 *
 * 重要：在運行此範例程式前，請務必確認已將 hicloud S3 Access Key 與 Secret Key 填入 sample/client.php 檔案中
 */

echo "S3 PHP SDK Serial Test";
echo "\n-----------------------------------------------------------------------\n";


/**
 * 建立暫存檔，用於範例中上傳至hicloud S3
 */
function createSampleFile()
{
	$temp = tmpfile();
	$content = "abcdefghijklmnopqrstuvwxyz<br>01234567890112345678901234<br>!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234<br>abcdefghijklmnopqrstuvwxyz<br>";
	fwrite($temp, $content);
	fseek($temp, 0);
	return $temp;
}


global $client;
$bucketname = "jeffusertest1";
$objName = "test.txt";

/**
 * 創建Bucket - Bucket 名稱必須是唯一，若 Bucket 名稱已被其他使用者所使用時，將無法成功建立相同名稱的Bucket
 */
echo "Create Bucket\n";
$client->createBucket([
	'Bucket' => $bucketname,
]);

/**
 * 列出帳號下所有的Bucket
 */
echo "List All My Bucket\n";
$result = $client->listBuckets();
$array = [];

foreach ($result['Buckets'] as $bucket) {
	echo " - " . $bucket['Name'] . "\n";
	$array[] = $bucket['Name'];
}

/**
 * 上傳Object到所建立的Bucket
 * 在上傳檔案的同時，也能夠設定個人化metadata，如content-type、content-encoding等metadata
 */
echo "Upload Object\n";
$client->putObject([
	'Bucket' => $bucketname,
	'Key' => $objName,
	'Body' => createSampleFile(),
	'ACL' => 'private',
	'Metadata' => [
		'flower' => 'lily',
		'color' => 'pink'
	],
	'ContentType' => "text/plain",
	'ContentLength' => '150',
	'ContentDisposition' => "attachment; filename=\"default.txt\"",
	'ContentMD5' => 'movf4FeaK/4LQyz5FP1oiQ=='
]);

/**
 * 下載Object - 當下載Object時，會將Object相關metadata及Object內容都下載回來
 *
 * GetObjectRequest也提供條件下載的選項，如可選擇Object修改時間在某時間後的Object下載，或選擇特定ETags的Object下載，或只下載部分Object
 */
echo "Download Object\n";
$result = $client->getObject([
	'Bucket' => $bucketname,
	'Key' => $objName
]);
$a = str_replace('"', "", $result['ETag']);
echo "Object ETag: " . $a . "\n";

/**
 * 列出Bucket中所有Prefix為"Te"的Object
 */
echo "List Object start with \"te\" \n";
$prefix = "te";
$result = $client->listObjects([
	'Bucket' => $bucketname,
	'Prefix' => $prefix
]);
$count = 0;
if (!empty($result['Contents'])) {
	foreach ($result['Contents'] as $obj) {
		echo " - " . $obj['Key'] . "\n";
		$count++;
	}
}
echo "Total: $count objects\n";

/**
 * 刪除Object - 除非在Versioning機制開啟的情況下，任何刪除Object的動作都是無法回復的，因此必須謹慎的選擇欲刪除的Object
 */
echo "Delete Object\n";
$client->deleteObject([
	'Bucket' => $bucketname,
	'Key' => $objName
]);

/**
 * 刪除Bucket - 欲刪除Bucket，此Bucket必須是已清空的，因此欲刪除Bucket前請先確認Bucket中是否仍存在任何Object
 */
try {
	echo "Delete Bucket\n";
	$client->deleteBucket(['Bucket' => $bucketname]);
} catch (S3Exception $e) {
	echo "Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.\n";
	echo "Error Message:    " . $e->getMessage() . "\n";
	echo "HTTP Status Code: " . $e->getStatusCode() . "\n";
	echo "AWS Error Code:   " . $e->getAwsErrorCode() . "\n";
	echo "Error Type:       " . $e->getAwsErrorType() . "\n";
	echo "Request ID:       " . $e->getAwsRequestId() . "\n";
} catch (AwsException $e) {
	echo "Caught an AwsException, which represents an error that occurred while processing the request.\n";
	echo "Error Message:    " . $e->getMessage() . "\n";
} catch (RequestException $e) {
	echo "Validation Error: " . $e->getMessage() . "\n";
}

echo "\nS3 PHP SDK Serial Test Done!\n";
?>