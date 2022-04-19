<?php
/*
 * test 1. put Bucket
 * 		2. put BucketTagging
 * 		3. get BucketTagging
 * 		4. Delete BucketTagging
 * 		5. Delete Bucket
 */

use Aws\S3\Exception\S3Exception;

require 'client.php';

$bucketname=$argv[2];

try {
    echo "BucketTagging Serial testing...\n";
        
    $client->createBucket(array(
        'Bucket' => $bucketname
    ));

    $result = $client->putBucketTagging(array(
                        'Bucket' => $bucketname,
                        'TagSet' => array(
                                array(
                                        'Key' => 'Jan',
                                        'Value' => 'pink'
                                ),array(
                                        'Key' => 'Dec',
                                        'Value' => 'blue'
                                )
                                
                        )
            ));
            
    $result = $client->getBucketTagging([
        'Bucket' => $bucketname // REQUIRED
    ]);
    #echo $result;

    $result = $client->deleteBucketTagging([
        'Bucket' => $bucketname // REQUIRED
    ]);

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
