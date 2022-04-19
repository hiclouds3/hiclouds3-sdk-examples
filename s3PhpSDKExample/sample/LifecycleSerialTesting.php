<?php
/*
 * test 1. putBucket
 * 		2. put Object
 * 		3. put BucketLifecycle (expire on date & expire in days)
 * 		4. get BucketLifecycle
 * 		5. Delete BucketLifecycle
 */

use Aws\S3\Exception\S3Exception;

require 'client.php';

date_default_timezone_set('Asia/Taipei');

$bucketname=$argv[2];
$id='testLC';
$id2='testLC2';
$prefix='test.txt';
$datetime = new DateTime('17 Oct 2020');

function createSampleFile()
{
    $temp = tmpfile();
    $content = "abcdefghijklmnopqrstuvwxyz<br>01234567890112345678901234<br>!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234<br>abcdefghijklmnopqrstuvwxyz<br>";
    fwrite($temp, $content);
    fseek($temp, 0);
    return $temp;
    fclose($temp); // this removes the file
}

try {
    echo "Lifecycle Serial testing...", "\n";


    $client->createBucket(array(
    'Bucket' => $bucketname
    ));
    $result = $client->putObject(array(
        'Bucket' => $bucketname,
        'Key'    => $prefix,
        'Body'   => createSampleFile()
    ));

    $client->putBucketLifecycle(array(
        'Bucket' => $bucketname ,
        'Rules' => array(
            array(
                'Expiration'=> array(
                    'Days' => 1,
                ),
                'ID' => $id ,
                'Prefix' => $prefix ,
                'Status' => 'Enabled',
            ),
        )
    ));

    $client->putBucketLifecycle(array(
        'Bucket' => $bucketname ,
        'Rules' => array(
            array(
                'Expiration'=> array(
                    'Date' => "GMT ".$datetime->format('c'),
                ),
                'ID' => $id2 ,
                'Prefix' => $prefix ,
                'Status' => 'Enabled',
            ),
            array(
                'Transition'=> array(
                    'Days' => 30,
                    'StorageClass' => 'GLACIER'
                ),
                'ID' => 'transition1' ,
                'Prefix' => $prefix ,
                'Status' => 'Enabled',
            ),
        )
    ));

    $result = $client->getBucketLifecycle(array(
        'Bucket' => $bucketname,
    ));

    echo "Listing Rules...\n";
    foreach ($result['Rules'] as $rule) {
		echo json_encode($rule), "\n";
    }

    $client->putBucketLifecycle(array(
        'Bucket' => $bucketname ,
        'Rules' => array(
            array(
                'Expiration'=> array(
                        'Days' => 1,
                ),
                'ID' => $id ,
                'Prefix' => $prefix ,
                'Status' => 'Disabled',
            ),
        )
    ));

    $result = $client->getBucketLifecycle(array(
        'Bucket' => $bucketname,
    ));

    $client->deleteBucketLifecycle(array(
        'Bucket' => $bucketname,
    ));

    $client->deleteObject(array(
        'Bucket' => $bucketname,
        'Key'	=> $prefix
    ));
    $client->deleteBucket(array(
        'Bucket' => $bucketname
    ));
} catch (S3Exception $e) {
    echo "Caught an AmazonServiceException.", "\n";
    echo "Error Message:    " . $e->getMessage(). "\n";
    echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
    echo "AWS Error Code:   " . $e->getExceptionCode(). "\n";
    echo "Error Type:       " . $e->getExceptionType(). "\n";
    echo "Request ID:       " . $e->getRequestId(). "\n";
}
