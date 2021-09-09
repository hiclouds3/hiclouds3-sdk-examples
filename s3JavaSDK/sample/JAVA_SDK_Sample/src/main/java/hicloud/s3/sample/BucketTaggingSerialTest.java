package hicloud.s3.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketTaggingConfiguration;
import com.amazonaws.services.s3.model.TagSet;


public class BucketTaggingSerialTest {

  private static void PutBucketTagging(String bucketName) throws IOException {
    System.out.println("Put bucket tagging..\n");

    AmazonS3 s3 = S3Client.getClient();
    try {
      // Create Bucket
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);


      // Prepare Tagging Configuration
      Map<String, String> tags = new HashMap<String, String>();
      tags.put("Mary", "music");
      tags.put("Tom", "film");

      TagSet tagSet = new TagSet(tags);

      Collection<TagSet> tagSets = new ArrayList<TagSet>();;
      tagSets.add(tagSet);


      BucketTaggingConfiguration taggingConfig = new BucketTaggingConfiguration();
      taggingConfig.setTagSets(tagSets);

      // setBucketTagging
      System.out.println("Set bucket tagging..\n");
      s3.setBucketTaggingConfiguration(bucketName, taggingConfig);

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

  private static void GetBucketTagging(String bucketName) throws IOException {
    System.out.println("Get bucket tagging..");

    AmazonS3 s3 = S3Client.getClient();
    try {
      // getBucketTagging
      BucketTaggingConfiguration bucketTaggingResult = s3.getBucketTaggingConfiguration(bucketName);

      // print Bucket Tagging Config
      System.out.println("List Bucket Tagging Config");
      List<TagSet> tagSets = bucketTaggingResult.getAllTagSets();

      System.out.println("<Tagging><TagSet>");
      for (TagSet tagSet : tagSets) {
        Map<String, String> map = tagSet.getAllTags();
        for (Object key : map.keySet()) {
          System.out.println("<Tag>");
          System.out.println("<Key>" + key + "</Key>" + "<Value>" + map.get(key) + "</Value>");
          System.out.println("</Tag>");
        }

      }
      System.out.println("</TagSet></Tagging>");
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

  private static void DeleteBucketTagging(String bucketName) throws IOException {
    System.out.println("Delete bucket tagging..");

    AmazonS3 s3 = S3Client.getClient();
    try {
      // deleteBucketTagging
      s3.deleteBucketTaggingConfiguration(bucketName);

      // deleteBucket (Teardown)
      System.out.println("Deleting bucket " + bucketName);
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
     * Put Bucket Tagging
     */
    PutBucketTagging(args[0]);


    /*
     * Get Bucket Tagging
     */
    GetBucketTagging(args[0]);

    /*
     * Delete Bucket Tagging
     */
    DeleteBucketTagging(args[0]);
  }

}
