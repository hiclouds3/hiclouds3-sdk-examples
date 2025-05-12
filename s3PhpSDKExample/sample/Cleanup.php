<?php
use Aws\S3\Exception\S3Exception;

require_once 'client.php';

function ExpectException($e, $statuscode)
{
    if ($e->getStatusCode() != $statuscode) {
        echo "Caught an AmazonServiceException.\n";
        echo "Error Message:    " . $e->getMessage() . "\n";
        echo "HTTP Status Code: " . $e->getStatusCode() . "\n";
        echo "AWS Error Code:   " . $e->getAwsErrorCode() . "\n";
        echo "Error Type:       " . $e->getAwsErrorType() . "\n";
        echo "Request ID:       " . $e->getAwsRequestId() . "\n";
    }
}

function cleanBucket($bucketname)
{
    global $client;

    try {
        // Disable Bucket Versioning
        $client->putBucketVersioning([
            'Bucket' => $bucketname,
            'VersioningConfiguration' => [
                'Status' => 'Suspended',
            ],
        ]);

        // List all object versions in the bucket
        $versions = $client->listObjectVersions(['Bucket' => $bucketname])->get('Versions');

        // Delete all object versions
        if (!empty($versions)) {
            $objectsToDelete = array_map(function ($version) {
                return [
                    'Key' => $version['Key'],
                    'VersionId' => $version['VersionId'],
                ];
            }, $versions);

            $client->deleteObjects([
                'Bucket' => $bucketname,
                'Delete' => [
                    'Objects' => $objectsToDelete,
                    'Quiet' => true,
                ],
            ]);
        }

        // Delete the bucket
        $client->deleteBucket(['Bucket' => $bucketname]);
        echo "Bucket $bucketname deleted successfully.\n";
    } catch (S3Exception $e) {
        ExpectException($e, 404);
    }
}

function cleanBucketSuppressed($bucketname)
{
    try {
        echo "> Cleanup... $bucketname\n";
        cleanBucket($bucketname);
        // echo "Cleanup completed for $bucketname\n";
    } catch (S3Exception $e) {
        echo "Error during cleanup for $bucketname: " . $e->getMessage() . "\n";
        ExpectException($e, 404);
    } catch (Exception $e) {
        echo "Unexpected error during cleanup for $bucketname: " . $e->getMessage() . "\n";
    }
}

