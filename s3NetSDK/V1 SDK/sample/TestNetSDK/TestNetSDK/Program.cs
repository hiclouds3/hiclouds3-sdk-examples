using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Collections.Specialized;
using System.Configuration;
using System.Threading;

using Amazon;
using Amazon.S3;
using Amazon.S3.Model;

namespace TestNetSDK
{
    class Program
    {
        static void Main(string[] args)
        {
            System.Console.WriteLine("Hello, World!");
            String bucketNameOne = "bucketone";
            String bucketNameTwo = "buckettwo";
            String object1 = "object1";
            String object2 = "object2";
            String object3 = "object3";
            String oriFilePath = "../../pic.jpg";  //For MPU test, file size need to be larger than 5MB
            String newFilePath = "new.jpg";

            System.Console.WriteLine(AppDomain.CurrentDomain.BaseDirectory);

            //Clean up
            CleanUP.cleanUPSerial(bucketNameOne);
            CleanUP.cleanUPSerial(bucketNameTwo);

            Bucket.bucketSerial(bucketNameOne);

            Object.objectSerial(bucketNameOne, object1, oriFilePath); 

            Policy.policySerial(bucketNameOne);

            Logging.loggingSerial(bucketNameOne, bucketNameTwo);

            Versioning.VersioningSerial(bucketNameOne);

            ACL.ACLSerial(bucketNameOne, object1, oriFilePath);

            Website.WebsiteSerial(bucketNameOne);

            MPU.mpuSerial(bucketNameOne, object1, object2, oriFilePath);

            Lifecycle.lifecycleSerial(bucketNameOne);

            DeleteMultipleObjects.deleteMultipleObjects(bucketNameOne, object1, object2, oriFilePath);

            PresignURL.preSignedURLSerial(bucketNameOne, object1);

            headBucket.doesS3BucketExist(bucketNameOne);

            Thread.Sleep(2000);

            Cors.corsSerial(bucketNameOne);

            Tagging.taggingSerial(bucketNameOne);

            ServerSideEncryption.SSE(bucketNameOne, object1, oriFilePath);
            
            Console.ReadLine();
        }
    }
}
