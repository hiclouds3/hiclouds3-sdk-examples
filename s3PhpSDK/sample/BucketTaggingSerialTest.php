<pre>
<?php
/*
 * test 1. put Bucket
 * 		2. put BucketTagging
 * 		3. get BucketTagging
 * 		4. Delete BucketTagging
 * 		5. Delete Bucket
 */

use Aws\S3\Exception\S3Exception;
use Guzzle\Service\Exception\ValidationException;

//require './aws-autoloader.php';
require 'client.php';

$bucketname=$argv[2];
$id='testLC';
$id2='testLC2';
$prefix='test.txt';
$datetime = new DateTime('17 Oct 2020');

try
{
echo "BucketTagging Serial testing...<br>";
	
$client->createBucket(array(
	'Bucket' => $bucketname
));

/*$result = $client->putBucketTagging([
    'Bucket' => $bucketname, // REQUIRED
    'Tagging' => [ // REQUIRED
        'TagSet' => [ // REQUIRED
            [
                'Key' => 'Jan', // REQUIRED
                'Value' => 'pink', // REQUIRED
            ],
            [
                'Key' => 'Dec', 
                'Value' => 'blue', 
            ]
        ]
    ],
]);
*/
$result = $client->putBucketTagging(array(
                'Bucket' => $bucketname,
                'TagSet' => array(
                        array(
                                'Key' => 'Jan',
                                'Value' => 'pink'
                        ),array(
                                'Key' => 'Dec',
                                'Value' => 'blue'
                        )
                        
                )
        ));
        
$result = $client->getBucketTagging([
    'Bucket' => $bucketname // REQUIRED
]);
#echo $result;

$result = $client->deleteBucketTagging([
    'Bucket' => $bucketname // REQUIRED
]);

$client->deleteBucket(array(
		'Bucket' => $bucketname
));

}catch(ValidationException $e){
		echo $e->getMessage();
} catch (S3Exception $e) {
	echo "<font color=red>¡I</font>Caught an AmazonServiceException.<br>";
	echo "Error Message:    " . $e->getMessage()."<br>";
	echo "HTTP Status Code: " . $e->getStatusCode()."<br>";
	echo "AWS Error Code:   " . $e->getExceptionCode()."<br>";
	echo "Error Type:       " . $e->getExceptionType()."<br>";
	echo "Request ID:       " . $e->getRequestId()."<br>";
}

?>
</pre>