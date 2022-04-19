<?php
use Aws\S3\Exception\S3Exception;

require 'client.php';

function ExpectException($e, $statuscode)
{
    if ($e->getStatusCode()!=$statuscode) {
		echo "Caught an AmazonServiceException.", "\n";
		echo "Error Message:    " . $e->getMessage(). "\n";
		echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
		echo "AWS Error Code:   " . $e->getExceptionCode(). "\n";
		echo "Error Type:       " . $e->getExceptionType(). "\n";
		echo "Request ID:       " . $e->getRequestId(). "\n";
    }
}

function cleanBucket($bucketname)
{
    global $client;
    
    try {
        //Disable Bucket Version
        $client->putBucketVersioning(array(
            'Bucket' => $bucketname,
            'Status' => 'Suspended'
        ));
    
        $versions = $client->listObjectVersions(array('Bucket' => $bucketname))->getPath('Versions');
        
        // 4. Delete the object versions.
        if (!is_null($versions)) {
            $result = $client->deleteObjects(array(
                'Bucket' => $bucketname,
                'Objects' => array_map(function ($version) {
                    return array(
                            'Key'       => $version['Key'],
                            'VersionId' => $version['VersionId']
                    );
                }, $versions)
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
