package clientSampleSerial;


import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;

public class WebsiteSerialTesting {

  private static void basicPutBucketWebsite(String bucketName) throws IOException {
    AmazonS3 s3 = S3Client.getClient();

    // Create Bucket
    System.out.println("Creating bucket " + bucketName + "\n");
    s3.createBucket(bucketName);

    // Website Config
    BucketWebsiteConfiguration config = new BucketWebsiteConfiguration("indexTest.html");
    config.setErrorDocument("404Test.html");

    try {
      // Create Bucket with Website config
      System.out.println("basic put bucket website");
      s3.setBucketWebsiteConfiguration(bucketName, config);
      System.out.println();

      // Get Bucket Website config
      System.out.println("basic get bucket website");
      BucketWebsiteConfiguration result = null;
      result = s3.getBucketWebsiteConfiguration(bucketName);
      System.out.println();

      // Delete Bucket Website config
      System.out.println("basic delete bucket website");
      s3.deleteBucketWebsiteConfiguration(bucketName);

      // Delete Bucket
      System.out.println("Tear down..");
      s3.deleteBucket(bucketName);
    } catch (AmazonServiceException ase) {
      System.out.println("Caught an AmazonServiceException, which means your request made it "
          + "to Amazon S3, but was rejected with an error response for some reason.");
      System.out.println("Error Message:    " + ase.getMessage());
      System.out.println("HTTP Status Code: " + ase.getStatusCode());
      System.out.println("AWS Error Code:   " + ase.getErrorCode());
      System.out.println("Error Type:       " + ase.getErrorType());
      System.out.println("Request ID:       " + ase.getRequestId());
    } catch (AmazonClientException ace) {
      System.out.println("Caught an AmazonClientException, which means the client encountered "
          + "a serious internal problem while trying to communicate with S3, "
          + "such as not being able to access the network.");
      System.out.println("Error Message: " + ace.getMessage());
    }
  }

  private static void PutBucketWebsite(String bucketName) throws IOException {

    AmazonS3 s3 = S3Client.getClient();

    // Create Bucket
    System.out.println("Creating bucket " + bucketName + "\n");
    s3.createBucket(bucketName);

    // Website Config
    BucketWebsiteConfiguration config = new BucketWebsiteConfiguration("indexTest.html");

    try {
      // Create Bucket with Website config
      System.out.println("basic put bucket website");
      s3.setBucketWebsiteConfiguration(bucketName, config);
      System.out.println();

      // Get Bucket Website config
      System.out.println("basic get bucket website");
      BucketWebsiteConfiguration result = null;
      result = s3.getBucketWebsiteConfiguration(bucketName);

      // Delete Bucket Website config
      System.out.println("basic delete bucket website");
      s3.deleteBucketWebsiteConfiguration(bucketName);

      // Delete Bucket
      System.out.println("Tear down..");
      s3.deleteBucket(bucketName);
    } catch (AmazonServiceException ase) {
      System.out.println("Caught an AmazonServiceException, which means your request made it "
          + "to Amazon S3, but was rejected with an error response for some reason.");
      System.out.println("Error Message:    " + ase.getMessage());
      System.out.println("HTTP Status Code: " + ase.getStatusCode());
      System.out.println("AWS Error Code:   " + ase.getErrorCode());
      System.out.println("Error Type:       " + ase.getErrorType());
      System.out.println("Request ID:       " + ase.getRequestId());
    } catch (AmazonClientException ace) {
      System.out.println("Caught an AmazonClientException, which means the client encountered "
          + "a serious internal problem while trying to communicate with S3, "
          + "such as not being able to access the network.");
      System.out.println("Error Message: " + ace.getMessage());
    }
  }

  public static void main(String args[]) throws IOException {
    System.out.println("hello world");

    /*
     * test 1. PutBucketWebsite 2. GetBucketWebsite 3. DeleteBucketWebsite
     */
    basicPutBucketWebsite(args[0]);

    /*
     * test 1. PutBucketWebsite without Optional xml field 2. GetBucketWebsite 3.
     * DeleteBucketWebsite
     */
    PutBucketWebsite(args[0]);
  }
}
