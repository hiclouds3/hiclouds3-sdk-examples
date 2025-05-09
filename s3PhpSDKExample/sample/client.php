<?php
require './vendor/autoload.php';
use Aws\S3\S3Client;
use Aws\Middleware;
use GuzzleHttp\Psr7\Utils;

// Hicloud S3 credentials
$accessKey = "";
$secretKey = "";

// Add check if either $accessKey or $secretKey is empty
if (empty($accessKey) || empty($secretKey)) {
	die("Error: Access key or secret key is empty. Please provide valid credentials in client.php.\n");
}

// Initialize the S3 client with configuration compatible with AWS SDK 3.343.6
$client = new S3Client([
	'region' => 'us-east-1',
	'endpoint' => 'https://s3.hicloud.net.tw',
	'credentials' => [
		'key' => $accessKey,
		'secret' => $secretKey,
	],
	// Set proxy (optional)
	// 'http' => [
	// 	'proxy' => [
	// 		'http' => 'tcp://xx.x.x.x:8080',
	// 		'https' => 'tcp://xx.x.x.x:8080',
	// 	]
	// ]

]);

$handlerList = $client->getHandlerList();
$handlerList->appendBuild(
	Middleware::mapRequest(static function ($request) {
		// Use this middleware to add Content-MD5 header for PUT and POST requests due to integrity change on AWS SDK
		// https://github.com/aws/aws-sdk-php/issues/3062
		$method = $request->getMethod();
		// echo "...Used method: $method\n";
		if (in_array($method, ['PUT', 'POST']) && !$request->hasHeader('x-amz-copy-source')) {
			$body = $request->getBody();
			$contentMd5 = base64_encode(Utils::hash($body, 'md5', true));

			return $request->withHeader('Content-MD5', $contentMd5);
		}

		return $request;
	})
);
