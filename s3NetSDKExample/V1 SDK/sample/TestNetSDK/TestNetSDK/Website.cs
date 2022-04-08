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
    class Website
    {
        public static void WebsiteSerial(String bucketName)
        {
            System.Console.WriteLine("\nhello,Website!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config connectionConfig = new AmazonS3Config();
            connectionConfig.CommunicationProtocol = Protocol.HTTP;
            connectionConfig.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], connectionConfig);

            //PutBucket
            PutBucketResponse response = s3Client.PutBucket(new PutBucketRequest().WithBucketName(bucketName));

            //PutBucketWebsite
            WebsiteConfiguration config = new WebsiteConfiguration();
            config.WithErrorDocument("404Test.html");
            config.WithIndexDocumentSuffix("indexTest.html");
            PutBucketWebsiteResponse putBucketWebsiteResult = s3Client.PutBucketWebsite(new PutBucketWebsiteRequest().WithBucketName(bucketName).WithWebsiteConfiguration(config));
            System.Console.WriteLine("\nPutBucketWebsite, requestID:{0}",putBucketWebsiteResult.RequestId);

            //GetBucketWebsite
            GetBucketWebsiteResponse getBucketWebsiteResult = s3Client.GetBucketWebsite(new GetBucketWebsiteRequest().WithBucketName(bucketName));
            System.Console.WriteLine("\nGetBucketWebsite Result:\n{0}",getBucketWebsiteResult.ResponseXml);

            //DeleteBucketWebsite
            DeleteBucketWebsiteResponse deleteBucketWebsiteResult = s3Client.DeleteBucketWebsite(new DeleteBucketWebsiteRequest().WithBucketName(bucketName));
            System.Console.WriteLine("\nDeleteBucketWebsite, requestID:{0}",deleteBucketWebsiteResult.RequestId);

            //DeleteBucket
            System.Console.WriteLine("Delete Bucket!");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
