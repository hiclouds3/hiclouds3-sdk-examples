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
    class Policy
    {
        public static void policySerial(String bucketName)
        {
            System.Console.WriteLine("\nhello,Policy!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            /*
             * 若如上自行設定AmazonS3Config，於建立s3Client時需帶入config參數
             * 若無自行設定AmazonS3Config，則可忽略config參數
             */
            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);

            //PutBucket
            System.Console.WriteLine("PutBucket: {0}\n", bucketName);
            PutBucketResponse response = s3Client.PutBucket(new PutBucketRequest().WithBucketName(bucketName));

            //PutBucketPolicy
            System.Console.WriteLine("PutBucketPolicy");
            String policyConfiguration = "{\"Version\": \"2008-10-17\",\"Id\": \"Policy1355909850780\",\"Statement\": [{\"Sid\": \"Stmt1355909849219\",\"Effect\": \"Allow\",\"Principal\": {\"AWS\": \"*\"},\"Action\": \"s3:GetObject\",\"Resource\": \"arn:aws:s3:::" + bucketName + "/*\"}]}";
            PutBucketPolicyResponse putPolicyResult = s3Client.PutBucketPolicy(new PutBucketPolicyRequest().WithBucketName(bucketName).WithPolicy(policyConfiguration));
            System.Console.WriteLine("PutBucketPolicy, requestID: {0}\n", putPolicyResult.RequestId);

            //GetBucketPolicy
            System.Console.WriteLine("GetBucketPolicy >>>");
            GetBucketPolicyResponse getPolicyResult = s3Client.GetBucketPolicy(new GetBucketPolicyRequest().WithBucketName(bucketName));
            System.Console.WriteLine("GetBucketPolicy:\n{0}\n",getPolicyResult.ResponseXml);

            //DeleteBucketPolicy
            System.Console.WriteLine("DeleteBucketPolicy >>>");
            DeleteBucketPolicyResponse deletePolicyResult = s3Client.DeleteBucketPolicy(new DeleteBucketPolicyRequest().WithBucketName(bucketName));
            System.Console.WriteLine("DeleteBucketPolicy, requestID: {0}\n", deletePolicyResult.RequestId);

            //DeleteBucket
            System.Console.WriteLine("Delete Bucket!");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
