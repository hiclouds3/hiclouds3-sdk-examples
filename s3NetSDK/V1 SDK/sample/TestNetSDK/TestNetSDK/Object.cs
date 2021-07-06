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
    public class Object
    {
        public static void objectSerial(String bucketName, String objectName, String filePath)
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

            //PutObject
            System.Console.WriteLine("PutObject!\n");
            PutObjectRequest request = new PutObjectRequest();
            request.WithBucketName(bucketName);
            request.WithKey(objectName);
            request.WithFilePath(filePath);
            PutObjectResponse PutResult = s3Client.PutObject(request);
            System.Console.WriteLine("Uploaded Object Etag: {0}\n", PutResult.ETag);

            //HeadObject
            System.Console.WriteLine("HeadObject!\n");
            GetObjectMetadataResponse HeadResult = s3Client.GetObjectMetadata(new GetObjectMetadataRequest().WithBucketName(bucketName).WithKey(objectName));
            System.Console.WriteLine("HeadObject: (1)ContentLength: {0} (2)ETag: {1}\n", HeadResult.ContentLength,HeadResult.ETag);

            //GetObject
            System.Console.WriteLine("GetObject!\n");
            GetObjectRequest getObjectRequest = new GetObjectRequest();
            getObjectRequest.WithBucketName(bucketName);
            getObjectRequest.WithKey(objectName);
            getObjectRequest.WithByteRange(1, 15);
            GetObjectResponse GetResult = s3Client.GetObject(getObjectRequest); 
            Stream responseStream = GetResult.ResponseStream;
            StreamReader reader = new StreamReader(responseStream);
            System.Console.WriteLine("Get Object Content:\n {0}\n",reader.ReadToEnd());
            System.Console.WriteLine("Get Object ETag:\n {0}\n", GetResult.ETag);

            //CopyObject
            CopyObjectResponse CopyResult = s3Client.CopyObject(new CopyObjectRequest().WithSourceBucket(bucketName).WithSourceKey(objectName).WithDestinationBucket(bucketName).WithDestinationKey("hihi"));
            System.Console.WriteLine("CopyObject: ETag: {0} \n",CopyResult.ETag);

            //DeleteObject
            System.Console.WriteLine("Delete Object!\n");
            s3Client.DeleteObject(new DeleteObjectRequest().WithBucketName(bucketName).WithKey(objectName));
            s3Client.DeleteObject(new DeleteObjectRequest().WithBucketName(bucketName).WithKey("hihi")); //copied object

            //DeleteBucket
            System.Console.WriteLine("Delete Bucket!\n");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
