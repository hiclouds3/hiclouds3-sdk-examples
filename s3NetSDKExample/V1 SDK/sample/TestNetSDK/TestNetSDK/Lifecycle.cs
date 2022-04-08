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
    class Lifecycle
    {
        public static void lifecycleSerial(String bucketName)
        {
            System.Console.WriteLine("\nhello,Lifecyle!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);

            //PutBucket
            System.Console.WriteLine("PutBucket: {0}\n", bucketName);
            s3Client.PutBucket(new PutBucketRequest().WithBucketName(bucketName));

            //PutBucketLifecycle
            System.Console.WriteLine("PutBucketLicycle");
            //rule 1
            LifecycleRule rule1 = new LifecycleRule();           
            LifecycleRuleExpiration days =  new LifecycleRuleExpiration();
            days.Days=5;

            rule1.Id="test";
            rule1.Prefix="pre";
            rule1.Status= LifecycleRuleStatus.Enabled;
            rule1.Expiration = days;

            //rule 2
            LifecycleRule rule2 = new LifecycleRule();
            LifecycleRuleExpiration second_days = new LifecycleRuleExpiration { Date = DateTime.Now.AddYears(5).AddMonths(3).AddDays(5)};
            rule2.Id = "test_log";
            rule2.Prefix = "log";
            rule2.Status = LifecycleRuleStatus.Enabled;
            rule2.Expiration = second_days;

            PutLifecycleConfigurationRequest request = new PutLifecycleConfigurationRequest();
            LifecycleConfiguration configuration = new LifecycleConfiguration();
            List<LifecycleRule> list = new List<LifecycleRule>();
            list.Add(rule1); //add rule 1
            list.Add(rule2); //add rule 2
            configuration.Rules = list;

            request.WithBucketName(bucketName);
            request.WithConfiguration(configuration);
            PutLifecycleConfigurationResponse putResult = s3Client.PutLifecycleConfiguration(request);
            System.Console.WriteLine("PutBucketLifecycle, requestID: {0}\n", putResult.RequestId);

            //GetBucketLifecycle
            GetLifecycleConfigurationResponse getResult = s3Client.GetLifecycleConfiguration(new GetLifecycleConfigurationRequest().WithBucketName(bucketName));
            System.Console.WriteLine("GetBucketLifecycle result:\n {0}\n", getResult.ResponseXml);
            System.Console.WriteLine("How many rules:\n {0}\n", getResult.Configuration.Rules.Count);
            
            //DeleteBucketLifecycle
            DeleteLifecycleConfigurationResponse deleteResult = s3Client.DeleteLifecycleConfiguration(new DeleteLifecycleConfigurationRequest().WithBucketName(bucketName));
            System.Console.WriteLine("DeleteBucketLifecycle, requestID: {0}\n", deleteResult.RequestId);

            //DeleteBucket
            System.Console.WriteLine("Delete Bucket!");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
