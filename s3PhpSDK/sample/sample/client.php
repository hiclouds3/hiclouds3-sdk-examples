<?php
 use Aws\S3\S3Client;
 use Guzzle\Plugin\Log\LogPlugin;
 require 'aws.phar';

 $client = S3Client::factory(array(
 		    'key'    => 'Enter Your AccessKey Here',
 		    'secret' => 'Enter Your SecretKey Here',
 			'scheme' => 'http',
 			'base_url' => 'http://s3.hicloud.net.tw:80',
));
 //For Debugging
 // $client->addSubscriber(LogPlugin::getDebugPlugin());
?>