package hicloud.s3.sample;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;

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

    String bucketName1 = "bucketonejava";
    String bucketName2 = "buckettwojava";
    String objectName1 = "objectonejava";
    String objectName2 = "objecttwojava";
    String objectName3 = "jobjectthreejava";
    String oriFileResName = "5M_File.jpg"; // files under src/main/resources dir
    String sbucketName = "sourcejava";
    String dbucketName = "targetjava";


    String args1[] =
        {bucketName1, bucketName2, objectName1, objectName2, objectName3, oriFileResName};

    System.out.println("Start Java Srial Test");

    // Prepare for test
    teardownPro(args1[0]);
    teardownPro(args1[1]);
    teardownPro(sbucketName);
    teardownPro(dbucketName);

    System.out.println("\n---BucketSerial---");
    BucketSerialTesting.main(args1);

    System.out.println("\n---ObjectSerial---");
    ObjectSerialTesting.main(args1);

    System.out.println("\n---ACLSerial---");
    ACLSerialTesting.main(args1);

    System.out.println("\n---BucketLoggingSerial---");
    BucketLoggingSerialTesting.main(args1);

    System.out.println("\n---DeleteMultipleObjectSerial---");
    DeleteMultipleObject.main(args1);

    System.out.println("\n---HeadBucketSerial---");
    HeadBucket.main(args1);

    System.out.println("\n---LifecycleSerial---");
    LifecycleSerial.main(args1);

    System.out.println("\n---MultipartUploadSerial---");
    MPUSerialTesting.main(args1);

    System.out.println("\n---PolicySerial---");
    PolicySerialTesting.main(args1);

    System.out.println("\n---PreSignedURLSerial---");
    PreSignedURL.main(args1);

    System.out.println("\n---PutObjectWithMD5Serial---");
    PutObjectWithMD5.main(args1);

    System.out.println("\n---ServerSideEncryptionSerial---");
    ServerSideEncryption.main(args1);

    System.out.println("\n---UploadBigFileByMPUSerial---");
    UploadBigFileByMPU.main(args1);

    System.out.println("\n---VersioningSerial---");
    VersioningSerialTesting.main(args1);

    System.out.println("\n---WebsiteSerial---");
    WebsiteSerialTesting.main(args1);

    System.out.println("\n---BucketCorsSerial---");
    BucketCorsSerialTesting.main(args1);

    System.out.println("\n---BucketTaggingSerial---");
    BucketTaggingSerialTest.main(args1);

    System.out.println("Java Serial Test End!");
  }
}
