using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Collections.Specialized;
using System.Configuration;

using Amazon;
using Amazon.S3;
using Amazon.S3.Model;
namespace TestNetSDK
{
    class Test
    {
        public static void deleteObject(String bucketName, String objectName) {
            System.Console.WriteLine("\nhello,object!!");

            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);
            System.Console.WriteLine("key:\n {0}\n", appConfig["AWSAccessKey"]);

            //DeleteObject
            System.Console.WriteLine("Delete Object!\n");
            s3Client.DeleteObject(new DeleteObjectRequest().WithBucketName(bucketName).WithKey(objectName));
            //s3Client.DeleteObject(new DeleteObjectRequest().WithBucketName(bucketName).WithKey("hihi")); //copied object
        }

        public static void putObject(String bucketName, String objectName, String filePath)
        {
            System.Console.WriteLine("\nhello,object!!");

            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);
            System.Console.WriteLine("key:\n {0}\n", appConfig["AWSAccessKey"]);

            System.Console.WriteLine("PutObject!\n");
            PutObjectRequest request = new PutObjectRequest();
            request.WithBucketName(bucketName);
            request.WithKey(objectName);
            request.WithFilePath(filePath);
            PutObjectResponse PutResult = s3Client.PutObject(request);
            System.Console.WriteLine("Uploaded Object Etag: {0}\n", PutResult.ETag);
        }

        public static void listBucket(String bucketName)
        {
            System.Console.WriteLine("\nhello,Bucket!!");

            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);
            System.Console.WriteLine("key:\n {0}\n", appConfig["AWSAccessKey"]);

            /*
            * 列出Bucket中所有P的Object
            */
            ListObjectsResponse objects = s3Client.ListObjects(new ListObjectsRequest().WithBucketName(bucketName));
            System.Console.WriteLine("Get Bucket Result:\n {0}\n", objects.ResponseXml);

            System.Console.WriteLine("END!");
        }
    }
}
