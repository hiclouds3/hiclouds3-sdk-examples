<?php
use Aws\S3\Exception\S3Exception;

require 'client.php';

function ExpectException($e, $statuscode)
{
    if ($e->getStatusCode()!=$statuscode) {
        echo "Expected Status Code : $statuscode , get another Exception...";
        echo "Error Message:    " . $e->getMessage()."<br>";
        echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
        echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
        echo "Error Type:       " . $e->getExceptionType()."<br>";
        echo "Request ID:       " . $e->getRequestId()."<br>";
    }
}

function cleanBucket($bucketname)
{
    global $client;
    
    try {
        //Disable Bucket Version
        $client->putBucketVersioning(array(
            'Bucket' => $bucketname,
            'VersioningConfiguration' => [
                'MFADelete' => 'Disabled',
                'Status' => 'Suspended',
            ],
        ));
    
        $versions = $client->listObjectVersions(array('Bucket' => $bucketname))->getPath('Versions');
        
        // 4. Delete the object versions.
        if (!is_null($versions)) {
            $result = $client->deleteObjects(array(
                'Bucket' => $bucketname,
                'Delete' => [
                    'Objects' => array_map(function ($version) {
                        return array(
                                'Key'       => $version['Key'],
                                'VersionId' => $version['VersionId']
                        );
                    }, $versions),
                ]
            ));
        }
        
        //DeleteBucket
        $client->deleteBucket(array('Bucket' => $bucketname));
    } catch (S3Exception $e) {
        ExpectException($e, 404);
    }
}

try {
    echo "Cleanup...";
    echo $argv[1];
    echo "\n";
    
    cleanBucket($argv[1]);
} catch (S3Exception $e) {
    ExpectException($e, 404);
}
