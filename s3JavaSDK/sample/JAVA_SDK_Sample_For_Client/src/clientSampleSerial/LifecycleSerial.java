package clientSampleSerial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration.Rule;


public class LifecycleSerial {

  @SuppressWarnings("unused")
  private static void basicLifecycle(String bucketName) throws IOException {
    AmazonS3 s3 = S3Client.getClient();
    // 創建Bucket
    System.out.println("Creating source bucket " + bucketName + "\n");
    s3.createBucket(bucketName);

    // 設定Lifecycle Rule
    List<Rule> rules = new ArrayList<Rule>();

    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    c.add(Calendar.YEAR, 10);
    c.set(Calendar.DAY_OF_MONTH, 12);
    c.set(Calendar.MONTH, 9);
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);

    Rule rule = new Rule();
    rule.setId("test");
    rule.setPrefix("pre");
    rule.setStatus("Enabled");
    rule.setExpirationDate(c.getTime());

    rules.add(rule);

    try {
      // 設定Bucket Lifecycle
      s3.setBucketLifecycleConfiguration(bucketName,
          new BucketLifecycleConfiguration().withRules(rules));

      // 查看Bucket Lifecycle設定
      BucketLifecycleConfiguration result = s3.getBucketLifecycleConfiguration(bucketName);
      System.out
          .println("GetBucketLifecycle Result:\n" + result.getRules().get(0).getExpirationDate());

      // 刪除Bucket Lifecycle設定
      s3.deleteBucketLifecycleConfiguration(bucketName);

      // 刪除Bucket
      s3.deleteBucket(bucketName);

      System.out.println("END");
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
     * PutBucketlifecycle GetBucketlifecycle DeleteBucketLifecycle
     */
    basicLifecycle(args[0]);
  }

}
