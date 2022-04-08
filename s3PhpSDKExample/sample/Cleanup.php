<?php
use Aws\S3\Exception\S3Exception;

require 'client.php';

function ExpectException($e, $statuscode)
{
    if ($e->getStatusCode()!=$statuscode) {
		echo "Caught an AmazonServiceException.", "\n";
		echo "Error Message:    " . $e->getAWSErrorMessage(). "\n";
		echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
		echo "AWS Error Code:   " . $e->getAwsErrorCode(). "\n";
		echo "Error Type:       " . $e->getAwsErrorType(). "\n";
		echo "Request ID:       " . $e->getAwsRequestId(). "\n";
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
