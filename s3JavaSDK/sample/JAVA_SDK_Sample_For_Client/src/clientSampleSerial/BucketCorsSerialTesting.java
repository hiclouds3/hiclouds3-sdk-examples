package clientSampleSerial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.CORSRule;
import com.amazonaws.services.s3.model.CORSRule.AllowedMethods;


public class BucketCorsSerialTesting {

  private static void PutBucketCors(String bucketName) throws IOException {
    System.out.println("Put bucket cors..\n");

    AmazonS3 s3 = S3Client.getClient();
    try {
      // Create Bucket
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);


      // Prepare Rule Configuration
      AllowedMethods allowedMethods = null;
      List<CORSRule> rules = new ArrayList<CORSRule>();
      CORSRule ruleOne = new CORSRule();
      ruleOne.setId("Rule One");
      ruleOne.setAllowedOrigins("http://google.com");
      ruleOne.setAllowedMethods(allowedMethods.GET);
      ruleOne.setExposedHeaders("content-type");
      ruleOne.setAllowedHeaders("content-encoding");
      ruleOne.setMaxAgeSeconds(100);

      CORSRule ruleTwo = new CORSRule();
      ruleTwo.setId("Rule Two");
      ruleTwo.setAllowedOrigins("tw.yahoo.com");
      ruleTwo.setAllowedMethods(allowedMethods.HEAD);
      ruleTwo.setExposedHeaders("content-length");
      ruleTwo.setAllowedHeaders("x-amz-*");
      ruleTwo.setMaxAgeSeconds(10);

      rules.add(ruleOne);
      rules.add(ruleTwo);

      BucketCrossOriginConfiguration corsRule = new BucketCrossOriginConfiguration();
      corsRule.setRules(rules);

      // setBucketCORS
      s3.setBucketCrossOriginConfiguration(bucketName, corsRule);

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

  private static void GetBucketCors(String bucketName) throws IOException {
    System.out.println("Get bucket cors..\n");

    AmazonS3 s3 = S3Client.getClient();
    try {
      // getBucketCORS
      BucketCrossOriginConfiguration bucketCorsResult =
          s3.getBucketCrossOriginConfiguration(bucketName);

      // print Bucket CORS Rule
      for (CORSRule rule : bucketCorsResult.getRules()) {
        System.out.println("List Bucket Rules");
        System.out.println("Rule ID: " + rule.getId());
        System.out.println("Rule Origin: " + rule.getAllowedOrigins());
        System.out.println("Rule Method: " + rule.getAllowedMethods());
        System.out.println("Rule allowHeader: " + rule.getAllowedHeaders());
        System.out.println("Rule exposeHeader: " + rule.getExposedHeaders());
        System.out.println("Rule maxAge: " + rule.getMaxAgeSeconds());
      }
      System.out.println("-End-\n");

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

  private static void DeleteBucketCors(String bucketName) throws IOException {
    System.out.println("Delete bucket cors..\n");

    AmazonS3 s3 = S3Client.getClient();
    try {
      // deleteBucketCORS
      s3.deleteBucketCrossOriginConfiguration(bucketName);

      // deleteBucket (Teardown)
      System.out.println("Deleting bucket " + bucketName + "\n");
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
     * Put Bucket CORS
     */
    PutBucketCors(args[0]);


    /*
     * Get Bucket CORS
     */
    GetBucketCors(args[0]);

    /*
     * Delete Bucket CORS
     */
    DeleteBucketCors(args[0]);
  }

}
