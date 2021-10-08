<?php
/*
 * test 1. Basic putBucket
 * 		2. put Object
 * 		3. put BucketWebsite (Basic & with redirect rules)
 * 		4. get BucketWebsite
 * 		5. Delete BucketWebsite
 */

use Aws\S3\Exception\S3Exception;
require 'client.php';
$bucketname=$argv[1];

try{
	echo "Website Serial Testing...\n";
	$client->createBucket(array(
		'Bucket' => $bucketname,
		'ACL' 	 => 'public-read'	
	));
	
	$client->putObject(array(
			'Body'   => '404testchttl<br><title>chttl</title>',
			'Bucket' => $bucketname,
			'Key'	 => 'error.html',
			'ACL' 	 => 'public-read',
			'WebsiteRedirectLocation' => 'http://google.com'
	));
	
	$client->putObject(array(
			'Body'   => 'Hello world!',
			'Bucket' => $bucketname,
			'Key'	 => 'index.html',
			'ACL' 	 => 'public-read'
	));
	$client->putBucketWebsite(array(
			'Bucket' => $bucketname,
			'WebsiteConfiguration' => [
				'ErrorDocument' => array(
					'Key' => 'error.html'
					),
				'IndexDocument' => array(
					'Suffix' => 'index.html'
					),
			]
	));
	
	$result=$client->getBucketWebsite( array(
			'Bucket' => $bucketname
	));
	
	//apply routing rules, may take some time for applying new rules  
	$client->putBucketWebsite(array(
			'Bucket' => $bucketname,
			'WebsiteConfiguration' => [
				'ErrorDocument' => array(
						'Key' => 'error.html'
				),
				'IndexDocument' => array(
						'Suffix' => 'index.html'
				),
				'RoutingRules' => array(
						'RoutingRule' =>array(
								'Redirect' =>array(
										'HostName' => 'www.google.com'
								),
						),
				),
			]
	));
	
	$result=$client->getBucketWebsite( array(
			'Bucket' => $bucketname
	));
	
	$client->deleteBucketWebsite(array(
			'Bucket' => $bucketname
	));
	
	$client->deleteObject(array(
			'Bucket' => $bucketname,
			'Key'	 => 'error.html'
	));
	$client->deleteObject(array(
			'Bucket' => $bucketname,
			'Key'	 => 'index.html'
	));
	
	$client->deleteBucket(array(
			'Bucket' => $bucketname
	));
}catch (S3Exception $e) {
    echo "Caught an AmazonServiceException.", "\n";
    echo "Error Message:    " . $e->getAWSErrorMessage(). "\n";
    echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
    echo "AWS Error Code:   " . $e->getAwsErrorCode(). "\n";
    echo "Error Type:       " . $e->getAwsErrorType(). "\n";
    echo "Request ID:       " . $e->getAwsRequestId(). "\n";
}
