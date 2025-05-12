<?php
require_once 'client.php';

function bucketCorsSerialTest($bucketname)
{
    global $client;

    echo "> Bucket CORS Serial Testing....\n";
    try {
        // Step 1: Create Bucket
        echo "Creating bucket $bucketname...\n";
        $client->createBucket(['Bucket' => $bucketname]);
        echo "Bucket $bucketname created successfully.\n";


        // Step 2: Set Bucket CORS
        echo "Setting CORS configuration for bucket $bucketname...\n";
        $client->putBucketCors([
            'Bucket' => $bucketname,
            'CORSConfiguration' => [
                'CORSRules' => [
                    [
                        'AllowedHeaders' => ['content-type', 'content-encoding'],
                        'AllowedMethods' => ['POST', 'PUT'],
                        'AllowedOrigins' => ['http://tw.yahoo.com.tw'],
                        'ExposeHeaders' => ['x-amz-*'],
                        'MaxAgeSeconds' => 100,
                    ],
                    [
                        'AllowedHeaders' => ['connection', 'content-encoding'],
                        'AllowedMethods' => ['DELETE', 'HEAD'],
                        'AllowedOrigins' => ['http://hello.world'],
                        'ExposeHeaders' => ['request-id'],
                        'MaxAgeSeconds' => 10,
                    ]
                ]
            ]
        ]);
        echo "CORS configuration set successfully for bucket $bucketname.\n";

        // Step 3: Get Bucket CORS
        echo "Retrieving CORS configuration for bucket $bucketname...\n";

        $result = $client->getBucketCors(['Bucket' => $bucketname]);
        echo "Listing CORS Rules:\n";
        foreach ($result['CORSRules'] as $rule) {
            echo "--------------------------\n";
            echo "[AllowedHeaders] " . implode(", ", $rule['AllowedHeaders']) . "\n";
            echo "[AllowedMethods] " . implode(", ", $rule['AllowedMethods']) . "\n";
            echo "[AllowedOrigins] " . implode(", ", $rule['AllowedOrigins']) . "\n";
            echo "[ExposeHeaders] " . implode(", ", $rule['ExposeHeaders']) . "\n";
            echo "[MaxAgeSeconds] " . $rule['MaxAgeSeconds'] . "\n";
        }

        // Step 4: Delete Bucket CORS
        echo "Deleting CORS configuration for bucket $bucketname...\n";
        $client->deleteBucketCors(['Bucket' => $bucketname]);
        echo "CORS configuration deleted successfully for bucket $bucketname.\n";

        // Step 5: Delete Bucket
        echo "Deleting bucket $bucketname...\n";

        $client->deleteBucket(['Bucket' => $bucketname]);
        echo "Bucket $bucketname deleted successfully.\n";

        echo "\n";
        echo "✔ BucketCors Serial Test DONE\n";
    } catch (Exception $e) {
        echo "Unexpected error during bucket cors serial test for $bucketname: " . $e->getMessage() . "\n";
    }
}
