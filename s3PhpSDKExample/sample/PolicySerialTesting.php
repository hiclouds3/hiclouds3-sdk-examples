<?php

use Aws\S3\Exception\S3Exception;

require 'client.php';
/*
 * test 1. Put bucket policy
 * 		2. Get bucket policy
 * 		3. Delete bucket policy
 *
 */
try {
    echo "Policy Serial Testing...\n";
    $bucketname=$argv[1];

    $client->createBucket(array(
            'Bucket' => $bucketname
    ));
    //policy to deny all user to GetObject()
    $policy='{"Version":"2008-10-17","Statement":[{"Sid":"DenyPublicREAD","Effect":"Deny","Principal":{"AWS":"*"},"Action":"s3:GetObject","Resource":"arn:aws:s3:::'.$bucketname.'/*"}]}';
    
    $client->putBucketPolicy(array(
        'Bucket' => $bucketname,
        'Policy' => $policy
    ));
    
    $result=$client->getBucketPolicy(array(
            'Bucket' => $bucketname
    ));
    
    $client->deleteBucketPolicy(array(
            'Bucket' => $bucketname
    ));
    
	$client->deleteBucket(array(
			'Bucket' => $bucketname
	));
} catch (S3Exception $e) {
    echo "Caught an AmazonServiceException.", "\n";
    echo "Error Message:    " . $e->getMessage(). "\n";
    echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
    echo "AWS Error Code:   " . $e->getExceptionCode(). "\n";
    echo "Error Type:       " . $e->getExceptionType(). "\n";
    echo "Request ID:       " . $e->getRequestId(). "\n";
}
