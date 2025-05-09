<?php
/*
 * test 1. put Bucket
 * 		2. put BucketTagging
 * 		3. get BucketTagging
 * 		4. Delete BucketTagging
 * 		5. Delete Bucket
 */

require_once 'client.php';

function bucketTaggingSerialTest($bucketname)
{
    global $client;
    try {
        echo "> BucketTagging Serial testing...\n";

        echo "Creating bucket: $bucketname\n";
        $client->createBucket([
            'Bucket' => $bucketname
        ]);

        echo "Adding bucket tagging...\n";
        $result = $client->putBucketTagging([
            'Bucket' => $bucketname,
            'Tagging' => [
                'TagSet' => [
                    [
                        'Key' => 'Jan',
                        'Value' => 'pink'
                    ],
                    [
                        'Key' => 'Dec',
                        'Value' => 'blue'
                    ]
                ]
            ]
        ]);

        echo "Retrieving bucket tagging...\n";
        $result = $client->getBucketTagging([
            'Bucket' => $bucketname
        ]);
        echo "Bucket Tagging: " . json_encode($result['TagSet']) . "\n";

        echo "Deleting bucket tagging...\n";
        $result = $client->deleteBucketTagging([
            'Bucket' => $bucketname
        ]);

        echo "Deleting bucket: $bucketname\n";
        $client->deleteBucket([
            'Bucket' => $bucketname
        ]);
        echo "\n";
        echo "✔ BucketTagging Serial Test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during website serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
