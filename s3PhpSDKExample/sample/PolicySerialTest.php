<?php
require_once 'client.php';
/*
 * test 1. Put bucket policy
 *      2. Get bucket policy
 *      3. Delete bucket policy
 *
 */
function policySerialTest($bucketname)
{
    global $client;
    try {
        echo "> Policy Serial Testing...\n";
        $client->createBucket([
            'Bucket' => $bucketname
        ]);

        // Policy to deny all users from GetObject()
        $policy = json_encode([
            'Version' => '2008-10-17',
            'Statement' => [
                [
                    'Sid' => 'DenyPublicREAD',
                    'Effect' => 'Deny',
                    'Principal' => '*',
                    'Action' => 's3:GetObject',
                    'Resource' => "arn:aws:s3:::$bucketname/*"
                ]
            ]
        ]);

        echo "Putting bucket policy...\n";
        $client->putBucketPolicy([
            'Bucket' => $bucketname,
            'Policy' => $policy
        ]);

        echo "Getting bucket policy...\n";
        $result = $client->getBucketPolicy([
            'Bucket' => $bucketname
        ]);
        echo "Policy: " . $result['Policy'] . "\n";

        echo "Deleting bucket policy...\n";
        $client->deleteBucketPolicy([
            'Bucket' => $bucketname
        ]);

        echo "Deleting bucket...\n";
        $client->deleteBucket([
            'Bucket' => $bucketname
        ]);
        echo "\n";
        echo "✔ Policy Serial Test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during policy serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
