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
using Amazon.S3.Util;

namespace TestNetSDK
{
    class headBucket
    {
        public static void doesS3BucketExist(String bucketName)
        {
            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            System.Console.WriteLine("\nhello,Bucket!!");
            
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"],config);

            //HeadBucket
            Boolean result = AmazonS3Util.DoesS3BucketExist(bucketName, s3Client);
            if (result)
            {
                System.Console.WriteLine("bucket exists and you have permission to access it");
            }
            else {
                System.Console.WriteLine("bucket does NOT exists or you DON'T have permission to access it");
            }

            System.Console.WriteLine("END!");
        }
    }
}
