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
    class MPU
    {
        public static void mpuSerial(String bucketName, String objectNameA, String objectNameB, String fPath)
        {
            System.Console.WriteLine("\nhello,MPU!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);

            //PutBucket
            PutBucketResponse response = s3Client.PutBucket(new PutBucketRequest().WithBucketName(bucketName));

            //Initial MPU * 2
            InitiateMultipartUploadResponse InitialResultA = s3Client.InitiateMultipartUpload(new InitiateMultipartUploadRequest().WithBucketName(bucketName).WithKey(objectNameA));
            InitiateMultipartUploadResponse InitialResultB = s3Client.InitiateMultipartUpload(new InitiateMultipartUploadRequest().WithBucketName(bucketName).WithKey(objectNameB));
            String objectAUID = InitialResultA.UploadId;
            String objectBUID = InitialResultB.UploadId;
            System.Console.WriteLine("\nInitial MPU:{0},uploadID:{1}",objectNameA,objectAUID);
            System.Console.WriteLine("Initial MPU:{0},uploadID:{1}", objectNameB, objectBUID);

            //Upload Part * 5 (To make a 5GB file for copy part)
            //Part Size - 5 MB to 5 GB，last part can be < 5 MB
            UploadPartResponse part1Result = s3Client.UploadPart(new UploadPartRequest().WithBucketName(bucketName).WithKey(objectNameA).WithPartNumber(1).WithUploadId(objectAUID).WithFilePath(fPath).WithFilePosition(0).WithTimeout(-1));
            UploadPartResponse part2Result = s3Client.UploadPart(new UploadPartRequest().WithBucketName(bucketName).WithKey(objectNameA).WithPartNumber(2).WithUploadId(objectAUID).WithFilePath(fPath).WithFilePosition(0).WithTimeout(-1));
            UploadPartResponse part3Result = s3Client.UploadPart(new UploadPartRequest().WithBucketName(bucketName).WithKey(objectNameA).WithPartNumber(3).WithUploadId(objectAUID).WithFilePath(fPath).WithFilePosition(0).WithTimeout(-1));
            UploadPartResponse part4Result = s3Client.UploadPart(new UploadPartRequest().WithBucketName(bucketName).WithKey(objectNameA).WithPartNumber(4).WithUploadId(objectAUID).WithFilePath(fPath).WithFilePosition(0).WithTimeout(-1));
            UploadPartResponse part5Result = s3Client.UploadPart(new UploadPartRequest().WithBucketName(bucketName).WithKey(objectNameA).WithPartNumber(5).WithUploadId(objectAUID).WithFilePath(fPath).WithFilePosition(0).WithTimeout(-1));

            System.Console.WriteLine("\nUpload Part Result: part 1 requestID:{0} & part 2 requestID:{1} & part 3 requestID:{2} & part 4 requestID:{3} & part 5 requestID:{4}", part1Result.RequestId, part2Result.RequestId, part3Result.RequestId, part4Result.RequestId, part5Result.RequestId);

            //List MPUs
            ListMultipartUploadsResponse listMPUsResult = s3Client.ListMultipartUploads(new ListMultipartUploadsRequest().WithBucketName(bucketName));
            System.Console.WriteLine("\nList MPUs Result:\n{0}", listMPUsResult.ResponseXml);

            //Complete MPU
            List<UploadPartResponse> uploadResponses = new List<UploadPartResponse>();
            uploadResponses.Add(part1Result);
            uploadResponses.Add(part2Result);
            uploadResponses.Add(part3Result);
            uploadResponses.Add(part4Result);
            uploadResponses.Add(part5Result);
            CompleteMultipartUploadResponse completeResult = s3Client.CompleteMultipartUpload(new CompleteMultipartUploadRequest().WithBucketName(bucketName).WithKey(objectNameA).WithUploadId(objectAUID).WithPartETags(uploadResponses));
            System.Console.WriteLine("\nComplete MPUs Result:\n{0}", completeResult.ResponseXml);

            //Upload Part Copy
            CopyPartRequest copyRequest = new CopyPartRequest{
                DestinationBucket = bucketName,
                SourceBucket = bucketName,
                SourceKey = objectNameA,
                DestinationKey = objectNameB,
                PartNumber = 1,
                UploadID = objectBUID
            };
            CopyPartResponse copyPartResult = s3Client.CopyPart(copyRequest);
            System.Console.WriteLine("\nCopy Part Result: requestID:{0}", copyPartResult.RequestId);

            //List Upload Parts
            ListPartsResponse listPartsResult = s3Client.ListParts(new ListPartsRequest().WithBucketName(bucketName).WithKey(objectNameB).WithUploadId(objectBUID));
            System.Console.WriteLine("\nListParts Result:\n{0}", listPartsResult.ResponseXml);

            //Abort MPU
            AbortMultipartUploadResponse abortResult = s3Client.AbortMultipartUpload(new AbortMultipartUploadRequest().WithBucketName(bucketName).WithKey(objectNameB).WithUploadId(objectBUID));
            System.Console.WriteLine("\nAbortMPU Result, requestID:{0}",abortResult.RequestId);

            //DeleteObject
            System.Console.WriteLine("\nDelete Object!\n");
            s3Client.DeleteObject(new DeleteObjectRequest().WithBucketName(bucketName).WithKey(objectNameA));
             //DeleteBucket
            System.Console.WriteLine("Delete Bucket!");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
