<?php
/*
 * test 1. putBucket
 * 		2. put Object
 * 		3. put BucketLifecycle (expire on date & expire in days)
 * 		4. get BucketLifecycle
 * 		5. Delete BucketLifecycle
 */


require_once 'client.php';
require_once 'common.php';

function lifecycleSerialTest($bucketname)
{
    global $client;
    $id = 'testLC';
    $id2 = 'testLC2';
    $prefix = 'test.txt';
    $datetime = new DateTime('17 Oct 2020');
    try {
        echo "> Lifecycle Serial testing...\n";

        echo "Creating bucket: $bucketname\n";
        $client->createBucket([
            'Bucket' => $bucketname
        ]);

        echo "Uploading object: $prefix\n";
        $result = $client->putObject([
            'Bucket' => $bucketname,
            'Key' => $prefix,
            'Body' => createSampleFile()
        ]);

        echo "Setting bucket lifecycle rules (expire in days)...\n";
        $client->putBucketLifecycleConfiguration([
            'Bucket' => $bucketname,
            'LifecycleConfiguration' => [
                'Rules' => [
                    [
                        'Expiration' => [
                            'Days' => 1,
                        ],
                        'ID' => $id,
                        'Filter' => [
                            'Prefix' => $prefix,
                        ],
                        'Status' => 'Enabled',
                    ],
                ],
            ],
        ]);

        echo "Setting bucket lifecycle rules (expire on date and transition)...\n";
        $client->putBucketLifecycleConfiguration([
            'Bucket' => $bucketname,
            'LifecycleConfiguration' => [
                'Rules' => [
                    [
                        'Expiration' => [
                            'Date' => $datetime->format('Y-m-d\TH:i:s\Z'),
                        ],
                        'ID' => $id2,
                        'Filter' => [
                            'Prefix' => $prefix,
                        ],
                        'Status' => 'Enabled',
                    ],
                    [
                        'Transitions' => [
                            [
                                'Days' => 30,
                                'StorageClass' => 'GLACIER',
                            ],
                        ],
                        'ID' => 'transition1',
                        'Filter' => [
                            'Prefix' => $prefix,
                        ],
                        'Status' => 'Enabled',
                    ],
                ],
            ],
        ]);

        echo "Getting bucket lifecycle configuration...\n";
        $result = $client->getBucketLifecycleConfiguration([
            'Bucket' => $bucketname,
        ]);

        echo "Listing Rules...\n";
        foreach ($result['Rules'] as $rule) {
            echo json_encode($rule), "\n";
        }

        echo "Disabling lifecycle rule: $id\n";
        $client->putBucketLifecycleConfiguration([
            'Bucket' => $bucketname,
            'LifecycleConfiguration' => [
                'Rules' => [
                    [
                        'Expiration' => [
                            'Days' => 1,
                        ],
                        'ID' => $id,
                        'Filter' => [
                            'Prefix' => $prefix,
                        ],
                        'Status' => 'Disabled',
                    ],
                ],
            ],
        ]);

        echo "Deleting bucket lifecycle configuration...\n";
        $client->deleteBucketLifecycle([
            'Bucket' => $bucketname,
        ]);

        echo "Deleting object: $prefix\n";
        $client->deleteObject([
            'Bucket' => $bucketname,
            'Key' => $prefix,
        ]);

        echo "Deleting bucket: $bucketname\n";
        $client->deleteBucket([
            'Bucket' => $bucketname,
        ]);
        echo "\n";
        echo "✔ Lifecycle Serial Test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during lifecycle serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
