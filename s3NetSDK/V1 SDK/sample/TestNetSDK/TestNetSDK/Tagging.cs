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
    class Tagging
    {
        public static void taggingSerial(String bucketName) {
            System.Console.WriteLine("\nhello,Tagging!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);

            //PutBucket
            System.Console.WriteLine("PutBucket: {0}\n", bucketName);
            s3Client.PutBucket(new PutBucketRequest().WithBucketName(bucketName));

            //PutBucketTagging
            System.Console.WriteLine("PutBucketTagging");

            Tag tag1 = new Tag();
            tag1.WithKey("Jan");
            tag1.WithValue("good");
            Tag tag2 = new Tag();
            tag2.WithKey("Feb");
            tag2.WithValue("ok");

            Tag[] Tags = new Tag[]{tag1,tag2};

            TagSet tagSets = new TagSet();
            tagSets.WithTags(Tags);
            PutBucketTaggingResponse putResult = s3Client.PutBucketTagging(new PutBucketTaggingRequest().WithBucketName(bucketName).WithTagSets(tagSets));
            System.Console.WriteLine("PutBucketTagging, requestID: {0}\n", putResult.RequestId);

            //GetBucketTagging
            GetBucketTaggingResponse getResult = s3Client.GetBucketTagging(new GetBucketTaggingRequest().WithBucketName(bucketName));
            System.Console.WriteLine("GetBucketTagging result:\n {0}\n", getResult.ResponseXml);
            System.Console.WriteLine("How many rules:\n {0}\n", getResult.TagSets.Count);

            //DeleteBucketTagging
            DeleteBucketTaggingResponse deleteBucketTaggingResult = s3Client.DeleteBucketTagging(new DeleteBucketTaggingRequest().WithBucketName(bucketName));
            System.Console.WriteLine("\nDeleteBucketTagging, requestID:{0}", deleteBucketTaggingResult.RequestId);

            //DeleteBucket
            System.Console.WriteLine("Delete Bucket!");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
