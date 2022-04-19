<?php

use Aws\S3\Enum\CannedAcl;
use Aws\S3\Exception\S3Exception;

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

function createBucket($bucketname, $loc=null)
{
    global $client;
    try {
        $result = $client->createBucket(array(
                'Bucket' => $bucketname ,
                'LocationConstraint' => $loc
        ));
    } catch (BucketAlreadyExistsException $e) {
        echo 'Bucket "'. $bucketname .'" already exists! <br>' .$e->getMessage()."<br><br>" ;
    } catch (S3Exception $e) {
        echo "Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.";
        echo "Error Message:    " . $e->getMessage()."<br>";
        echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
        echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
        echo "Error Type:       " . $e->getExceptionType()."<br>";
        echo "Request ID:       " . $e->getRequestId()."<br>";
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

function showObjectACL($bucketname, $file)
{
    global $client;
    $result=$client->getObjectAcl(array(
            'Bucket' => $bucketname,
            'Key' => $file
    ));
    echo "\n";
    echo "<br><br>Owner DisplayName: " . $result['Owner']['DisplayName'] . "<br>";
    echo "Owner ID: " . $result['Owner']['ID'] . "<br>";
    foreach ($result['Grants'] as $Grantee) {
        if (! empty($Grantee['Grantee']['Type'])) {
            echo  "Grantee type: " . $Grantee['Grantee']['Type'] . "<br>";
        }
        if (! empty($Grantee['Grantee']['ID'])) {
            echo  "Grantee ID: " . $Grantee['Grantee']['ID'] ."<br>";
        }
        if (! empty($Grantee['Grantee']['URI'])) {
            echo  "Grantee URI: " . $Grantee['Grantee']['URI'] ."<br>";
        }
        if (! empty($Grantee['Grantee']['DisplayName'])) {
            echo  "Grantee DisplayName: " . $Grantee['Grantee']['DisplayName'] . "<br>";
        }
        if (! empty($Grantee['Permission'])) {
            echo  "Grantee Permission: " . $Grantee['Permission']."<br><br>";
        }
    }
    echo "\n";
}

function showBucketACL($bucketname)
{
    global $client;
    $result=$client->getBucketAcl(array(
        'Bucket' => $bucketname
    ));
    echo "\n";
    echo "<br><br>Owner DisplayName: " . $result['Owner']['DisplayName'] . "<br>";
    echo "Owner ID: " . $result['Owner']['ID'] . "<br>";
    foreach ($result['Grants'] as $Grantee) {
        if (! empty($Grantee['Grantee']['Type'])) {
            echo  "Grantee type: " . $Grantee['Grantee']['Type'] . "<br>";
        }
        if (! empty($Grantee['Grantee']['ID'])) {
            echo  "Grantee ID: " . $Grantee['Grantee']['ID'] ."<br>";
        }
        if (! empty($Grantee['Grantee']['URI'])) {
            echo  "Grantee URI: " . $Grantee['Grantee']['URI'] ."<br>";
        }
        if (! empty($Grantee['Grantee']['DisplayName'])) {
            echo  "Grantee DisplayName: " . $Grantee['Grantee']['DisplayName'] . "<br>";
        }
        if (! empty($Grantee['Permission'])) {
            echo  "Grantee Permission: " . $Grantee['Permission']."<br><br>";
        }
    }
    echo "\n";
}

function BasicputBucket()
{
    global $bucketname;
    global $client;
    global $userAEmail;
    global $ownerCanonicalId;
    
    //CreateBucket
    createBucket($bucketname);
    //SetBucketACL
    $client->putBucketAcl(array(
        'Bucket' => $bucketname ,
        'Grants' => [
            [
                'Grantee' => [
                    'ID' => "{$ownerCanonicalId}",
                    'Type' => 'CanonicalUser',
                ],
                'Permission' => 'FULL_CONTROL',
            ],
            [
                'Grantee' => [
                    'EmailAddress' => "{$userAEmail}",
                    'Type' => 'AmazonCustomerByEmail',
                ],
                'Permission' => 'FULL_CONTROL',
            ],
            [
                'Grantee' => [
                    'Type' => 'Group',
                    'URI' => 'http://acs.amazonaws.com/groups/global/AllUsers'
                ],
                'Permission' => 'FULL_CONTROL',
            ],
        ],
        'Owner' => [
            'ID' => "{$ownerCanonicalId}"
        ]
    ));
    
    //SetBucketACL by Canned ACL
    $client->putBucketAcl(array(
            'Bucket' => $bucketname ,
            'ACL'	 => 'public-read'
    ));
    
    showBucketACL($bucketname);
    //DeleteBucket
    $client->deleteBucket(array('Bucket' => $bucketname));
}

function putBucketV()
{
    global $bucketname;
    global $client;
    global $userAEmail;
    global $ownerCanonicalId;
        
    //CreateBucket
    $file="testv.txt";
    createBucket($bucketname);
        
    //Enable Bucket Version
    $client->putBucketVersioning(array(
        'Bucket' => $bucketname,
        'Status' => 'Enabled'
    ));
    
    //PutObject
    putObject($bucketname, $file, createSampleFile());
    
    //GetObject
    $result=$client->getObject(array(
        'Bucket' => $bucketname,
        'Key'	 => $file
    ));
        
    //SetObjectACL
    $client->putObjectAcl(array(
        'Bucket' => $bucketname ,
        'Key'	 => $file ,
        'Grants' => [
            [
                'Grantee' => [
                    'ID' => "{$ownerCanonicalId}",
                    'Type' => 'CanonicalUser',
                ],
                'Permission' => 'FULL_CONTROL',
            ],
            [
                'Grantee' => [
                    'EmailAddress' => "{$userAEmail}",
                    'Type' => 'AmazonCustomerByEmail',
                ],
                'Permission' => 'FULL_CONTROL',
            ],
            [
                'Grantee' => [
                    'Type' => 'Group',
                    'URI' => 'http://acs.amazonaws.com/groups/global/AllUsers'
                ],
                'Permission' => 'FULL_CONTROL',
            ],
        ],
        'Owner' => [
            'ID' => "{$ownerCanonicalId}"
        ],
    ));

    showObjectACL($bucketname, $file);
        
    //List all versions in your bucket
    $result=$client->listObjectVersions(array(
                'Bucket' => $bucketname
        ));
        
    $result = $client->listObjectVersions(array(
            'Bucket' => $bucketname,
        ));
    $i=0;
    foreach ($result['Versions'] as $v) {
        $a[$i] = $v['VersionId'];
        $i++;
    }
        
    //Delete Objects
    $objs = array();
    for ($i=0;$i<count($a);$i++) {
        array_push($objs, array( 'Key' => $file, 'VersionId' => $a[$i]));
    }

    if (count($objs) > 0) {
        $result = $client->deleteObjects(array(
            'Bucket' => $bucketname,
            'Objects' => $objs
        ));
    }
        
    //DeleteBucket
    $client->deleteBucket(array('Bucket' => $bucketname));
}

$bucketname=$argv[1];
$userAEmail=$argv[4];
$ownerCanonicalId=$argv[5];

try {
    echo "ACLSerialTesting...";
    /*
     * test 1. create Bucket
     * 		2. put canned ACL & ACP
     * 		2. get Bucket List
     * 		3. get Bucket ACL & ACP
     *
     */
    BasicputBucket();
    /*
     * test 1. create Bucket
     * 		2. put Bucket versioning
     * 		3. put object
     * 		4. get object (version ID)
     * 		5. put canned ACL & ACP
     */
    putBucketV();
} catch (S3Exception $e) {
    echo "Caught an AmazonServiceException.", "\n";
    echo "Error Message:    " . $e->getMessage(). "\n";
    echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
    echo "AWS Error Code:   " . $e->getExceptionCode(). "\n";
    echo "Error Type:       " . $e->getExceptionType(). "\n";
    echo "Request ID:       " . $e->getRequestId(). "\n";
}
