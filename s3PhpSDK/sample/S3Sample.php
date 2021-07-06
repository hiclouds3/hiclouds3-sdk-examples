<?php
require 'sample/client.php';

use Aws\S3\S3Client;
use Aws\S3\Enum\CannedAcl;
use Aws\S3\Exception\S3Exception;
use Aws\S3\Model\ClearBucket;
use Guzzle\Common\Exception\ExceptionCollection;
use Aws\S3\Enum\Permission;
use Aws\S3\Enum\GranteeType;

/**
 * ���d�ҵ{�����ܽd�p��ϥ�hicloud S3 PHP SDK�o�e�򥻪�request��hicloud S3
 *
 * �e���G���������ohicloud S3�}�o�һݤ�AccessKey�PSecretKey
 * ���oAccessKey�PSecretKey���y�{�A�аѦ�"hicloud S3 Quick Start"��� :
 * https://userportal.hicloud.hinet.net/cloud/document/files/hicloud-S3-QuickStart.pdf
 *
 * ���n�G�b�B�榹�d�ҵ{���e�A�аȥ��T�{�w�N hicloud S3 Access Key �P Secret Key ��J sample/client.php �ɮפ�
 */

echo "S3 PHP SDK Serial Test";
echo "\n-----------------------------------------------------------------------\n";


/**
 * �إ߼Ȧs�ɡA�Ω�d�Ҥ��W�Ǧ�hicloud S3
 */
function createSampleFile()
{
	$temp = tmpfile();
	$content = "abcdefghijklmnopqrstuvwxyz<br>01234567890112345678901234<br>!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234<br>abcdefghijklmnopqrstuvwxyz<br>";
	fwrite($temp, $content);
	fseek($temp, 0);
	return $temp;
	fclose($temp); // this removes the file
}


global $client;
$bucketname = "usertest1";
$objName="test.txt";

/**
* �Ы�Bucket - Bucket �W�٥����O�ߤ@�A�Y Bucket �W�٤w�Q��L�ϥΪ̩ҨϥήɡA�N�L�k���\�إ߬ۦP�W�٪�Bucket
*/
echo "Create Bucket\n";
$client->createBucket(array(
		'Bucket' => $bucketname ,
));

/**
* �C�X�b���U�Ҧ���Bucket
*/
echo "List All My Bucket\n";
$result = $client->listBuckets();
$array = array();

foreach ($result['Buckets'] as $bucket) {
	echo " - ". $bucket['Name'] . "\n";
	array_push($array, $bucket['Name']);
}

/**
* �W��Object��ҫإߪ�Bucket
* �b�W���ɮת��P�ɡA�]����]�w�ӤH��metadata�A�pcontent-type�Bcontent-encoding��metadata
*/
echo "Upload Object\n";
$client->putObject(array(
		'Bucket' => $bucketname,
		'Key'    => $objName,
		'Body'   => createSampleFile(),
		'ACL'	 => CannedAcl::PUBLIC_READ,
		'command.headers' => array(
				'x-amz-meta-flower' => 'lily',
				'x-amz-meta-color' => "pink"
		),
		'ContentType' => "text/plain",
		'ContentLength' => '150',
		'ContentEncoding' => "UTF-8",
		'ContentDisposition'=> "attachment; filename=\"default.txt\"",
		'CacheControl' => "no-cache",
		'ContentMD5'=>'movf4FeaK/4LQyz5FP1oiQ=='
));

/**
* �U��Object - ��U��Object�ɡA�|�NObject����metadata��Object���e���U���^��
*
* GetObjectRequest�]���ѱ���U�����ﶵ�A�p�i���Object�ק�ɶ��b�Y�ɶ��᪺Object�U���A�ο�ܯS�wETags��Object�U���A�Υu�U������Object
*/
echo "Download Object\n";
$result=$client->getObject(array(
		'Bucket' => $bucketname,
		'Key'=> $objName
));
$a=str_replace('"', "", $result['ETag']);
echo "Object ETag: ".$a."\n";

/**
* �C�XBucket���Ҧ�Prefix��"Te"��Object
*/
echo "List Object start with \"te\" \n";
$prefix="te";
$result = $client->listObjects( array(
		'Bucket' 	=> $bucketname ,
		'Prefix' 	=> $prefix
));
$count=0;
if($result['Contents'] != null){
	foreach ( $result['Contents'] as $obj){
		echo " - ".$obj['Key']."\n";
		$count++;
	}
}
echo "Total: $count objects\n";

/**
* �R��Object - ���D�bVersioning����}�Ҫ����p�U�A����R��Object���ʧ@���O�L�k�^�_���A�]�������ԷV����ܱ��R����Object
*/
echo "Delete Object\n";
$client->deleteObject(array(
		'Bucket' => $bucketname,
		'Key'	 => $objName
));

/**
* �R��Bucket - ���R��Bucket�A��Bucket�����O�w�M�Ū��A�]�����R��Bucket�e�Х��T�{Bucket���O�_���s�b����Object
*/
try
{
	echo "Delete Bucket\n";
	$client->deleteBucket(array('Bucket' => $bucketname));
}
catch (S3Exception $e) {
	echo "<font color=red>�I</font>Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.<br>";
	echo "Error Message:    " . $e->getMessage()."<br>";
	echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
	echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
	echo "Error Type:       " . $e->getExceptionType()."<br>";
	echo "Request ID:       " . $e->getRequestId()."<br>";
}
catch (ExceptionCollection $e) {
	echo "Validation Error:". $e->getMessage() ."<br><br>" ;
}

echo "\nS3 PHP SDK Serial Test Done!";

?>