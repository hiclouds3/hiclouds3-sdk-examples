<?php
require_once 'client.php';
require_once 'common.php';

function bucketSerialTest($bucketname)
{
    global $client;

    echo "> Bucket Serial Testing....\n";

    // Test 1: Region Put Bucket
    echo "Creating bucket with region...\n";
    try {
        $client->createBucket(['Bucket' => $bucketname]);
        echo "Bucket $bucketname created successfully.\n";
        $client->headBucket(['Bucket' => $bucketname]);
        echo "Bucket $bucketname exists.\n";
        $client->deleteBucket(['Bucket' => $bucketname]);
        echo "Bucket $bucketname deleted successfully.\n";
    } catch (Exception $e) {
        echo "Error during region bucket operations: " . $e->getMessage() . "\n";
    }

    // Test 2: ACL Put Bucket
    echo "Creating bucket with ACL...\n";
    try {
        $client->createBucket(['Bucket' => $bucketname, 'ACL' => 'public-read']);
        echo "Bucket $bucketname created with public-read ACL.\n";
        $result = $client->getBucketAcl(['Bucket' => $bucketname]);
        echo "Bucket ACL: " . json_encode($result['Grants']) . "\n";

        $sampleFile = createSampleFile();
        $client->putObject(['Bucket' => $bucketname, 'Key' => 'photo/test1.jpg', 'Body' => $sampleFile]);
        echo "Object photo/test1.jpg uploaded.\n";
        $client->putObject(['Bucket' => $bucketname, 'Key' => 'photo/test2.jpg', 'Body' => $sampleFile]);
        echo "Object photo/test2.jpg uploaded.\n";
        $client->putObject(['Bucket' => $bucketname, 'Key' => 'photo/test3.jpg', 'Body' => $sampleFile]);
        echo "Object photo/test3.jpg uploaded.\n";

        $client->deleteObject(['Bucket' => $bucketname, 'Key' => 'photo/test1.jpg']);
        echo "Object photo/test1.jpg deleted.\n";
        $client->deleteObject(['Bucket' => $bucketname, 'Key' => 'photo/test2.jpg']);
        echo "Object photo/test2.jpg deleted.\n";
        $client->deleteObject(['Bucket' => $bucketname, 'Key' => 'photo/test3.jpg']);
        echo "Object photo/test3.jpg deleted.\n";

        $client->deleteBucket(['Bucket' => $bucketname]);
        echo "Bucket $bucketname deleted successfully.\n";
    } catch (Exception $e) {
        echo "Error during ACL bucket operations: " . $e->getMessage() . "\n";
    }

    // Test 3: Parameter Put Bucket
    echo "Creating bucket and testing parameters...\n";
    try {
        $client->createBucket(['Bucket' => $bucketname]);
        echo "Bucket $bucketname created successfully.\n";

        $sampleFile = createSampleFile();
        $fileNames = ["apple.jpg", "photos/2006/January/sample.jpg", "photos/2006/February/sample2.jpg", "asset.txt"];
        foreach ($fileNames as $fileName) {
            $client->putObject(['Bucket' => $bucketname, 'Key' => $fileName, 'Body' => $sampleFile]);
            echo "Object $fileName uploaded.\n";
        }

        $client->listObjects(['Bucket' => $bucketname]);
        echo "Listed objects in bucket $bucketname.\n";

        foreach ($fileNames as $fileName) {
            $client->deleteObject(['Bucket' => $bucketname, 'Key' => $fileName]);
            echo "Object $fileName deleted.\n";
        }

        $client->deleteBucket(['Bucket' => $bucketname]);
        echo "Bucket $bucketname deleted successfully.\n";

        echo "\n";
        echo "✔ Bucket Serial Test DONE\n";
    } catch (Exception $e) {
        echo "Error during parameter bucket operations: " . $e->getMessage() . "\n";
    }
}
