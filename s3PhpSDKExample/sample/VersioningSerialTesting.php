<?php

require 'client.php';

function createSampleFile()
{
    $temp = tmpfile();
    $content = "abcdefghijklmnopqrstuvwxyz\n01234567890112345678901234\n!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234\nabcdefghijklmnopqrstuvwxyz\n";
    fwrite($temp, $content);
    fseek($temp, 0);
    return $temp;
    fclose($temp); // this removes the file
}
function showResult($result)
{
    if (!empty($result['Versions'])) {
        foreach ($result['Versions'] as $v) {
            echo "File Name: ".$v['Key']."<br>";
            echo "VersionID: ".$v['VersionId']."<br>";
            echo "File Size: ".$v['Size']."<br>";
            echo "File ETag: ".$v['ETag']."<br>";
            echo "-------------------------------------------------------------------------------<br>";
        }
    }
}

try {
    echo "Versioning Serial Testing...\n";
    $bucketname=$argv[1];
    
    $client->createBucket(array(
            'Bucket' => $bucketname
    ));
    
    $result=$client->getBucketVersioning(array(
            'Bucket' => $bucketname,
    ));
    
    $client->putBucketVersioning(array(
        'Bucket' => $bucketname,
        'Status' => 'Enabled'
    ));
    
    $result=$client->getBucketVersioning(array(
            'Bucket' => $bucketname,
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
    
    //echo "<br>-List object versionwith delimeter 'photo/'<br>";
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
            'Delimiter' => 'photo/'
    ));
    //showResult($result);
    //echo "<br>-List object version with max-key=2<br>";
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
            'MaxKeys' => 2
    ));
    //showResult($result);
    //echo "<br>-List all object version with keyMarker /photo/cht2.jpg<br>";
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
            'KeyMarker' => '/photo/cht2.jpg'
    ));
    //showResult($result);
    //echo "<br>-List object version with prefix & Maxkeys<br>";
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
            'Prefix' => 'photo/',
            'MaxKeys' => 2
    ));
    //showResult($result);
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
    ));
    $i=0;
    foreach ($result['Versions'] as $v) {
        $a[$i] = $v['VersionId'];
        $i++;
    }

    $client->putBucketVersioning(array(
        'Bucket' => $bucketname,
        'Status' => 'Suspended'
    ));

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
