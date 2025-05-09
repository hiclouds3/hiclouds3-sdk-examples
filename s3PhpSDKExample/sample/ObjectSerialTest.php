<?php
require_once 'client.php';
require_once 'common.php';
function objectSerialTest($bucketname, $bucketname2)
{
    global $client;
    echo "> Object Serial Testing...\n";

    $objName = "test.txt";
    $objName2 = "test2.txt";
    $datetime = new DateTime('17 Oct 2100');
    $datetime2 = new DateTime('17 Oct 2000');
    try {
        $client->createBucket([
            'Bucket' => $bucketname
        ]);
        $client->createBucket([
            'Bucket' => $bucketname2
        ]);

        echo "Uploading object...\n";
        $client->putObject([
            'Bucket' => $bucketname,
            'Key' => $objName,
            'Body' => createSampleFile(),
            'Expires' => $datetime->format(DateTime::ATOM),
            'ContentMD5' => base64_encode(md5(SAMPLE_CONTENT, true))
        ]);

        $result = $client->getObject([
            'Bucket' => $bucketname,
            'Key' => $objName
        ]);


        $client->deleteObject([
            'Bucket' => $bucketname,
            'Key' => $objName
        ]);

        echo "Uploading object with acl public-read...\n";
        $client->putObject([
            'Bucket' => $bucketname,
            'Key' => $objName,
            'Body' => "abc",
            'ACL' => 'public-read',
            'Metadata' => [
                'flower' => 'lily',
                'color' => 'pink'
            ],
        ]);

        $result = $client->getObject([
            'Bucket' => $bucketname,
            'Key' => $objName
        ]);
        // $etag = str_replace('"', "", $result['ETag']);

        echo "Copying object $objName from $bucketname to $bucketname2 ...\n";
        $client->copyObject([
            'Bucket' => $bucketname2,
            'Key' => $objName2,
            'CopySource' => "$bucketname/$objName",
        ]);

        echo "cleaning up...\n";
        $client->deleteObject([
            'Bucket' => $bucketname,
            'Key' => $objName
        ]);
        $client->deleteObject([
            'Bucket' => $bucketname2,
            'Key' => $objName2
        ]);

        $client->deleteBucket([
            'Bucket' => $bucketname
        ]);
        $client->deleteBucket([
            'Bucket' => $bucketname2
        ]);
        echo "\n";
        echo "✔ Object Serial Test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during object serial test for $bucketname or $bucketname2: " . $e->getMessage() . "\n";
    }
}
?>