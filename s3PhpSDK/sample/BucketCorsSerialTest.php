<?php
require 'client.php';

use Aws\S3\Exception\S3Exception;

function createBucket($bucketname)
{
    global $client;

    try {
        $result = $client->createBucket(array(
                'Bucket' => $bucketname
        ));
    } catch (S3Exception $e) {
        echo "Caught an AmazonServiceException.", "\n";
        echo "Error Message:    " . $e->getAWSErrorMessage(). "\n";
        echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
        echo "AWS Error Code:   " . $e->getAwsErrorCode(). "\n";
        echo "Error Type:       " . $e->getAwsErrorType(). "\n";
        echo "Request ID:       " . $e->getAwsRequestId(). "\n";
    }
}

function setBucketCORS($bucketname)
{
    global $client;

    try {
        $result = $client->putBucketCors(array(
                // Bucket is required
                'Bucket' => $bucketname,
                'CORSConfiguration' => [
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
                ]
        ));
    } catch (S3Exception $e) {
        echo "Caught an AmazonServiceException.", "\n";
        echo "Error Message:    " . $e->getAWSErrorMessage(). "\n";
        echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
        echo "AWS Error Code:   " . $e->getAwsErrorCode(). "\n";
        echo "Error Type:       " . $e->getAwsErrorType(). "\n";
        echo "Request ID:       " . $e->getAwsRequestId(). "\n";
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
        foreach ($result['CORSRules'] as $rule) {
            echo "--------------------------\n";
            echo
            "[AllowedHeaders]".$rule['AllowedHeaders'][0].", ".$rule["AllowedHeaders"][1]. "\n".
            "[AllowedMethods]".$rule['AllowedMethods'][0].", ".$rule["AllowedMethods"][1]. "\n".
            "[AllowedOrigins]".$rule['AllowedOrigins'][0]." \n".
            "[ExposeHeaders]".$rule['ExposeHeaders'][0]." \n".
            "[MaxAgeSeconds]".$rule['MaxAgeSeconds']." \n";
        }
    } catch (S3Exception $e) {
        echo "Caught an AmazonServiceException.", "\n";
        echo "Error Message:    " . $e->getAWSErrorMessage(). "\n";
        echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
        echo "AWS Error Code:   " . $e->getAwsErrorCode(). "\n";
        echo "Error Type:       " . $e->getAwsErrorType(). "\n";
        echo "Request ID:       " . $e->getAwsRequestId(). "\n";
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
        echo "Caught an AmazonServiceException.", "\n";
        echo "Error Message:    " . $e->getAWSErrorMessage(). "\n";
        echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
        echo "AWS Error Code:   " . $e->getAwsErrorCode(). "\n";
        echo "Error Type:       " . $e->getAwsErrorType(). "\n";
        echo "Request ID:       " . $e->getAwsRequestId(). "\n";
    }
}

function delBucket($bucketname)
{
    global $client;
    try {
        $client->deleteBucket(array('Bucket' => $bucketname));
    } catch (S3Exception $e) {
        echo "Caught an AmazonServiceException.", "\n";
        echo "Error Message:    " . $e->getAWSErrorMessage(). "\n";
        echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
        echo "AWS Error Code:   " . $e->getAwsErrorCode(). "\n";
        echo "Error Type:       " . $e->getAwsErrorType(). "\n";
        echo "Request ID:       " . $e->getAwsRequestId(). "\n";
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
