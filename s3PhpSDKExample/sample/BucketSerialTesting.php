<?php
require 'client.php';

use Aws\S3\Enum\CannedAcl;
use Aws\S3\Exception\S3Exception;

function createSampleFile()
{
    $temp = tmpfile();
    $content = "abcdefghijklmnopqrstuvwxyz\n01234567890112345678901234\n!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234\nabcdefghijklmnopqrstuvwxyz\n";
    fwrite($temp, $content);
    fseek($temp, 0);
    return $temp;
    fclose($temp); // this removes the file
}

function createBucket($bucketname)
{
    global $client;
    
    try {
        $result = $client->createBucket(array(
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
}

function putObject($bucketname, $objName, $tmpfile, $ACL = CannedAcl::PRIVATE_ACCESS)
{
    global $client;
    $result = $client->putObject(array(
            'Bucket' => $bucketname,
            'Key'    => $objName,
            'Body'   => $tmpfile,
            'ACL'    => $ACL
    ));
}

// Show Objects in specefic buckets, prefix(folder) is optional.
function showObjectList($bucketname, $delimiter=null, $marker=null, $prefix = null, $maxkey=1000)
{
    global $client;
    echo "<br>List of objects in '$bucketname':";
    if ($delimiter != null) {
        echo "with delimiter ' $delimiter '<br>";
    }
    if ($marker != null) {
        echo "with marker ' $marker '<br>";
    }
    if ($maxkey != 1000) {
        echo "with maxkey ' $maxkey '<br>";
    }
    if ($prefix != null) {
        echo "with prefix ' $prefix '<br>";
    }
    
    $result = $client->listObjects(array(
            'Bucket' 	=> $bucketname ,
            'Delimiter' => $delimiter ,
            'Marker' 	=> $marker ,
            'MaxKeys' 	=> $maxkey,
            'Prefix' 	=> $prefix
    ));
    $count=0;
    if ($result['Contents'] != null) {
        foreach ($result['Contents'] as $obj) {
            echo " - ".$obj['Key']."<br>";
            $count++;
        }
    }
    echo "Total: $count objects<br>";
}

function showBucketList()
{
    global $client;
    global $array;
    //echo "Bucket List: ";
    $result = $client->listBuckets();
    $array = array();

    foreach ($result['Buckets'] as $bucket) {
        echo " - ". $bucket['Name'] . "<br>";
        array_push($array, $bucket['Name']);
    }
}

function delObject($bucketname, $objName)
{
    global $client;
    $result = $client->deleteObject(array(
            'Bucket' => $bucketname ,
            'Key' => $objName
    ));
    echo $result['Key'];
}

function delBucket($bucketname)
{
    global $client;
    try {
        $client->deleteBucket(array('Bucket' => $bucketname));
    } catch (S3Exception $e) {
		echo "Caught an AmazonServiceException.", "\n";
		echo "Error Message:    " . $e->getMessage(). "\n";
		echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
		echo "AWS Error Code:   " . $e->getExceptionCode(). "\n";
		echo "Error Type:       " . $e->getExceptionType(). "\n";
		echo "Request ID:       " . $e->getRequestId(). "\n";
    }
}

// Instantiate the S3 client with your AWS credentials and desired AWS region
$content = "abcdefghijklmnopqrstuvwxyz\n01234567890112345678901234\n!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234\nabcdefghijklmnopqrstuvwxyz\n";


function RegionPutBucket()
{
    //echo "Region Put Bucket";
    global $client;
    global $bucketname3;
    createBucket($bucketname3, 'EU');
    $client->headBucket(array(
            'Bucket' => $bucketname3
    ));
    delBucket($bucketname3);
}

function ACLPutBucket()
{
    global $client;
    global $bucketname;
    
    $client->createBucket(array(
        'Bucket' => $bucketname ,
        'ACL'	 => 'public-read'
    ));
            
    $result = $client->getBucketAcl(array(
        'Bucket' => $bucketname,
    ));
    
    echo json_encode($result['Grants']), "\n";
    putObject($bucketname, "/photo/test1.jpg", createSampleFile());
    putObject($bucketname, "/photo/test2.jpg", createSampleFile());
    putObject($bucketname, "/photo/test3.jpg", createSampleFile());
            
    delObject($bucketname, "/photo/test1.jpg");
    delObject($bucketname, "/photo/test2.jpg");
    delObject($bucketname, "/photo/test3.jpg");
    
    delBucket($bucketname);
}

function ParameterPutBucket()
{
    global $client;
    global $bucketname;
    $fileName = "apple.jpg";
    $fileName2 = "photos/2006/January/sample.jpg";
    $fileName3 = "photos/2006/February/sample2.jpg";
    $fileName4 = "asset.txt";
    
    createBucket($bucketname);
    
    putObject($bucketname, $fileName, createSampleFile());
    putObject($bucketname, $fileName2, createSampleFile());
    putObject($bucketname, $fileName3, createSampleFile());
    putObject($bucketname, $fileName4, createSampleFile());

    showObjectList($bucketname);
    showObjectList($bucketname, null, null, "photos/");
    showObjectList($bucketname, '/');
    showObjectList($bucketname, null, null, null, 2);
    showObjectList($bucketname, '/', null, "photos/2006");
    showObjectList($bucketname, null, $fileName);
    
    
    delObject($bucketname, $fileName);
    delObject($bucketname, $fileName2);
    delObject($bucketname, $fileName3);
    delObject($bucketname, $fileName4);
    delBucket($bucketname);
}
$bucketname = $argv[1];
$bucketname2 = $argv[2];
$bucketname3 = $argv[3];
echo "Bucket Serial Testing....", "\n";
/*
 * test 1. bucket with region(type Region & String)
 *      2. Get Service
 *      3. Delete Bucket
 */
RegionPutBucket();
/*
 * test 1. bucket with Canned ACL & normal put bucket
 *      2. Normal GetBucket
 */
ACLPutBucket();
/*
 * test 1. GetBucket with parameters
 */
ParameterPutBucket();
