
<?php
require 'client.php';

date_default_timezone_set('Asia/Taipei');

function createSampleFile()
{
    $temp = tmpfile();
    $content = "abcdefghijklmnopqrstuvwxyz<br>01234567890112345678901234<br>!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234<br>abcdefghijklmnopqrstuvwxyz<br>";
    fwrite($temp, $content);
    fseek($temp, 0);
    return $temp;
    fclose($temp); // this removes the file
}
echo "Object Serial Testing...\n";
$bucketname=$argv[1];
$bucketname2=$argv[2];
$objName="test.txt";
$objName2="test2.txt";
$datetime = new DateTime('17 Oct 2100');
$datetime2 = new DateTime('17 Oct 2019');

$client->createBucket(array(
    'Bucket' => $bucketname
));
$client->createBucket(array(
    'Bucket' => $bucketname2
));

$client->putObject(array(
    'Bucket' => $bucketname,
    'Key'    => $objName,
    'Body'   => createSampleFile(),
    'Expires' => "GMT ".$datetime->format('c'),
));

$result=$client->getObject(array(
    'Bucket' => $bucketname,
        'Key'=> $objName
));

$client->deleteObject(array(
    'Bucket' => $bucketname,
    'Key'	 => $objName
));


//put with acl & custom meta
$client->putObject(array(
        'Bucket' => $bucketname,
        'Key'    => $objName,
        'Body'   => createSampleFile(),
        'ACL'	 => 'public-read',
        'Metadata' => array(
                'x-amz-meta-flower' => 'lily',
                'x-amz-meta-color' => "pink"
        ),
        'ContentType' => "text/plain",
        'ContentLength' => '150',
        'ContentDisposition'=> "attachment; filename=\"default.txt\"",
        'ContentMD5'=>'movf4FeaK/4LQyz5FP1oiQ=='
));

$result=$client->getObject(array(
        'Bucket' => $bucketname,
        'Key'=> $objName
));
$a=str_replace('"', "", $result['ETag']);
//echo $a;
$client->copyObject(array(
        'Bucket' => $bucketname2,
        'Key'    => $objName2,
        'Body'   => createSampleFile(),
        'ACL'	 => 'public-read-write',
        'CopySource'=> $bucketname.'/'.$objName,
        'MetadataDirective' => 'REPLACE',
        'Metadata' => array(
                'x-amz-meta-flower' => 'lily',
                'x-amz-meta-color' => "pink"
        ),
        'ContentType' => "image/jpeg",
));

$client->copyObject(array(
        'Bucket' => $bucketname2,
        'Key'    => 'test4',
        'Body'   => createSampleFile(),
        'ACL'	 => 'public-read-write',
        'CopySource'=> $bucketname.'/'.$objName,
        'Expires' => "GMT ".$datetime->format('c'),
        

));
$client->copyObject(array(
        'Bucket' => $bucketname2,
        'Key'    => 'test5',
        'Body'   => createSampleFile(),
        'ACL'	 => 'public-read-write',
        'CopySource'=> $bucketname.'/'.$objName,
        'CopySourceIfmodifiedSince' =>  "GMT ".$datetime2->format('c')


));
$client->copyObject(array(
        'Bucket' => $bucketname2,
        'Key'    => 'test6',
        'Body'   => createSampleFile(),
        'ACL'	 => 'public-read-write',
        'CopySource'=> $bucketname.'/'.$objName,
        'CopySourceIfUnmodifiedSince' =>  "GMT ".$datetime->format('c')
));

$client->copyObject(array(
        'Bucket' => $bucketname2,
        'Key'    => 'test7',
        'Body'   => createSampleFile(),
        'ACL'	 => 'public-read-write',
        'CopySource'=> $bucketname.'/'.$objName,
        'MetadataDirective' => 'COPY',
        'WebsiteRedirectLocation' => 'http://www.google.com'


));
// Head Object

$client->headObject(array(
        'Bucket' => $bucketname,
        'Key'=> $objName,
        'IfMatch' => $a
));
$client->headObject(array(
        'Bucket' => $bucketname,
        'Key'=> $objName,
        'IfNoneMatch' => '123'
));
$client->headObject(array(
        'Bucket' => $bucketname,
        'Key'=> $objName,
        'IfModifiedSince' => "GMT ".$datetime2->format('c')
));
$client->headObject(array(
        'Bucket' => $bucketname,
        'Key'=> $objName,
        'IfUnmodifiedSince' => "GMT ".$datetime->format('c')
));
$client->headObject(array(
        'Bucket' => $bucketname,
        'Key'=> $objName,
        'Range' => 'bytes=50-100'
));


// Get Object
$client->getObject(array(
		'Bucket' => $bucketname,
		'Key'=> $objName,
		'IfMatch' => $a,
		'Range' => 'bytes=50-100',
		'ResponseContentDisposition' => 'attachment; filename="default.txt"',
		'ResponseContentLanguage' => 'en',
		'ResponseContentType' => 'text/plain',
		'ResponseExpires' => "GMT ".$datetime->format('c'),
));

$client->getObject(array(
		'Bucket' => $bucketname,
		'Key'=> $objName,
		'IfNoneMatch' => '123'
));

$client->getObject(array(
		'Bucket' => $bucketname,
		'Key'=> $objName,
		'IfModifiedSince' => "GMT ".$datetime2->format('c')
));

$client->getObject(array(
		'Bucket' => $bucketname,
		'Key'=> $objName,
		'IfUnmodifiedSince' => "GMT ".$datetime->format('c')
));

$client->getObject(array(
		'Bucket' => $bucketname,
		'Key'=> $objName,
		'Range' => 'bytes=0-100',
		'SaveAs' => '123.456'
));


$client->deleteObject(array(
		'Bucket' => $bucketname,
		'Key'	 => $objName
));
$client->deleteObject(array(
		'Bucket' => $bucketname2,
		'Key'	 => 'test4'
));
$client->deleteObject(array(
		'Bucket' => $bucketname2,
		'Key'	 => 'test5'
));
$client->deleteObject(array(
		'Bucket' => $bucketname2,
		'Key'	 => 'test6'
));
$client->deleteObject(array(
		'Bucket' => $bucketname2,
		'Key'	 => 'test7'
));

$client->deleteObject(array(
		'Bucket' => $bucketname2,
		'Key'	 => $objName2
));
$client->deleteBucket(array(
    	'Bucket' => $bucketname
));
$client->deleteBucket(array(
    	'Bucket' => $bucketname2
));
