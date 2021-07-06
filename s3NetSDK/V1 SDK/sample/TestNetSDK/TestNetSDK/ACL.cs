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
    class ACL
    {
        public static void ACLSerial(String bucketName, String objectName, String filePath)
        {
            System.Console.WriteLine("\nhello,ACL!!");
            NameValueCollection appConfig = ConfigurationManager.AppSettings;

            AmazonS3Config config = new AmazonS3Config();
            config.CommunicationProtocol = Protocol.HTTP;
            config.ServiceURL = "s3.hicloud.net.tw";

            AmazonS3 s3Client = AWSClientFactory.CreateAmazonS3Client(appConfig["AWSAccessKey"], appConfig["AWSSecretKey"], config);

            //PutBucket
            System.Console.WriteLine("PutBucket: {0}\n", bucketName);
            s3Client.PutBucket(new PutBucketRequest().WithBucketName(bucketName));

            //PutObject
            System.Console.WriteLine("PutObject!");
            PutObjectRequest request = new PutObjectRequest();
            request.WithBucketName(bucketName);
            request.WithKey(objectName);
            request.WithFilePath(filePath);
            PutObjectResponse PutResult = s3Client.PutObject(request);
            System.Console.WriteLine("Uploaded Object Etag: {0}\n", PutResult.ETag);

            //PutBucketACL
            SetACLRequest aclRequest = new SetACLRequest();
            S3AccessControlList aclConfig = new S3AccessControlList();
            aclConfig.WithOwner(new Owner().WithDisplayName(appConfig["ownerDisplayName"]).WithId(appConfig["ownerCanonicalID"]));
            aclConfig.WithGrants(new S3Grant().WithGrantee(new S3Grantee().WithCanonicalUser(appConfig["userACanonicalID"], appConfig["userADisplayName"])).WithPermission(S3Permission.FULL_CONTROL));
            aclConfig.WithGrants(new S3Grant().WithGrantee(new S3Grantee().WithCanonicalUser(appConfig["userBCanonicalID"], appConfig["userBDisplayName"])).WithPermission(S3Permission.READ_ACP));

            aclRequest.WithBucketName(bucketName);
            aclRequest.WithACL(aclConfig);

            SetACLResponse putBucketACLResult = s3Client.SetACL(aclRequest);
            System.Console.WriteLine("\nPutBucketACL, requestID:{0}",putBucketACLResult.RequestId);

            //GetBucketACL
            GetACLResponse getBucketACLResult = s3Client.GetACL(new GetACLRequest().WithBucketName(bucketName));
            System.Console.WriteLine("\nGetBucketACL Result:\n{0}\n",getBucketACLResult.ResponseXml);

            //PutObjectACL
            SetACLRequest objectACLRequest = new SetACLRequest();
            S3AccessControlList objectACLConfig = new S3AccessControlList();
            objectACLConfig.WithOwner(new Owner().WithDisplayName(appConfig["ownerDisplayName"]).WithId(appConfig["ownerCanonicalID"]));
            objectACLConfig.WithGrants(new S3Grant().WithGrantee(new S3Grantee().WithCanonicalUser(appConfig["userACanonicalID"], appConfig["userADisplayName"])).WithPermission(S3Permission.FULL_CONTROL));
            objectACLConfig.WithGrants(new S3Grant().WithGrantee(new S3Grantee().WithCanonicalUser(appConfig["userBCanonicalID"], appConfig["userBDisplayName"])).WithPermission(S3Permission.WRITE_ACP));

            objectACLRequest.WithBucketName(bucketName);
            objectACLRequest.WithKey(objectName);
            objectACLRequest.WithACL(objectACLConfig);

            SetACLResponse putObjectACLResult = s3Client.SetACL(objectACLRequest);
            System.Console.WriteLine("\nPutObjectACL, requestID:{0}", putObjectACLResult.RequestId);

            //GetObjectACl
            GetACLResponse getObjectACLResult = s3Client.GetACL(new GetACLRequest().WithBucketName(bucketName).WithKey(objectName));
            System.Console.WriteLine("\nGetObjectACL Result:\n{0}\n", getObjectACLResult.ResponseXml);

            //for tead down
            SetACLRequest aclRequest1 = new SetACLRequest(); 
            S3AccessControlList aclConfig1 = new S3AccessControlList();
            aclConfig1.WithOwner(new Owner().WithDisplayName(appConfig["ownerDisplayName"]).WithId(appConfig["ownerCanonicalID"]));
            aclConfig1.WithGrants(new S3Grant().WithGrantee(new S3Grantee().WithCanonicalUser(appConfig["userBCanonicalID"], appConfig["userBDisplayName"])).WithPermission(S3Permission.FULL_CONTROL));
            aclConfig1.WithGrants(new S3Grant().WithGrantee(new S3Grantee().WithCanonicalUser(appConfig["ownerCanonicalID"], appConfig["ownerBDisplayName"])).WithPermission(S3Permission.FULL_CONTROL)); //Have to be User's ID to tear down
            aclRequest1.WithBucketName(bucketName);
            aclRequest1.WithACL(aclConfig1);
            SetACLResponse putBucketACLResult1 = s3Client.SetACL(aclRequest1);
            System.Console.WriteLine("\nPutBucketACL, requestID:{0}", putBucketACLResult1.RequestId);

            GetACLResponse getBucketACLResult1 = s3Client.GetACL(new GetACLRequest().WithBucketName(bucketName));
            System.Console.WriteLine("\nGetBucketACL Result:\n{0}\n", getBucketACLResult1.ResponseXml);
            
            //DeleteObject
            System.Console.WriteLine("Delete Object!");
            s3Client.DeleteObject(new DeleteObjectRequest().WithBucketName(bucketName).WithKey(objectName));
            //DeleteBucket
            System.Console.WriteLine("Delete Bucket!");
            s3Client.DeleteBucket(new DeleteBucketRequest().WithBucketName(bucketName));
            System.Console.WriteLine("END!");
        }
    }
}
