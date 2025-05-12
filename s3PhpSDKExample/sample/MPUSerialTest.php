<?php

require_once 'client.php';
function BasicMPU($bucketname)
{
    global $client;

    echo "Creating bucket: $bucketname\n";
    $client->createBucket([
        'Bucket' => $bucketname
    ]);

    echo "Initiating multipart upload for castle.jpg\n";
    $result = $client->createMultipartUpload([
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg'
    ]);
    $uploadID = $result['UploadId'];

    echo "Listing multipart uploads\n";
    $result = $client->listMultipartUploads([
        'Bucket' => $bucketname,
    ]);

    echo "Uploading parts\n";
    $client->uploadPart([
        'Body' => fopen('castle.jpg.001', 'r+'),
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'PartNumber' => 1,
        'UploadId' => $uploadID
    ]);
    $client->uploadPart([
        'Body' => fopen('castle.jpg.002', 'r+'),
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'PartNumber' => 2,
        'UploadId' => $uploadID
    ]);
    $client->uploadPart([
        'Body' => fopen('castle.jpg.003', 'r+'),
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'PartNumber' => 3,
        'UploadId' => $uploadID
    ]);

    echo "Listing uploaded parts\n";
    $result = $client->listParts([
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'UploadId' => $uploadID
    ]);
    $parts = $result['Parts'];

    echo "Completing multipart upload\n";
    $client->completeMultipartUpload([
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'UploadId' => $uploadID,
        'MultipartUpload' => [
            'Parts' => $parts
        ]
    ]);

    echo "Deleting object: castle.jpg\n";
    $client->deleteObject([
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg'
    ]);

    echo "Deleting bucket: $bucketname\n";
    $client->deleteBucket([
        'Bucket' => $bucketname
    ]);
}

function AbortMPU($bucketname)
{
    global $client;

    echo "Creating bucket: $bucketname\n";
    $client->createBucket(array(
        'Bucket' => $bucketname
    ));

    echo "Initiating multipart upload for castle.jpg\n";
    $result = $client->createMultipartUpload(array(
        'ACL' => 'public-read',
        'StorageClass' => 'STANDARD',
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg'
    ));
    $uploadID = $result['UploadId'];

    echo "Listing multipart uploads\n";
    $result = $client->listMultipartUploads(array(
        'Bucket' => $bucketname,
    ));

    sleep(5);

    echo "Aborting multipart upload\n";
    $client->abortMultipartUpload(array(
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'UploadId' => $uploadID
    ));

    echo "Listing multipart uploads after abort\n";
    $result = $client->listMultipartUploads(array(
        'Bucket' => $bucketname,
    ));

    echo "Deleting bucket: $bucketname\n";
    $client->deleteBucket(array(
        'Bucket' => $bucketname
    ));
}

function ListPart($bucketname)
{
    global $client;

    echo "Creating bucket: $bucketname\n";
    $client->createBucket(array(
        'Bucket' => $bucketname
    ));

    echo "Initiating multipart upload for castle.jpg\n";
    $result = $client->createMultipartUpload(array(
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg'
    ));
    $uploadID = $result['UploadId'];

    echo "Listing multipart uploads\n";
    $result = $client->listMultipartUploads(array(
        'Bucket' => $bucketname,
    ));

    echo "Uploading parts\n";
    $client->uploadPart(array(
        'Body' => 'Hello!',
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'PartNumber' => '1',
        'UploadId' => $uploadID
    ));
    $client->uploadPart(array(
        'Body' => 'Hello!',
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'PartNumber' => '2',
        'UploadId' => $uploadID
    ));
    $client->uploadPart(array(
        'Body' => 'Hello!',
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'PartNumber' => '3',
        'UploadId' => $uploadID
    ));

    echo "Listing uploaded parts with parameters\n";
    $result = $client->listParts(array(
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'UploadId' => $uploadID,
        'MaxParts' => '2',
        'PartNumberMarker' => '2'
    ));

    echo "Aborting multipart upload\n";
    $client->abortMultipartUpload(array(
        'Bucket' => $bucketname,
        'Key' => 'castle.jpg',
        'UploadId' => $uploadID
    ));

    echo "Deleting bucket: $bucketname\n";
    $client->deleteBucket(array(
        'Bucket' => $bucketname
    ));
}

function ListMPU($bucketname)
{
    global $client;

    $datetime = new DateTime('17 Oct 2100');
    $datetime2 = new DateTime('17 Oct 2200');

    echo "Creating bucket: $bucketname\n";
    $client->createBucket(array(
        'Bucket' => $bucketname
    ));

    echo "Uploading object: test1.txt\n";
    $client->putObject(array(
        'Body' => 'Hello!',
        'Bucket' => $bucketname,
        'Key' => 'test1.txt',
    ));

    echo "Initiating multipart upload for test1.txt\n";
    $result = $client->createMultipartUpload(array(
        'Bucket' => $bucketname,
        'Key' => 'test1.txt',
        'ContentType' => "text/plain",
        'ContentLanguage' => 'en',
        'ContentEncoding' => "UTF-8",
        'ContentDisposition' => "attachment; filename=\"default.txt\"",
        'CacheControl' => "no-cache",
        'ContentMD5' => 'movf4FeaK/4LQyz5FP1oiQ=='
    ));
    $uploadID1 = $result['UploadId'];

    sleep(5);

    echo "Initiating multipart upload for xtest2.txt\n";
    $result = $client->createMultipartUpload(array(
        'Bucket' => $bucketname,
        'Key' => 'xtest2.txt',
        'ValidateMD5' => 'false',
        'MetadataDirective' => 'REPLACE',
        'command.headers' => array(
            'x-amz-meta-flower' => 'lily',
            'x-amz-meta-color' => "pink"
        ),
        'WebsiteRedirectLocation' => 'http://google.com',
    ));
    $uploadID2 = $result['UploadId'];

    sleep(10);

    echo "Listing multipart uploads\n";
    $result = $client->listMultipartUploads(array(
        'Bucket' => $bucketname,
        'ContentMD5' => 'false',
        'ValidateMD5' => 'false',
    ));

    echo "Copying parts\n";
    $client->uploadPartCopy(array(
        'CopySource' => $bucketname . "/test1.txt",
        'Bucket' => $bucketname,
        'Key' => 'test1.txt',
        'PartNumber' => '1',
        'Expire' => "GMT " . $datetime->format('c'),
        'UploadId' => $uploadID1,
    ));

    $client->uploadPartCopy(array(
        'CopySource' => $bucketname . "/test1.txt",
        'Bucket' => $bucketname,
        'Key' => 'xtest2.txt',
        'PartNumber' => '1',
        'CopySourceIfmodifiedSince' => "GMT " . $datetime2->format('c'),
        'UploadId' => $uploadID2
    ));

    $client->uploadPartCopy(array(
        'CopySource' => $bucketname . "/test1.txt",
        'Bucket' => $bucketname,
        'Key' => 'xtest2.txt',
        'PartNumber' => '2',
        'CopySourceIfUnmodifiedSince' => "GMT " . $datetime->format('c'),
        'UploadId' => $uploadID2
    ));

    echo "Listing parts for xtest2.txt\n";
    $result = $client->listParts(array(
        'Bucket' => $bucketname,
        'Key' => 'xtest2.txt',
        'UploadId' => $uploadID2,
    ));

    echo "Deleting object: test1.txt\n";
    $client->deleteObject(array(
        'Bucket' => $bucketname,
        'Key' => 'test1.txt'
    ));

    echo "Aborting multipart uploads\n";
    $client->abortMultipartUpload(array(
        'Bucket' => $bucketname,
        'Key' => 'test1.txt',
        'UploadId' => $uploadID1
    ));
    $client->abortMultipartUpload(array(
        'Bucket' => $bucketname,
        'Key' => 'xtest2.txt',
        'UploadId' => $uploadID2
    ));

    echo "Deleting bucket: $bucketname\n";
    $client->deleteBucket(array(
        'Bucket' => $bucketname
    ));
}

function mpuSerialTest($bucketname)
{
    try {
        echo "> MultiPartUpload testing...\n";
        /*
         * test 1.Normal Initial MPU
         *		2.Normal List MPU
         *		3.Upload part
         *		4.List Uploaded Parts
         *		5.Complete MPU
         */
        echo ">> Basic MPU tests\n";
        BasicMPU($bucketname);
        echo "\n";


        /*
         * test 1.Normal Initial MPU
         *		2.Normal List MPU
         *		3.Upload part
         *		4.List Uploaded Parts with Parameters
         *		5.Abort
         */
        echo ">> Abort MPU tests\n";
        AbortMPU($bucketname);
        echo "\n";

        /*
         * test 1.Normal Initial MPU
         *		2.Normal List MPU
         *		3.Upload part
         *		4.List Uploaded Parts with Parameters
         *		5.Abort
         */
        echo ">> List Part tests\n";
        ListPart($bucketname);
        echo "\n";

        /*
         * test 1. Initial MPU with parameters
         * 		2. copy part with parameters
         * 		3. getObject with parameters
         * 		4. headObject with parameters
         * 		5. List MPU with parameters
         * 		6. Abort
         */
        echo ">> List MPU tests\n";
        ListMPU($bucketname);
        echo "\n";

        echo "✔ MultiPartUpload test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during mpu serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
