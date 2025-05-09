<?php

require_once 'client.php';
require_once 'common.php';

function versioningSerialTest($bucketname)
{
    global $client;
    try {
        echo "> Versioning Serial Testing...\n";

        echo "Creating bucket: $bucketname\n";
        $client->createBucket([
            'Bucket' => $bucketname
        ]);

        echo "Getting bucket versioning status...\n";
        $result = $client->getBucketVersioning([
            'Bucket' => $bucketname,
        ]);

        echo "Enabling versioning for bucket: $bucketname\n";
        $client->putBucketVersioning([
            'Bucket' => $bucketname,
            'VersioningConfiguration' => [
                'Status' => 'Enabled'
            ]
        ]);

        echo "Getting updated bucket versioning status...\n";
        $result = $client->getBucketVersioning([
            'Bucket' => $bucketname,
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

        echo "Listing object versions with delimiter...\n";
        $result = $client->listObjectVersions([
            'Bucket' => $bucketname,
            'Delimiter' => 'photo/'
        ]);

        echo "Listing object versions with max keys...\n";
        $result = $client->listObjectVersions([
            'Bucket' => $bucketname,
            'MaxKeys' => 2
        ]);

        echo "Listing object versions with key marker...\n";
        $result = $client->listObjectVersions([
            'Bucket' => $bucketname,
            'KeyMarker' => 'photo/cht2.jpg'
        ]);

        echo "Listing object versions with prefix and max keys...\n";
        $result = $client->listObjectVersions([
            'Bucket' => $bucketname,
            'Prefix' => 'photo/',
            'MaxKeys' => 2
        ]);

        $result = $client->listObjectVersions([
            'Bucket' => $bucketname,
        ]);
        $i = 0;
        foreach ($result['Versions'] as $v) {
            $a[$i] = $v['VersionId'];
            $i++;
        }

        echo "Suspending versioning for bucket: $bucketname\n";
        $client->putBucketVersioning([
            'Bucket' => $bucketname,
            'VersioningConfiguration' => [
                'Status' => 'Suspended'
            ]
        ]);

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
        echo "✔ Versioning Serial Test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during versioning serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
