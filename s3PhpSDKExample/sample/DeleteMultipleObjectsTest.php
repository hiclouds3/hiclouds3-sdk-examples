<?php

require_once 'client.php';
require_once 'common.php';

function deleteMultipleObjectsTest($bucketname)
{
    global $client;
    try {
        echo "> Delete Multiple Objects Testing...\n";

        echo "Creating bucket: $bucketname\n";
        $client->createBucket([
            'Bucket' => $bucketname
        ]);

        echo "Enabling versioning for bucket: $bucketname\n";
        $client->putBucketVersioning([
            'Bucket' => $bucketname,
            'VersioningConfiguration' => [
                'Status' => 'Enabled',
            ],
        ]);

        echo "Uploading object version 1...\n";
        $result = $client->putObject([
            'Bucket' => $bucketname,
            'Key' => 'photo/cht2.jpg',
            'Body' => createSampleFile()
        ]);
        $vid1 = $result['VersionId'];

        echo "Uploading object version 2...\n";
        $result = $client->putObject([
            'Bucket' => $bucketname,
            'Key' => 'photo/cht2.jpg',
            'Body' => createSampleFile()
        ]);
        $vid2 = $result['VersionId'];

        echo "Uploading object version 3...\n";
        $result = $client->putObject([
            'Bucket' => $bucketname,
            'Key' => 'photo/cht2.jpg',
            'Body' => createSampleFile()
        ]);
        $vid3 = $result['VersionId'];

        echo "Retrieving object with version ID: $vid1\n";
        $client->getObject([
            'Bucket' => $bucketname,
            'Key' => 'photo/cht2.jpg',
            'VersionId' => $vid1
        ]);

        echo "Retrieving object metadata with version ID: $vid2\n";
        $client->headObject([
            'Bucket' => $bucketname,
            'Key' => 'photo/cht2.jpg',
            'VersionId' => $vid2
        ]);

        echo "Listing all object versions...\n";
        $result = $client->listObjectVersions([
            'Bucket' => $bucketname
        ]);
        showResult($result);

        echo "Deleting all object versions...\n";
        $client->deleteObjects([
            'Bucket' => $bucketname,
            'Delete' => [
                'Objects' => array_map(function ($version) {
                    return [
                        'Key' => $version['Key'],
                        'VersionId' => $version['VersionId']
                    ];
                }, $result['Versions'])
            ]
        ]);

        echo "Deleting bucket: $bucketname\n";
        $client->deleteBucket([
            'Bucket' => $bucketname,
        ]);
        echo "\n";
        echo "✔ Delete Multiple Objects Test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during deleteMultipleObjects serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
