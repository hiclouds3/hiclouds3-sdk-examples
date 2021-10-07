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

function createSampleFile()
{
	$temp = tmpfile();
	$content = "abcdefghijklmnopqrstuvwxyz\n01234567890112345678901234\n!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234\nabcdefghijklmnopqrstuvwxyz\n";
	fwrite($temp, $content);
	fseek($temp, 0);
	return $temp;
	fclose($temp); // this removes the file
}

function createBucket($bucketname , $loc=null)
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
		echo "<font color=red>¡I</font>Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.<br>";
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
	
// Show Objects in specefic buckets, prefix(folder) is optional.
function showObjectList($bucketname, $delimiter=null, $marker=null, $prefix = null, $maxkey=1000)
{
	global $client;
	echo "<br>List of objects in '$bucketname':<br>";
	if($delimiter != null)
		echo "with delimiter ' $delimiter '<br>";
	if($marker != null)
		echo "with marker ' $marker '<br>";
	if($maxkey != 1000)
		echo "with maxkey ' $maxkey '<br>";
	if($prefix != null)
		echo "with prefix ' $prefix '<br>";

		$result = $client->listObjects( array(
				'Bucket' 	=> $bucketname ,
					'Delimiter' => $delimiter ,
					'Marker' 	=> $marker ,
				'MaxKeys' 	=> $maxkey,
				'Prefix' 	=> $prefix
		));
					$count=0;
	if($result['Contents'] != null){
				foreach ( $result['Contents'] as $obj){
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
		echo "Bucket List: <br>";
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
	try
	{
		$client->deleteBucket(array('Bucket' => $bucketname));
	}catch (S3Exception $e) {
			echo "<font color=red>¡I</font>Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.<br>";
			echo "Error Message:    " . $e->getMessage()."<br>";
			echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
			echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
			echo "Error Type:       " . $e->getExceptionType()."<br>";
			echo "Request ID:       " . $e->getRequestId()."<br>";
	}catch (ExceptionCollection $e) {
		echo "Validation Error:". $e->getMessage() ."<br><br>" ;
	}
}

function showObjectACL($bucketname, $file)
{
	global $client;
	$result=$client->getObjectAcl(array(
			'Bucket' => $bucketname,
			'Key' => $file
	));
	echo "<br><br>Owner DisplayName: " . $result['Owner']['DisplayName'] . "<br>";
	echo "Owner ID: " . $result['Owner']['ID'] . "<br>";
	foreach ( $result['Grants'] as $Grantee)
	{
		if(! empty($Grantee['Grantee']['Type']) )
			echo  "Grantee type: " . $Grantee['Grantee']['Type'] . "<br>";
		if(! empty($Grantee['Grantee']['ID']) )
			echo  "Grantee ID: " . $Grantee['Grantee']['ID'] ."<br>";
		if(! empty($Grantee['Grantee']['URI']) )
			echo  "Grantee URI: " . $Grantee['Grantee']['URI'] ."<br>";
		if(! empty($Grantee['Grantee']['DisplayName']) )
			echo  "Grantee DisplayName: " . $Grantee['Grantee']['DisplayName'] . "<br>";
		if(! empty($Grantee['Permission']) )
			echo  "Grantee Permission: " . $Grantee['Permission']."<br><br>";
	}

}

function showBucketACL($bucketname)
{
	global $client;
	$result=$client->getBucketAcl(array(
		'Bucket' => $bucketname
	));
	echo "<br><br>Owner DisplayName: " . $result['Owner']['DisplayName'] . "<br>";
	echo "Owner ID: " . $result['Owner']['ID'] . "<br>";
	foreach ( $result['Grants'] as $Grantee)	
	{
		if(! empty($Grantee['Grantee']['Type']) )
			echo  "Grantee type: " . $Grantee['Grantee']['Type'] . "<br>";
		if(! empty($Grantee['Grantee']['ID']) )
			echo  "Grantee ID: " . $Grantee['Grantee']['ID'] ."<br>";
		if(! empty($Grantee['Grantee']['URI']) )
			echo  "Grantee URI: " . $Grantee['Grantee']['URI'] ."<br>";
		if(! empty($Grantee['Grantee']['DisplayName']) )
			echo  "Grantee DisplayName: " . $Grantee['Grantee']['DisplayName'] . "<br>";
		if(! empty($Grantee['Permission']) )
			echo  "Grantee Permission: " . $Grantee['Permission']."<br><br>";
	}
	
}

function showBucketACP($bucketname)
{
	global $client;
	$result=$client->getBucketPolicy(array(
			'Bucket' => $bucketname
	));
	echo "<br><br>Owner DisplayName: " . $result['Owner']['DisplayName'] . "<br>";
	echo "Owner ID: " . $result['Owner']['ID'] . "<br>";
	foreach ( $result['Grants'] as $Grantee)
	{
		if(! empty($Grantee['Grantee']['Type']) )
			echo  "Grantee type: " . $Grantee['Grantee']['Type'] . "<br>";
		if(! empty($Grantee['Grantee']['ID']) )
			echo  "Grantee ID: " . $Grantee['Grantee']['ID'] ."<br>";
		if(! empty($Grantee['Grantee']['URI']) )
			echo  "Grantee URI: " . $Grantee['Grantee']['URI'] ."<br>";
		if(! empty($Grantee['Grantee']['DisplayName']) )
			echo  "Grantee DisplayName: " . $Grantee['Grantee']['DisplayName'] . "<br>";
		if(! empty($Grantee['Permission']) )
			echo  "Grantee Permission: " . $Grantee['Permission']."<br><br>";
	}

}

function BasicputBucket(){
	global $bucketname;
	global $client;
	global $userAEmail;
	global $ownerCanonicalId;
	
	$acp = AcpBuilder::newInstance();
	$acp->addGrantForEmail('FULL_CONTROL', $userAEmail); //must be another registered user's mail
	$acp->setOwner($ownerCanonicalId,"I AM OWNER"); //OwnerID must be correct
	$acp->addGrantForUser('FULL_CONTROL', $ownerCanonicalId);
	$acp->addGrantForGroup('FULL_CONTROL', Group::ALL_USERS);
	$acp2 = $acp->build();

	//CreateBucket
	createBucket($bucketname);
	//SetBucketACL
	$client->putBucketAcl(array(
			'Bucket' => $bucketname ,
			'ContentMD5' => 'false',
			'ACP'	 => $acp2
	));
	
	//SetBucketACL by Canned ACL
	$client->putBucketAcl(array(
			'Bucket' => $bucketname ,
			'ACL'	 => 'public-read'
	));
	
	//DeleteBucket
	$client->deleteBucket(array('Bucket' => $bucketname));
}

function putBucketV()
{
	
	global $bucketname;
	global $client;
	global $userAEmail;
	global $ownerCanonicalId;
	
	$acp = AcpBuilder::newInstance();
	$acp->addGrantForEmail('FULL_CONTROL', $userAEmail); //must be another registered user's mail
	$acp->setOwner($ownerCanonicalId,"I AM OWNER"); //OwnerID must be correct
	$acp->addGrantForUser('FULL_CONTROL', $ownerCanonicalId);
	$acp->addGrantForGroup('FULL_CONTROL', Group::ALL_USERS);
	$acp2 = $acp->build();
		
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
				'ACP'	 => $acp2
		));
		
		//List all versions in your bucket
		$result=$client->listObjectVersions(array(
				'Bucket' => $bucketname
		));
		
		$result = $client->listObjectVersions(array(
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
		));
		
		//DeleteBucket
		$client->deleteBucket(array('Bucket' => $bucketname));
		
}	

//$client->addSubscriber(LogPlugin::getDebugPlugin());
$bucketname=$argv[1];
$userAEmail=$argv[4];
$ownerCanonicalId=$argv[5];

try
{	
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
	
}catch (S3Exception $e) {
		echo "<font color=red>¡I</font>Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.<br>";
		echo "Error Message:    " . $e->getMessage()."<br>";
		echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
		echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
		echo "Error Type:       " . $e->getExceptionType()."<br>";
		echo "Request ID:       " . $e->getRequestId()."<br>";
}catch (RequestException $e) {
		echo $e->getMessage();
}
?>
</pre>