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
    public class Cors
    {
        public static void corsSerial(String bucketName)
        {
            System.Console.WriteLine("\nhello,Cors!!");
            /*
            * 重要：在運行此範例程式前，請務必確認已將 hicloud S3 Access Key 與 Secret Key 填入App.config檔案中
            */
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            /* 若使用 Amazon S3 官方 library 請加上下列程式：
            *  AmazonS3Config config = new AmazonS3Config();
            *  config.CommunicationProtocol = Protocol.HTTP; //如有需要調整
			*  config.ServiceURL = "s3.hicloud.net.tw"; //設定hostname
            */
            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            /*
             * 若如上自行設定AmazonS3Config，於建立s3Client時需帶入config參數
             * 若無自行設定AmazonS3Config，則可忽略config參數
             */
            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);
            System.Console.WriteLine("key:\n {0}\n", appConfig["AWSAccessKey"]);

            /*
            * 創建Bucket - Bucket 名稱必須是唯一，若 Bucket 名稱已被其他使用者所使用時，將無法成功建立相同名稱的Bucket
            */
            PutBucketResponse response = s3Client.PutBucket(new PutBucketRequest().WithBucketName(bucketName));

            /*
            * 設定 Bucket CORS Rule
            */
            List<CORSRule> rules = new List<CORSRule>();
            CORSRule ruleOne = new CORSRule();
            ruleOne.WithId("Rule One");
            ruleOne.WithAllowedOrigins("http://google.com");
            ruleOne.WithAllowedMethods("GET");
            ruleOne.WithAllowedHeaders("content-type");
            ruleOne.WithExposeHeaders("content-encoding");
            ruleOne.WithMaxAgeSeconds(100);
            CORSRule ruleTwo = new CORSRule();
            ruleTwo.WithId("Rule Two");
            ruleTwo.WithAllowedOrigins("tw.yahoo.com");
            ruleTwo.WithAllowedMethods("PUT");
            ruleTwo.WithAllowedHeaders("accept-language");
            ruleTwo.WithExposeHeaders("content-length");
            ruleTwo.WithMaxAgeSeconds(200);
            rules.Add(ruleOne);
            rules.Add(ruleTwo);

            CORSConfiguration conf = new CORSConfiguration();
            conf.Rules = rules;

            PutCORSConfigurationRequest request = new PutCORSConfigurationRequest();
            request.WithBucketName(bucketName);
            request.WithConfiguration(conf);
            PutCORSConfigurationResponse putBucketCorsResponse = s3Client.PutCORSConfiguration(request);
            System.Console.WriteLine("\nPutBucketCors, requestID:{0}", putBucketCorsResponse.RequestId);

            /*
            * 取回 Bucket CORS Rule 設定
            */
            GetCORSConfigurationResponse getBucketCorsResponse = s3Client.GetCORSConfiguration(new GetCORSConfigurationRequest().WithBucketName(bucketName));
            System.Console.WriteLine("\nGetBucketCORS:\n{0}", getBucketCorsResponse.ResponseXml);

            /*
             * 刪除 Bucket CORS Rule 設定
             */
            DeleteCORSConfigurationResponse deleteBucketCorsResponse = s3Client.DeleteCORSConfiguration(new DeleteCORSConfigurationRequest().WithBucketName(bucketName));
            System.Console.WriteLine("\nDeleteBucketCors, requestID:{0}",deleteBucketCorsResponse.RequestId);

            /*
            * 刪除Bucket - 欲刪除Bucket，此Bucket必須是已清空的，因此欲刪除Bucket前請先確認Bucket中是否仍存在任何Object
            */
            System.Console.WriteLine("Delete Bucket!");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
