package hicloud.s3.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * The sample program intends to demonstrate how to perform several S3-compatible operations on
 * hicloud S3
 *
 * To access hicloud S3, you will need to sign up for a hicloud S3 account. To sign up for a hicloud
 * S3 account Go to https://userportal.hicloud.hinet.net/cloud/ & Follow the on-screen instructions.
 *
 * Important note: Before running this program, make sure your AccessKey, SecretKey is correctly set
 * in 「AwsCredentials.properties」
 */
public class S3Sample {

  public static void main(String[] args) throws IOException {

    AmazonS3 s3 = S3Client.getClient();

    String bucketName = "my-first-s3-bucket-" + UUID.randomUUID();
    String key = "MyObjectKey";

    System.out.println("===========================================");
    System.out.println("Getting Started with Amazon S3");
    System.out.println("===========================================\n");

    try {
      /*
       * Create Bucket
       */
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      /*
       * List all Buckets owned by the account
       */
      System.out.println("Listing buckets");
      for (Bucket bucket : s3.listBuckets()) {
        System.out.println(" - " + bucket.getName());
      }
      System.out.println();

      /*
       * Upload Objects
       */
      System.out.println("Uploading a new object to S3 from a file\n");
      s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));

      /*
       * Download Objects
       */
      System.out.println("Downloading an object");
      S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
      System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
      displayTextInputStream(object.getObjectContent());

      /*
       * List objects with prefix "My" in the Bucket
       */
      System.out.println("Listing objects");
      ObjectListing objectListing =
          s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix("My"));
      for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
        System.out.println(
            " - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
      }
      System.out.println();

      /*
       * Delete Object
       */
      System.out.println("Deleting an object\n");
      s3.deleteObject(bucketName, key);

      /*
       * Delete Bucket
       * 
       * Note: The bucket must be empty to be deleted!
       */
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

  /**
   * Create a temporary file to demonstrate putObject Operation
   *
   * @return Temporary File Object
   * @throws IOException
   */
  private static File createSampleFile() throws IOException {
    File file = File.createTempFile("aws-java-sdk-", ".txt");
    file.deleteOnExit();

    Writer writer = new OutputStreamWriter(new FileOutputStream(file));
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.write("01234567890112345678901234\n");
    writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
    writer.write("01234567890112345678901234\n");
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.close();

    return file;
  }

  /**
   * Dump the contents of the InputStream
   *
   * @param InputStream
   * @throws IOException
   */
  private static void displayTextInputStream(InputStream input) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    while (true) {
      String line = reader.readLine();
      if (line == null)
        break;

      System.out.println("    " + line);
    }
    System.out.println();
  }
}
