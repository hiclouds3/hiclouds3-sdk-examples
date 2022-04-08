using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Collections.Specialized;
using System.Configuration;
using System.IO;
using System.Globalization;

using Amazon;
using Amazon.S3;
using Amazon.S3.Model;

namespace TestNetSDK
{
    public class DeleteMultipleObjects
    {
        public static void deleteMultipleObjects(String bucketName, String objectName, String objectName2, String filePath)
        {
            System.Console.WriteLine("\nhello,object!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);

            //PutBucket
            System.Console.WriteLine("PutBucket: {0}\n",bucketName);
            PutBucketResponse response = s3Client.PutBucket(new PutBucketRequest().WithBucketName(bucketName));

            //PutObject 1
             System.Console.WriteLine("PutObject 1 \n");
             PutObjectRequest request = new PutObjectRequest();
             request.WithBucketName(bucketName);
             request.WithKey(objectName);
             request.WithFilePath(filePath);
             PutObjectResponse PutResult = s3Client.PutObject(request);
             System.Console.WriteLine("Uploaded Object Etag: {0}\n", PutResult.ETag);

             //PutObject 2
             System.Console.WriteLine("PutObject 2 \n");
             PutObjectRequest request2 = new PutObjectRequest();
             request2.WithBucketName(bucketName);
             request2.WithKey(objectName2);
             request2.WithFilePath(filePath);
             PutObjectResponse PutResult2 = s3Client.PutObject(request2);
             System.Console.WriteLine("Uploaded Object Etag: {0}\n", PutResult2.ETag);

            //Delete Multiple Objects
            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest();
            multiObjectDeleteRequest.BucketName = bucketName;
            multiObjectDeleteRequest.AddKey(objectName);
            multiObjectDeleteRequest.AddKey(objectName2);
            DeleteObjectsResponse deleteObjectsResponse = s3Client.DeleteObjects(multiObjectDeleteRequest);
            Console.WriteLine("Successfully deleted all the {0} items", deleteObjectsResponse.DeletedObjects.Count);

            //DeleteBucket
            System.Console.WriteLine("Delete Bucket!\n");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
