<pre>
<?php
require 'client.php';

use Aws\S3\S3Client;
use Aws\S3\Enum\CannedAcl;
use Aws\S3\Exception\S3Exception;
use Aws\S3\Model\ClearBucket;
use Guzzle\Common\Exception\ExceptionCollection;
use Aws\S3\Enum\Permission;
use Aws\S3\Enum\GranteeType;

function createBucket($bucketname)
{
	global $client;

	try {
		$result = $client->createBucket(array(
				'Bucket' => $bucketname
		));
	} catch (BucketAlreadyExistsException $e) {
		echo 'Bucket "'. $bucketname .'" already exists! <br>' .$e->getMessage()."<br><br>" ;
	} catch (S3Exception $e) {
		echo "<font color=red>¡I</font>Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.<br>";
		echo "Error Message:    " . $e->getMessage()."<br>";
		echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
		echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
		echo "Error Type:       " . $e->getExceptionType()."<br>";
		echo "Request ID:       " . $e->getRequestId()."<br>";
	}
}

function setBucketCORS($bucketname)
{
	global $client;

	try {
		$result = $client->putBucketCors(array(
				// Bucket is required
				'Bucket' => $bucketname,
				'CORSRules' => array( //ID Parameter NOT Support
						array(
								'AllowedHeaders' => array('content-type', 'content-encoding'),
								'AllowedMethods' => array('POST', 'PUT'),
								'AllowedOrigins' => array('http://tw.yahoo.com.tw'),
								'ExposeHeaders' => array('x-amz-*'),
								'MaxAgeSeconds' => 100,
						),
						array(
								'AllowedHeaders' => array('connection', 'content-encoding'),
								'AllowedMethods' => array('DELETE', 'HEAD'),
								'AllowedOrigins' => array('http://hello.world'),
								'ExposeHeaders' => array('request-id'),
								'MaxAgeSeconds' => 10,
						)
				),
		));
	} catch (S3Exception $e) {
		echo "<font color=red>¡I</font>Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.<br>";
		echo "Error Message:    " . $e->getMessage()."<br>";
		echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
		echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
		echo "Error Type:       " . $e->getExceptionType()."<br>";
		echo "Request ID:       " . $e->getRequestId()."<br>";
	}
}

function getBucketCORS($bucketname)
{
	global $client;

	try {
		$result = $client->getBucketCors(array(
				// Bucket is required
				'Bucket' => $bucketname,
		));

		echo "Listing Rules...\n";
		foreach($result['CORSRules'] as $rule){
			echo "--------------------------\n";
			echo 
			"[AllowedHeaders]".$rule['AllowedHeaders'][0].", ".$rule["AllowedHeaders"][1]. "\n".
			"[AllowedMethods]".$rule['AllowedMethods'][0].", ".$rule["AllowedMethods"][1]. "\n".
			"[AllowedOrigins]".$rule['AllowedOrigins'][0]." \n".
			"[ExposeHeaders]".$rule['ExposeHeaders'][0]." \n".
			"[MaxAgeSeconds]".$rule['MaxAgeSeconds'][0]." \n";
		}
	} catch (S3Exception $e) {
		echo "<font color=red>¡I</font>Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.<br>";
		echo "Error Message:    " . $e->getMessage()."<br>";
		echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
		echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
		echo "Error Type:       " . $e->getExceptionType()."<br>";
		echo "Request ID:       " . $e->getRequestId()."<br>";
	}
}

function deleteBucketCORS($bucketname)
{
	global $client;

	try {
		$result = $client->deleteBucketCors(array(
				// Bucket is required
				'Bucket' => $bucketname,
		));
	} catch (S3Exception $e) {
		echo "<font color=red>¡I</font>Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.<br>";
		echo "Error Message:    " . $e->getMessage()."<br>";
		echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
		echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
		echo "Error Type:       " . $e->getExceptionType()."<br>";
		echo "Request ID:       " . $e->getRequestId()."<br>";
	}
}

function delBucket($bucketname)
{

	global $client;
	try
	{
		$client->deleteBucket(array('Bucket' => $bucketname));
	}
	catch (S3Exception $e) {
		echo "<font color=red>¡I</font>Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.<br>";
		echo "Error Message:    " . $e->getMessage()."<br>";
		echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
		echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
		echo "Error Type:       " . $e->getExceptionType()."<br>";
		echo "Request ID:       " . $e->getRequestId()."<br>";
	}
	catch (ExceptionCollection $e) {
		echo "Validation Error:". $e->getMessage() ."<br><br>" ;
	}
}


$bucketname = $argv[1];
$bucketname2 = $argv[2];
$bucketname3 = $argv[3];
echo "Bucket CORS Serial Testing....\n";
/*
 * test 1. create Bucket
*      2. Put Bucket CORS
*      3. Get Bucket CORS
*      4. Delete Bucket CORS
*      5. Delete Bucket
*/
createBucket($bucketname);
setBucketCORS($bucketname);
getBucketCORS($bucketname);
deleteBucketCORS($bucketname);
delBucket($bucketname);

?>
</pre>

