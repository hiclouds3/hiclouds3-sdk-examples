<pre>
<?php
use Aws\S3\Enum\CannedAcl;
use Aws\S3\Model\AcpBuilder;
use Aws\S3\Model\Grantee;
use Aws\S3\Enum\GranteeType;
use Aws\S3\Exception\InvalidArgumentException;
use Aws\S3\Enum\Group;
use Guzzle\Http\Exception\RequestException;
use Aws\S3\Model\ClearBucket;
use Aws\S3\S3Client;
use Guzzle\Plugin\Log\LogPlugin;
use Aws\S3\Exception\S3Exception;

require 'client.php';

function ExpectException($e,$statuscode){
	if($e->getStatusCode()!=$statuscode){
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
	
	try
	{
		//Disable Bucket Version
		$client->putBucketVersioning(array(
				'Bucket' => $bucketname,
				'MFADelete ' => 'Disabled',
				'Status' => 'Suspended'
		));
	
		$versions = $client->listObjectVersions(array('Bucket' => $bucketname))->getPath('Versions');
		
		// 4. Delete the object versions.
		$result = $client->deleteObjects(array(
				'Bucket'  => $bucketname,
				'Objects' => array_map(function ($version) {
					return array(
							'Key'       => $version['Key'],
							'VersionId' => $version['VersionId']
					);
				}, $versions),
		));
		
		
		/*$result = $client->listObjectVersions(array(
			'Bucket' => $bucketname,
		));
		$i=0;
		foreach ( $result['Versions'] as $v)
		{
			$a[$i] = $v['VersionId'];
			$i++;
		}
		
		//Delete Objects
		$b=array();
		for($i=0;$i<count($a);$i++){
			array_push($b, array( 'Key' => $file, 'VersionId' => $a[$i]));
		}
		$client->deleteObjects(array(
				'Quiet' => true,
				'Bucket' => $bucketname,
				'Objects' => $b
		));*/
		
		//DeleteBucket
		$client->deleteBucket(array('Bucket' => $bucketname));
	
	}catch (S3Exception $e){
		ExpectException($e,404);
	}
}	

try
{	
	echo "Cleanup...";
	echo $argv[1];
	
	cleanBucket($argv[1]);
	
}catch (S3Exception $e){
	ExpectException($e,404);
}

?>
</pre>