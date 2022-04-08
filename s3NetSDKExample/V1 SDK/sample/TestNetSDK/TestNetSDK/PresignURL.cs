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
    class PresignURL
    {
        public static void preSignedURLSerial(String bucketName, String objectName)
        {
            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            System.Console.WriteLine("\nhello,Bucket!!");

            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);

            //GeneratePresignedURL - PutObject
            GetPreSignedUrlRequest requestPut = new GetPreSignedUrlRequest();
            requestPut.BucketName = bucketName;
            requestPut.Key = objectName;
            requestPut.Verb = HttpVerb.PUT;
            requestPut.Expires = DateTime.Now.AddMinutes(5);

            String urlPut = s3Client.GetPreSignedURL(requestPut);
            System.Console.WriteLine("PutObject preSignedURL: {0}\n", urlPut);

            //GeneratePresignedURL - GetObject
            GetPreSignedUrlRequest requestGet = new GetPreSignedUrlRequest();
            requestGet.BucketName = bucketName;
            requestGet.Key = objectName;
            requestGet.Verb = HttpVerb.GET;
            requestGet.Expires = DateTime.Now.AddMinutes(5);

            String urlGet = s3Client.GetPreSignedURL(requestGet);
            System.Console.WriteLine("GetObject preSignedURL: {0}\n", urlGet);

            //GeneratePresignedURL - DeleteObject
            GetPreSignedUrlRequest requestDelete = new GetPreSignedUrlRequest();
            requestDelete.BucketName = bucketName;
            requestDelete.Key = objectName;
            requestDelete.Verb = HttpVerb.DELETE;
            requestDelete.Expires = DateTime.Now.AddMinutes(5);

            String urlDelete = s3Client.GetPreSignedURL(requestDelete);
            System.Console.WriteLine("DeleteObject preSignedURL: {0}\n", urlDelete);

            System.Console.WriteLine("END!");
        }
    }
}
