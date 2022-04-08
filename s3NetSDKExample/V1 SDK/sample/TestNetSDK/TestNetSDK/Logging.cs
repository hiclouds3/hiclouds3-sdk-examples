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
    class Logging
    {
        public static void loggingSerial(String bucketName, String logBucketName)
        {
            System.Console.WriteLine("\nhello,Logging!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config connectionConfig = new AmazonS3Config();
            connectionConfig.CommunicationProtocol = Protocol.HTTP;
            connectionConfig.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], connectionConfig);

            //PutBucket
            System.Console.WriteLine("PutBucket: {0} + {1}\n", bucketName,logBucketName);
            s3Client.PutBucket(new PutBucketRequest().WithBucketName(bucketName));
            s3Client.PutBucket(new PutBucketRequest().WithBucketName(logBucketName));

            //PutBucketACL for logDelivery user
            SetACLRequest aclRequest = new SetACLRequest();
            aclRequest.WithBucketName(logBucketName);
            aclRequest.WithCannedACL(S3CannedACL.LogDeliveryWrite);
            SetACLResponse setACLResult = s3Client.SetACL(aclRequest);
            System.Console.WriteLine("SetBucketACL, requestID:{0}\n",setACLResult.RequestId);

            //PutBucketLogging
            S3BucketLoggingConfig config = new S3BucketLoggingConfig();
            config.WithTargetBucketName(logBucketName);
            config.WithTargetPrefix("log-prefix");
            EnableBucketLoggingResponse setLoggingResult = s3Client.EnableBucketLogging(new EnableBucketLoggingRequest().WithBucketName(bucketName).WithLoggingConfig(config));
            System.Console.WriteLine("SetBucketLogging, requestID:{0}\n", setLoggingResult.RequestId);

            //GetBucketLogging
            GetBucketLoggingResponse getLoggingResult = s3Client.GetBucketLogging(new GetBucketLoggingRequest().WithBucketName(bucketName));
            System.Console.WriteLine("GetBucketLogging:\n {0}\n", getLoggingResult.ResponseXml);

            //DeleteBucket
            System.Console.WriteLine("Delete Bucket!");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(logBucketName));
            System.Console.WriteLine("END!");
        }
    }
}
