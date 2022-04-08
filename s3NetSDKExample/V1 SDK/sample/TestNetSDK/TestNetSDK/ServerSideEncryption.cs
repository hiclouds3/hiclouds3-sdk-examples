using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Collections.Specialized;
using System.Configuration;
using System.IO;

using Amazon;
using Amazon.S3;
using Amazon.S3.Model;

namespace TestNetSDK
{
    class ServerSideEncryption
    {
        public static void SSE(String bucketName, String objectName, String filePath) {
            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            System.Console.WriteLine("\nhello,SSE!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

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
            request.WithServerSideEncryptionMethod(ServerSideEncryptionMethod.AES256);
            PutObjectResponse PutResult = s3Client.PutObject(request);
            System.Console.WriteLine("Uploaded Object Etag: {0}\n", PutResult.ETag);

            //GetObject
            System.Console.WriteLine("GetObject!\n");
            GetObjectRequest requestGet = new GetObjectRequest();
            requestGet.WithBucketName(bucketName);
            requestGet.WithKey(objectName);
            GetObjectResponse GetResult = s3Client.GetObject(requestGet); 
            //Optional to print object content out
            //Stream responseStream = GetResult.ResponseStream;
            //StreamReader reader = new StreamReader(responseStream);
            //System.Console.WriteLine("Get Object Content:\n {0}\n",reader.ReadToEnd());
            System.Console.WriteLine("Get Object SSE:\n {0}\n", GetResult.ServerSideEncryptionMethod);

            //DeleteObject
            System.Console.WriteLine("Delete Object!\n");
            s3Client.DeleteObject(new DeleteObjectRequest().WithBucketName(bucketName).WithKey(objectName));
            //DeleteBucket
            System.Console.WriteLine("Delete Bucket!\n");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
