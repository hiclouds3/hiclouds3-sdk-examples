<?php
/*
 * test 1. Basic putBucket
 * 		2. put BucketACL
 * 		3. put BucketLogging
 * 		4. get BucketLogging
 * 		5. Delete Bucket
 */
require 'client.php';

function bucketLoggingSerialTest($bucketname, $target_bucketname)
{
    global $client;
    try {
        echo "> Bucket Logging Serial Testing...\n";
        // CreateBucket
        $client->createBucket([
            'Bucket' => $bucketname,
        ]);
        $client->createBucket([
            'Bucket' => $target_bucketname,
        ]);

        $client->putBucketAcl([
            'Bucket' => $target_bucketname,
            'ACL' => 'log-delivery-write',
        ]);

        //SetBucketLogging
        $client->putBucketLogging([
            'Bucket' => $bucketname,
            'BucketLoggingStatus' => [
                'LoggingEnabled' => [
                    'TargetBucket' => $target_bucketname,
                    'TargetPrefix' => 'logs/',
                ],
            ],
        ]);

        //Check BucketLogging
        $result = $client->getBucketLogging([
            'Bucket' => $bucketname
        ]);

        echo json_encode($result['LoggingEnabled']), "\n";

        //DeleteBucket
        $client->deleteBucket(array(
            'Bucket' => $bucketname
        ));
        $client->deleteBucket(array(
            'Bucket' => $target_bucketname
        ));
        echo "\n";
        echo "✔ Bucket Logging Serial Test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during bucket logging serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
