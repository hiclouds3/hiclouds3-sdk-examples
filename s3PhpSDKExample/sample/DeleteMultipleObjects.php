<?php
use Aws\S3\Exception\S3Exception;

require 'client.php';

function createSampleFile()
{
    $temp = tmpfile();
    $content = "abcdefghijklmnopqrstuvwxyz\n01234567890112345678901234\n!@#$%^&*()-=[]{};':',.<>/?\n01234567890112345678901234\nabcdefghijklmnopqrstuvwxyz\n";
    fwrite($temp, $content);
    fseek($temp, 0);
    return $temp;
    fclose($temp); // this removes the file
}
function showResult($result)
{
    if (!empty($result['Versions'])) {
        foreach ($result['Versions'] as $v) {
            echo "File Name: ".$v['Key']."\n";
            echo "VersionID: ".$v['VersionId']."\n";
            echo "File Size: ".$v['Size']."\n";
            echo "File ETag: ".$v['ETag']."\n";
        }
    }
}

try {
    echo "Delete Multiple Objects Testing...\n";
    $bucketname=$argv[1];
    
    $client->createBucket(array(
            'Bucket' => $bucketname
    ));

    $client->putBucketVersioning(array(
        'Bucket' => $bucketname,
        'Status' => 'Enabled',
    ));
    
    $result=$client->putObject(array(
        'Bucket' => $bucketname,
        'Key'    => '/photo/cht2.jpg',
        'Body'   => createSampleFile()
    ));
    $vid1=$result['VersionId'];
    
    $result=$client->putObject(array(
        'Bucket' => $bucketname,
        'Key'    => '/photo/cht2.jpg',
        'Body'   => createSampleFile()
    ));
    $vid2=$result['VersionId'];
    $result=$client->putObject(array(
        'Bucket' => $bucketname,
        'Key'    => '/photo/cht2.jpg',
        'Body'   => createSampleFile()
    ));
    $vid3=$result['VersionId'];
    
    $client->getObject(array(
        'Bucket' => $bucketname,
        'Key'    => '/photo/cht2.jpg',
        'VersionId'=> $vid1
    ));
    
    $client->headObject(array(
            'Bucket' => $bucketname,
            'Key'    => '/photo/cht2.jpg',
            'VersionId'=> $vid2
    ));
    
    
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname
    ));
    //showResult($result);
    
    //echo "\n-List object versionwith delimeter 'photo/'\n";
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
            'Delimiter' => 'photo/'
    ));
    //showResult($result);
    //echo "\n-List object version with max-key=2\n";
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
            'MaxKeys' => 2
    ));
    //showResult($result);
    //echo "\n-List all object version with keyMarker /photo/cht2.jpg\n";
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
            'KeyMarker' => '/photo/cht2.jpg'
    ));
    //showResult($result);
    //echo "\n-List object version with prefix & Maxkeys\n";
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
            'Prefix' => 'photo/',
            'MaxKeys' => 2
    ));
    //showResult($result);

    $result = $client->listObjectVersions(array(
        'Bucket' => $bucketname,
    ));
    showResult($result);
    $client->deleteObjects(array(
            'Bucket' => $bucketname,
            'Objects' => array_map(function ($version) {
                return array(
                        'Key'       => $version['Key'],
                        'VersionId' => $version['VersionId']
                );
            }, $result['Versions'])
    ));

    $client->deleteBucket(array(
            'Bucket' => $bucketname,
    ));
} catch (S3Exception $e) {
    echo "Caught an AmazonServiceException.", "\n";
    echo "Error Message:    " . $e->getMessage(). "\n";
    echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
    echo "AWS Error Code:   " . $e->getExceptionCode(). "\n";
    echo "Error Type:       " . $e->getExceptionType(). "\n";
    echo "Request ID:       " . $e->getRequestId(). "\n";
}
