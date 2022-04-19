<?php
/*
 * test 1. Basic putBucket
* 		2. put BucketACL
* 		3. put BucketLogging (put log Native & to target bucket)
* 		4. get BucketLogging
* 		5. Delete Bucket
*/

use Aws\S3\Exception\MalformedXMLException;
use Aws\S3\Exception\S3Exception;

require 'client.php';

$bucketname=$argv[1];
$bucketname2=$argv[2];
$ownerCanonicalId=$argv[4];
try {
    echo "\nBucket Logging Serial Testing...\n";
    //CreateBucket
    $client->createBucket(array(
        'Bucket' => $bucketname,
    ));
    $client->createBucket(array(
        'Bucket' => $bucketname2,
    ));
    
    //log_delievery group must have WRITE & READ_ACP Permission
    $acp = [
        
    ];
    
    //SetBucketACL
    $client->putBucketAcl(array(
            'Bucket' => $bucketname,
            'Grants' => [
                [
                    'Grantee' => [
                        'Type' => 'Group',
                        'URI' => 'http://acs.amazonaws.com/groups/global/LogDelivery'
                    ],
                    'Permission' => 'WRITE',
                ],
                [
                    'Grantee' => [
                        'Type' => 'Group',
                        'URI' => 'http://acs.amazonaws.com/groups/global/LogDelivery'
                    ],
                    'Permission' => 'READ_ACP',
                ],
                [
                    'Grantee' => [
                        'Type' => 'CanonicalUser',
                        'ID' => $ownerCanonicalId,
                    ],
                    'Permission' => 'FULL_CONTROL',
                ],
            ],
            'Owner' => [
                'ID' => "{$ownerCanonicalId}"
            ]
    ));
    
    $client->putBucketAcl(array(
            'Bucket' => $bucketname2,
            'Grants' => [
                [
                    'Grantee' => [
                        'Type' => 'Group',
                        'URI' => 'http://acs.amazonaws.com/groups/global/LogDelivery'
                    ],
                    'Permission' => 'WRITE',
                ],
                [
                    'Grantee' => [
                        'Type' => 'Group',
                        'URI' => 'http://acs.amazonaws.com/groups/global/LogDelivery'
                    ],
                    'Permission' => 'READ_ACP',
                ],
                [
                    'Grantee' => [
                        'Type' => 'CanonicalUser',
                        'ID' => $ownerCanonicalId,
                    ],
                    'Permission' => 'FULL_CONTROL',
                ],
            ],
            'Owner' => [
                'ID' => "{$ownerCanonicalId}"
            ]
    ));
    
    //SetBucketLogging
    $client->putBucketLogging(
        array(
            'Bucket' => $bucketname,
            'LoggingEnabled' => array(
                'TargetBucket' => $bucketname2,
                'TargetGrants' => array(
                    [
                        'Grantee' => array(
                            'Type' => 'CanonicalUser',
                            'ID' => $ownerCanonicalId,
                        ),
                        'Permission' => 'FULL_CONTROL',
                    ]
                ),
                'TargetPrefix' => 'log-'
            )
        ),
    );
    
    //Check BucketLogging
    $result=$client->getBucketLogging(array(
        'Bucket' => $bucketname
    ));
    echo json_encode($result['LoggingEnabled']), "\n";
    
    //SetBucketLogging
    $client->putBucketLogging(
        array(
            'Bucket' => $bucketname,
            'BucketLoggingStatus' => [
                'LoggingEnabled' => array(
                    'TargetBucket' => $bucketname,
                    'TargetGrants' => array(
                        [
                            'Grantee' => array(
                                'Type' => 'CanonicalUser',
                                'ID' => $ownerCanonicalId,
                            ),
                            'Permission' => 'FULL_CONTROL',
                        ]
                    ),
                    'TargetPrefix' => 'log-'
                )
            ]
        ),
    );
    
    //Check BucketLogging
    $result=$client->getBucketLogging(array(
            'Bucket' => $bucketname,
    ));
    
    //DeleteBucket
    $client->deleteBucket(array(
            'Bucket' => $bucketname
    ));
    $client->deleteBucket(array(
            'Bucket' => $bucketname2
    ));
} catch (S3Exception $e) {
    echo "Caught an AmazonServiceException.", "\n";
    echo "Error Message:    " . $e->getMessage(). "\n";
    echo "HTTP Status Code: " . $e->getStatusCode(). "\n";
    echo "AWS Error Code:   " . $e->getExceptionCode(). "\n";
    echo "Error Type:       " . $e->getExceptionType(). "\n";
    echo "Request ID:       " . $e->getRequestId(). "\n";
} catch (MalformedXMLException $e) {
    echo $e->__toString();
}
