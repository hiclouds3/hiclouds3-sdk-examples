<?php

require_once 'client.php';
require_once 'common.php';

function aclSerialTest($bucketname)
{
    global $client;

    try {
        echo "Starting S3 operations...\n";

        // Create Bucket
        $client->createBucket([
            'Bucket' => $bucketname,
        ]);
        echo "Bucket created: $bucketname\n";

        // Set Bucket ACL
        $client->putBucketAcl([
            'ACL' => 'public-read',
            'Bucket' => $bucketname,

        ]);
        echo "Bucket ACL set.\n";

        // Enable Bucket Versioning
        $client->putBucketVersioning([
            'Bucket' => $bucketname,
            'VersioningConfiguration' => [
                'Status' => 'Enabled',
            ],
        ]);
        echo "Bucket versioning enabled.\n";

        // Put Object
        $fileName = 'testv.txt';
        $client->putObject([
            'Bucket' => $bucketname,
            'Key' => $fileName,
            'Body' => createSampleFile(),
            'ACL' => 'private',
        ]);
        echo "Object uploaded: $fileName\n";

        // Get Object ACL
        $objectAcl = $client->getObjectAcl([
            'Bucket' => $bucketname,
            'Key' => $fileName,
        ]);
        echo "Object ACL retrieved.\n";

        // List Object Versions
        $versions = $client->listObjectVersions([
            'Bucket' => $bucketname,
        ]);
        $versionIds = array_map(fn($v) => $v['VersionId'], $versions['Versions']);
        echo "Object versions listed.\n";

        // Delete Objects
        $objectsToDelete = array_map(fn($versionId) => ['Key' => $fileName, 'VersionId' => $versionId], $versionIds);
        if (!empty($objectsToDelete)) {
            $client->deleteObjects([
                'Bucket' => $bucketname,
                'Delete' => [
                    'Objects' => $objectsToDelete,
                ],
            ]);
            echo "Objects deleted.\n";
        }

        // Delete Bucket
        $client->deleteBucket([
            'Bucket' => $bucketname,
        ]);
        echo "Bucket deleted: $bucketname\n";
        echo "\n";
        echo "✔ ACL Serial Test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during acl serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
