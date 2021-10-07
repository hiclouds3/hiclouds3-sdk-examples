<pre>
<?php
/*
 * test 1. Basic putBucket
* 		2. put BucketACL
* 		3. put BucketLogging (put log Native & to target bucket)
* 		4. get BucketLogging
* 		5. Delete Bucket
*/

use Aws\S3\Enum\GranteeType;
use Guzzle\Plugin\Log\LogPlugin;
use Aws\S3\Enum\Group;
use Aws\S3\Model\AcpBuilder;
use Aws\S3\Enum\Permission;
use Aws\S3\Exception\MalformedXMLException;

require 'client.php';

$bucketname=$argv[1];
$bucketname2=$argv[2];
$ownerCanonicalId=$argv[4];
try{
	echo "Bucket Logging Serial Testing...";
	//CreateBucket
	$client->createBucket(array(
		'Bucket' => $bucketname,
	));
	$client->createBucket(array(
		'Bucket' => $bucketname2,
	));
	
	//log_delievery group must have WRITE & READ_ACP Permission
	$acp = AcpBuilder::newInstance();
	$acp->setOwner($ownerCanonicalId,"Owner"); //Owner Canonical ID must be correct
	$acp->addGrantForUser('FULL_CONTROL', $ownerCanonicalId);
	$acp->addGrantForGroup(Permission::WRITE, Group::LOG_DELIVERY);
	$acp->addGrantForGroup(Permission::READ_ACP, Group::LOG_DELIVERY);
	$acp2 = $acp->build();
	
	//SetBucketACL
	$client->putBucketAcl(array(
			'Bucket' => $bucketname,
			'ACP'	 => $acp2
	));
	
	$client->putBucketAcl(array(
			'Bucket' => $bucketname2,
			'ACP'	 => $acp2
	));
	
	//SetBucketLogging
	$client->putBucketLogging(array(
			'Bucket' => $bucketname,
			'ContentMD5'=> 'false',
			'LoggingEnabled' => array(
            'TargetBucket' => $bucketname2,
            'TargetGrants' => array(
                'Grant' => array(
                    'Grantee' => array(
                        'Type' => GranteeType::USER,
                        'ID' => $ownerCanonicalId,
                    ),
                    'Permission' => 'FULL_CONTROL',
                ),
            ),
            'TargetPrefix' => 'log-',
        ),
	));
	
	//Check BucketLogging
	$result=$client->getBucketLogging(array(
		'Bucket' => $bucketname, 
	));
	
	//SetBucketLogging
	$client->putBucketLogging(array(
			'Bucket' => $bucketname,
			'LoggingEnabled' => array(
					'TargetBucket' => $bucketname,
					'TargetGrants' => array(
							'Grant' => array(
									'Grantee' => array(
											'Type' => GranteeType::USER,
											'ID' => $ownerCanonicalId,
									),
									'Permission' => 'FULL_CONTROL',
							),
					),
					'TargetPrefix' => 'log-',
			),
	));
	
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
	
}catch (S3Exception $e) {
	echo "<font color=red>¡I</font>Caught an AmazonServiceException.<br>";
	echo "Error Message:    " . $e->getMessage()."<br>";
	echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
	echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
	echo "Error Type:       " . $e->getExceptionType()."<br>";
	echo "Request ID:       " . $e->getRequestId()."<br>";
} catch (MalformedXMLException $e) {
	echo $e->__toString();
}

?>
</pre>