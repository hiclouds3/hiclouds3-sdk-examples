<?php
 require './vendor/autoload.php';
 use Aws\S3\S3Client;

 $client = new S3Client(array(
	'credentials' => array(
		'key'    => 'Enter Your AccessKey Here',
		'secret' => 'Enter Your SecretKey Here'
	),
	'scheme' => 'http',
	'endpoint' => 'http://s3.hicloud.net.tw',
	'version' => '2006-03-01',
    'region' => 'us-east-1'
));
?>
