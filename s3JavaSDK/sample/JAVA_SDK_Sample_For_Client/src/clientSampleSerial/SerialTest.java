package clientSampleSerial;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;

import clientSampleSerial.BucketSerialTesting;
import clientSampleSerial.ObjectSerialTesting;
import clientSampleSerial.BucketLoggingSerialTesting;
import clientSampleSerial.ClientSideEncryption;
import clientSampleSerial.DeleteMultipleObject;
import clientSampleSerial.HeadBucket;
import clientSampleSerial.LifecycleSerial;
import clientSampleSerial.MPUSerialTesting;
import clientSampleSerial.PolicySerialTesting;
import clientSampleSerial.PreSignedURL;
import clientSampleSerial.PutObjectWithMD5;
import clientSampleSerial.ServerSideEncryption;
import clientSampleSerial.UploadBigFileByMPU;
import clientSampleSerial.VersioningSerialTesting;
import clientSampleSerial.WebsiteSerialTesting;


public class SerialTest {

  private static void expectException(int expect, AmazonServiceException ase) {
    if (expect != ase.getStatusCode()) {
      System.out.println("Caught an AmazonServiceException, which means your request made it "
          + "to Amazon S3, but was rejected with an error response for some reason.(Expect:"
          + expect + ",but get" + ase.getStatusCode() + ")");
      System.out.println("Error Message:    " + ase.getMessage());
      System.out.println("HTTP Status Code: " + ase.getStatusCode());
      System.out.println("AWS Error Code:   " + ase.getErrorCode());
      System.out.println("Error Type:       " + ase.getErrorType());
      System.out.println("Request ID:       " + ase.getRequestId());
    }
  }

  private static void teardownPro(String bucketName) throws IOException {

    AmazonS3 s3 = S3Client.getClient();

    System.out.println("---Teardown---");
    try {
      // GetBucketVersions & Delete it
      VersionListing result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName));
      for (S3VersionSummary s : result.getVersionSummaries()) {
        s3.deleteVersion(s.getBucketName(), s.getKey(), s.getVersionId());
      }

      // DeleteBucket
      s3.deleteBucket(bucketName);
    } catch (AmazonServiceException ase) {
      expectException(404, ase);
    } catch (AmazonClientException ace) {
      System.out.println("Caught an AmazonClientException, which means the client encountered "
          + "a serious internal problem while trying to communicate with S3, "
          + "such as not being able to access the network.");
      System.out.println("Error Message: " + ace.getMessage());
    }
    System.out.println("---Teardown END---");
  }

  public static void main(String args[])
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    BucketSerialTesting Bucket = null;
    ObjectSerialTesting Object = null;
    ACLSerialTesting ACL = null;
    BucketLoggingSerialTesting BucketLogging = null;
    ClientSideEncryption CSE = null;
    DeleteMultipleObject deleteMultipleObject = null;
    HeadBucket headBucket = null;
    LifecycleSerial lifeCycle = null;
    MPUSerialTesting mpu = null;
    PolicySerialTesting policy = null;
    PreSignedURL preSignURL = null;
    PutObjectWithMD5 putObjectMD5 = null;
    ServerSideEncryption sse = null;
    UploadBigFileByMPU mpuBigFile = null;
    VersioningSerialTesting versioning = null;
    WebsiteSerialTesting website = null;
    BucketCorsSerialTesting cors = null;
    BucketTaggingSerialTest tagging = null;

    String bucketName1 = "jeffbucketonejava";
    String bucketName2 = "jeffbuckettwojava";
    String objectName1 = "jeffobjectonejava";
    String objectName2 = "jeffobjecttwojava";
    String objectName3 = "jeffobjectthreejava";
    String oriFilePath =
        (SerialTest.class.getResource("5M_File.jpg").getFile()).replaceAll("%20", " ");
    String newFilePath =
        (SerialTest.class.getResource("/").getFile() + "new_File.jpg").replaceAll("%20", " ");
    String sbucketName = "jeffsourcejava";
    String dbucketName = "jefftargetjava";


    String args1[] =
        {bucketName1, bucketName2, objectName1, objectName2, objectName3, oriFilePath, newFilePath};

    System.out.println("Start Java Srial Test");

    // Prepare for test
    teardownPro(args1[0]);
    teardownPro(args1[1]);
    teardownPro(sbucketName);
    teardownPro(dbucketName);

    System.out.println("\n---BucketSerial---");
    Bucket.main(args1);

    System.out.println("\n---ObjectSerial---");
    Object.main(args1);

    System.out.println("\n---ACLSerial---");
    ACL.main(args1);

    System.out.println("\n---BucketLoggingSerial---");
    BucketLogging.main(args1);

    System.out.println("\n---ClientSideEncryptionSerial---");
    CSE.main(args1);

    System.out.println("\n---DeleteMultipleObjectSerial---");
    deleteMultipleObject.main(args1);

    System.out.println("\n---HeadBucketSerial---");
    headBucket.main(args1);

    System.out.println("\n---LifecycleSerial---");
    lifeCycle.main(args1);

    System.out.println("\n---MultipartUploadSerial---");
    mpu.main(args1);

    System.out.println("\n---PolicySerial---");
    policy.main(args1);

    System.out.println("\n---PreSignedURLSerial---");
    preSignURL.main(args1);

    System.out.println("\n---PutObjectWithMD5Serial---");
    putObjectMD5.main(args1);

    System.out.println("\n---ServerSideEncryptionSerial---");
    sse.main(args1);

    System.out.println("\n---UploadBigFileByMPUSerial---");
    mpuBigFile.main(args1);

    System.out.println("\n---VersioningSerial---");
    versioning.main(args1);

    System.out.println("\n---WebsiteSerial---");
    website.main(args1);

    System.out.println("\n---BucketCorsSerial---");
    cors.main(args1);

    System.out.println("\n---BucketTaggingSerial---");
    tagging.main(args1);

    System.out.println("Java Serial Test End!");
  }
}
