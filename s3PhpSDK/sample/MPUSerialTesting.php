<?php
use Aws\S3\Exception\S3Exception;

require 'client.php';

date_default_timezone_set('Asia/Taipei');

function BasicMPU()
{
    global $client;
    global $bucketname;
    $client->createBucket(array(
            'Bucket' => $bucketname
    ));
    
    $result=$client->createMultipartUpload(array(
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg'
    ));
    $uploadID=$result['UploadId'];
    
    $result=$client->listMultipartUploads(array(
            'Bucket' => $bucketname,
    ));
    
    $client->uploadPart(array(
            'Body'	 => fopen('castle.jpg.001', 'r+'),
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'PartNumber' => '1',
            'UploadId'=> $uploadID
    ));
    $client->uploadPart(array(
            'Body'	 => fopen('castle.jpg.002', 'r+'),
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'PartNumber' => '2',
            'UploadId'=> $uploadID
    ));
    $client->uploadPart(array(
            'Body'	 => fopen('castle.jpg.003', 'r+'),
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'PartNumber' => '3',
            'UploadId'=> $uploadID
    ));
    
    
    $result=$client->listParts(array(
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'UploadId'=> $uploadID
    ));
    
    
    //print_r($result['Parts']);
    $parts=$result['Parts'];
    
    $result=$client->completeMultipartUpload(array(
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'UploadId'=> $uploadID,
			'MultipartUpload' => [
            	'Parts'	 => $parts
			]
    ));
    
    
    $client->deleteObject(array(
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg'
    ));
    
    $client->deleteBucket(array(
            'Bucket' => $bucketname
    ));
}

function AbortMPU()
{
    global $client;
    global $bucketname;
    $client->createBucket(array(
            'Bucket' => $bucketname
    ));
    
    $result=$client->createMultipartUpload(array(
            'ACL'	=>'public-read-write',
            'StorageClass'=>'STANDARD',
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg'
            
    ));
    $uploadID=$result['UploadId'];
    
    
    $result=$client->listMultipartUploads(array(
            'Bucket' => $bucketname,
    ));
    
    sleep(5);
    
    $client->abortMultipartUpload(array(
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'UploadId'=> $uploadID
    ));
    
    
    $result=$client->listMultipartUploads(array(
            'Bucket' => $bucketname,
    ));
    
    //tear down
    $client->deleteBucket(array(
            'Bucket' => $bucketname
    ));
}

function ListPart()
{
    global $client;
    global $bucketname;
    
    $client->createBucket(array(
            'Bucket' => $bucketname
    ));
    
    $result=$client->createMultipartUpload(array(
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg'
    ));
    $uploadID=$result['UploadId'];
    
    $result=$client->listMultipartUploads(array(
            'Bucket' => $bucketname,
    ));
    
    
    $client->uploadPart(array(
            'Body'   => 'Hello!',
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'PartNumber' => '1',
            'UploadId'=> $uploadID
    ));
    $client->uploadPart(array(
            'Body'   => 'Hello!',
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'PartNumber' => '2',
            'UploadId'=> $uploadID
    ));
    $client->uploadPart(array(
            'Body'   => 'Hello!',
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'PartNumber' => '3',
            'UploadId'=> $uploadID
    ));
    
    //list with maxpart & PartNumberMarker
    $result=$client->listParts(array(
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'UploadId'=> $uploadID,
            'MaxParts'=> '2',
            'PartNumberMarker' =>'2'
    ));
    $client->abortMultipartUpload(array(
            'Bucket' => $bucketname,
            'Key'	 => 'castle.jpg',
            'UploadId'=> $uploadID
    ));
    
    $client->deleteBucket(array(
            'Bucket' => $bucketname
    ));
}

function ListMPU()
{
    global $client;
    global $bucketname;
    global $userAEmail;
    global $userBCanonicalId;
    global $ownerCanonicalId;
    $datetime = new DateTime('17 Oct 2100');
    $datetime2 = new DateTime('17 Oct 2200');
    
    $client->createBucket(array(
            'Bucket' => $bucketname
    ));
    $client->putObject(array(
            'Body'   => 'Hello!',
            'Bucket' => $bucketname,
            'Key'	 => 'test1.txt',
    ));
    
    //create MPUs
    $result = $client->createMultipartUpload(array(
            'Bucket' => $bucketname,
            'Key'	 => 'test1.txt',
            'ContentType' => "text/plain",
            'ContentLanguage' => 'en',
            'ContentEncoding' => "UTF-8",
            'ContentDisposition'=> "attachment; filename=\"default.txt\"",
            'CacheControl' => "no-cache",
            'ContentMD5'=>'movf4FeaK/4LQyz5FP1oiQ=='
    ));
    $uploadID1 = $result['UploadId'];
    sleep(5);
    $result = $client->createMultipartUpload(array(
            'Bucket' => $bucketname,
            'Key'	 => 'xtest2.txt',
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
    
    //List
    $result=$client->listMultipartUploads(array(
            'Bucket' => $bucketname,
            'ContentMD5' => 'false',
            'ValidateMD5' => 'false',
    ));
    
    //echo $result;
    
    $client->uploadPartCopy(array(
            'CopySource' => $bucketname."/test1.txt",
            'Bucket' => $bucketname,
            'Key'	 => 'test1.txt',
            'PartNumber' => '1',
            'Expire' => "GMT ".$datetime->format('c'),
            'UploadId'=> $uploadID1,
    ));
    
    $client->uploadPartCopy(array(
            'CopySource' => $bucketname."/test1.txt",
            'Bucket' => $bucketname,
            'Key'	 => 'xtest2.txt',
            'PartNumber' => '1',
            'CopySourceIfmodifiedSince' =>  "GMT ".$datetime2->format('c'),
            'UploadId'=> $uploadID2
    ));
    
    $client->uploadPartCopy(array(
            'CopySource' => $bucketname."/test1.txt",
            'Bucket' => $bucketname,
            'Key'	 => 'xtest2.txt',
            'PartNumber' => '2',
            'CopySourceIfUnmodifiedSince' =>  "GMT ".$datetime->format('c'),
            'UploadId'=> $uploadID2
    ));
    
    $result=$client->listParts(array(
            'Bucket' => $bucketname,
            'Key'	 => 'xtest2.txt',
            'UploadId'=> $uploadID2,
    ));
    
    $client->deleteObject(array(
        'Bucket'=>$bucketname,
        'Key'=>'test1.txt'
    ));
    
    $client->abortMultipartUpload(array(
            'Bucket' => $bucketname,
            'Key'	 => 'test1.txt',
            'UploadId'=> $uploadID1
    ));
    $client->abortMultipartUpload(array(
            'Bucket' => $bucketname,
            'Key'	 => 'xtest2.txt',
            'UploadId'=> $uploadID2
    ));
    
    $client->deleteBucket(array(
            'Bucket' => $bucketname
    ));
}

try {
    $bucketname=$argv[1];
    $userAEmail=$argv[4];
    ;
    $userBCanonicalId=$argv[5];
    ;
    $ownerCanonicalId=$argv[6];
    ;
    echo "MultiPartUpload testing...\n";
    /*
    * test 1.Normal Initial MPU
    *		2.Normal List MPU
    *		3.Upload part
    *		4.List Uploaded Parts
    *		5.Complete MPU
    */
    BasicMPU();


    /*
    * test 1.Normal Initial MPU
    *		2.Normal List MPU
    *		3.Upload part
    *		4.List Uploaded Parts with Parameters
    *		5.Abort
    */
    AbortMPU();
    
    /*
    * test 1.Normal Initial MPU
    *		2.Normal List MPU
    *		3.Upload part
    *		4.List Uploaded Parts with Parameters
    *		5.Abort
    */
    ListPart();
    
    /*
    * test 1. Initial MPU with parameters
    * 		2. copy part with parameters
    * 		3. getObject with parameters
    * 		4. headObject with parameters
    * 		5. List MPU with parameters
    * 		6. Abort
    */
    ListMPU();
} catch (S3Exception $e) {
    echo "Caught an AmazonServiceException.", "\n";
    echo "Error Message:    " . $e->getAWSErrorMessage(). "\n";
    echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
    echo "AWS Error Code:   " . $e->getAwsErrorCode(). "\n";
    echo "Error Type:       " . $e->getAwsErrorType(). "\n";
    echo "Request ID:       " . $e->getAwsRequestId(). "\n";
}
