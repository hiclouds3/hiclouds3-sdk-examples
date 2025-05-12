<?php
/*
 * test 1. Basic putBucket
 * 		2. put Object
 * 		3. put BucketWebsite (Basic & with redirect rules)
 * 		4. get BucketWebsite
 * 		5. Delete BucketWebsite
 */

require_once 'client.php';

function websiteSerialTest($bucketname)
{
    global $client;
    try {
        echo "> Website Serial Testing...\n";

        echo "Creating bucket: $bucketname\n";
        $client->createBucket([
            'Bucket' => $bucketname,
            'ACL' => 'public-read'
        ]);

        echo "Uploading error.html object...\n";
        $client->putObject([
            'Body' => '404testchttl<br><title>chttl</title>',
            'Bucket' => $bucketname,
            'Key' => 'error.html',
            'ACL' => 'public-read',
            'WebsiteRedirectLocation' => 'http://google.com'
        ]);

        echo "Uploading index.html object...\n";
        $client->putObject([
            'Body' => 'Hello world!',
            'Bucket' => $bucketname,
            'Key' => 'index.html',
            'ACL' => 'public-read'
        ]);

        echo "Setting bucket website configuration (basic)...\n";
        $client->putBucketWebsite([
            'Bucket' => $bucketname,
            'WebsiteConfiguration' => [
                'ErrorDocument' => [
                    'Key' => 'error.html'
                ],
                'IndexDocument' => [
                    'Suffix' => 'index.html'
                ]
            ]
        ]);

        echo "Getting bucket website configuration...\n";
        $result = $client->getBucketWebsite([
            'Bucket' => $bucketname
        ]);

        echo "Setting bucket website configuration with routing rules...\n";
        $client->putBucketWebsite([
            'Bucket' => $bucketname,
            'WebsiteConfiguration' => [
                'ErrorDocument' => [
                    'Key' => 'error.html'
                ],
                'IndexDocument' => [
                    'Suffix' => 'index.html'
                ],
                'RoutingRules' => [
                    [
                        'Redirect' => [
                            'HostName' => 'www.google.com'
                        ]
                    ]
                ]
            ]
        ]);

        echo "Getting updated bucket website configuration...\n";
        $result = $client->getBucketWebsite([
            'Bucket' => $bucketname
        ]);

        echo "Deleting bucket website configuration...\n";
        $client->deleteBucketWebsite([
            'Bucket' => $bucketname
        ]);

        echo "Deleting error.html object...\n";
        $client->deleteObject([
            'Bucket' => $bucketname,
            'Key' => 'error.html'
        ]);

        echo "Deleting index.html object...\n";
        $client->deleteObject([
            'Bucket' => $bucketname,
            'Key' => 'index.html'
        ]);

        echo "Deleting bucket: $bucketname\n";
        $client->deleteBucket([
            'Bucket' => $bucketname
        ]);
        echo "\n";
        echo "✔ Website Serial Test DONE\n";

    } catch (Exception $e) {
        echo "Unexpected error during website serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
