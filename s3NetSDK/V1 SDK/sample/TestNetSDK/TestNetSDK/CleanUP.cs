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
using Amazon.S3.Util;

namespace TestNetSDK
{
    class CleanUP
    {
        public static void cleanUPSerial(String bucketName)
        {
            System.Console.WriteLine("\nhello, cleanup!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);

            Boolean isExist = AmazonS3Util.DoesS3BucketExist(bucketName, s3Client);
            if (isExist)
            {
                ListVersionsResponse result = s3Client.ListVersions(new ListVersionsRequest().WithBucketName(bucketName));
                if (result != null)
                {
                    foreach (S3ObjectVersion item in result.Versions)
                    {
                        System.Console.WriteLine("bucketName: {0}, objectName: {1}, vid: {2}", item.BucketName, item.Key, item.VersionId);
                        s3Client.DeleteObject(new DeleteObjectRequest().WithBucketName(item.BucketName).WithKey(item.Key).WithVersionId(item.VersionId));
                    }
                }

                //DeleteBucket
                System.Console.WriteLine("Delete Bucket!\n");
                s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            }
            else
            {
                System.Console.WriteLine("bucket does NOT exists or you DON'T have permission to access it");
            }

            System.Console.WriteLine("END!");
        }
        
    }
}
