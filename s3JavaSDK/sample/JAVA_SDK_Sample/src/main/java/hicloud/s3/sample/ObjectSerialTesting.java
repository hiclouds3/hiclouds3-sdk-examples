package hicloud.s3.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;


public class ObjectSerialTesting {

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

  private static InputStream ReadFile() throws IOException {
    File file = File.createTempFile("aws-java-sdk-", ".txt");

    Writer writer = new OutputStreamWriter(new FileOutputStream(file));
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.write("01234567890112345678901234\n");
    writer.write("01234567890112345678901234\n");
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.close();

    FileInputStream fin = new FileInputStream(file);
    return fin;
  }

  private static InputStream ReadFile1() throws IOException {
    File file = File.createTempFile("aws-java-sdk-", ".txt");

    Writer writer = new OutputStreamWriter(new FileOutputStream(file));
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.write("01234567890112345678901234\n");
    writer.write("01234567890112345678901234\n");
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.close();

    FileInputStream fin = new FileInputStream(file);
    return fin;
  }

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

  private static void basicPutObject(String bucketName) throws IOException {
    String fileName = "hi.txt";

    AmazonS3 s3 = S3Client.getClient();

    try {
      s3.createBucket(bucketName);

      System.out.println("basic put object");
      PutObjectRequest request = new PutObjectRequest(bucketName, fileName, createSampleFile());

      request.withGeneralProgressListener(new ProgressListener() {
        public void progressChanged(ProgressEvent event) {
          System.out.println("Transferred bytes: " + event.getBytesTransferred());
        }
      });
      s3.putObject(request);

      System.out.println("basic get object");
      GetObjectRequest getrequest = new GetObjectRequest(bucketName, fileName);
      getrequest.withGeneralProgressListener(new ProgressListener() {
        public void progressChanged(ProgressEvent event) {
          System.out.println("Transferred bytes: " + event.getBytesTransferred());
          System.out.println("Event Type: " + event.getEventType());
        }
      });
      S3Object object = s3.getObject(getrequest);

      System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
      System.out.println("ETag: " + object.getObjectMetadata().getETag());
      System.out.println("user-metadata: " + object.getObjectMetadata().getUserMetadata());
      System.out.println("raw-metadata: " + object.getObjectMetadata().getRawMetadata());
      displayTextInputStream(object.getObjectContent());
      System.out.println();

      System.out.println("Deleting object " + fileName + "\n");
      // Delete Object
      s3.deleteObject(bucketName, fileName);
      // Delete Bucket
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


  private static void mBasicPutObjectandhead(String bucketName) throws IOException {
    String fileName = "hello.txt";

    ObjectMetadata metadata = new ObjectMetadata();
    Map<String, String> map = new HashMap<String, String>();

    AmazonS3 s3 = S3Client.getClient();

    System.out.println("Creating source bucket " + bucketName + "\n");
    s3.createBucket(bucketName);

    try {
      System.out.println("put object metadata");

      // Config Object metadata
      map.put("x-amz-meta-month", "september");
      metadata.setUserMetadata(map);
      metadata.addUserMetadata("x-amz-meta-flower", "lily");
      metadata.addUserMetadata("x-amz-meta-color", "pink");
      metadata.setContentType("text/plain");
      metadata.setContentLength(108);
      metadata.setContentEncoding("UTF-8");
      metadata.setContentDisposition("attachment; filename=\"default.txt\"");
      metadata.setContentMD5("aSsJ8P/c05f2r0JDoSWbHg=="); // hello.txt

      System.out.println("Uploading a new object to S3 from a file\n");
      s3.putObject(new PutObjectRequest(bucketName, fileName, ReadFile(), metadata)
          .withInputStream(ReadFile1()).withCannedAcl(CannedAccessControlList.PublicRead)
          .withStorageClass("STANDARD").withMetadata(metadata));

      // print object metadata
      System.out.println("head object metadata");
      ObjectMetadata object = s3.getObjectMetadata(bucketName, fileName);
      System.out.println("Content-Length: " + object.getContentLength());
      System.out.println("raw-metadata: " + object.getRawMetadata());
      System.out.println("user-metadata: " + object.getUserMetadata());
      System.out.println("MD5: " + object.getContentMD5());
      System.out.println("ETag: " + object.getETag());
      System.out.println();

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

  private static void BasicCopyObject(String sbucketName, String dbucketName) throws IOException {

    String sfileName = "hello.txt";
    String dfileName = "hello.txt";

    ObjectMetadata metadata = new ObjectMetadata();

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -10);
    cal.add(Calendar.MONTH, 1);

    Calendar cal2 = Calendar.getInstance();
    cal2.add(Calendar.MONTH, 2);

    AmazonS3 s3 = S3Client.getClient();

    s3.createBucket(dbucketName);
    s3.putObject(sbucketName, sfileName, createSampleFile());

    try {

      metadata.addUserMetadata("x-amz-meta-company", "chttl");
      metadata.addUserMetadata("x-amz-meta-department", "cloud");
      metadata.setContentType("image/jpeg");

      System.out.println(
          "Copy object with storage class, acl, metadata, if-match, if-modify-since ...\n");
      CopyObjectResult result =
          s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName)
              .withStorageClass("STANDARD")
              .withCannedAccessControlList(CannedAccessControlList.PublicRead)
              .withNewObjectMetadata(metadata)
              .withMatchingETagConstraint("24156eb0ff2ed1056106fb2fc4d0ed85")
              .withModifiedSinceConstraint(cal.getTime()));
      if (result != null) {
        System.out.println(result.getETag());
        System.out.println(result.getLastModifiedDate());
      }

      System.out.println("Copy object with if-non-match ...\n");
      CopyObjectResult result1 =
          s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName)
              .withNonmatchingETagConstraint("692b09f0ffdcd397f6af4243a1259b1c"));
      if (result != null) {
        System.out.println(result1.getETag());
        System.out.println(result1.getLastModifiedDate());
      }

      System.out.println("Copy object with if-non-modify-since ...\n");
      CopyObjectResult result2 =
          s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName)
              .withUnmodifiedSinceConstraint(cal2.getTime()));
      if (result != null) {
        System.out.println(result2.getETag());
        System.out.println(result2.getLastModifiedDate());
      }

      // TearDown
      s3.deleteObject(sbucketName, sfileName);
      s3.deleteObject(dbucketName, dfileName);
      s3.deleteBucket(sbucketName);
      s3.deleteBucket(dbucketName);

      System.out.println("DONE");

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
     * test 1. Put Object 2. Get Object 3. Delete Object
     */
    basicPutObject(args[0]);

    /*
     * test 1. Put Object + Metadata 2. Head Object
     */
    mBasicPutObjectandhead(args[0]);

    /*
     * test 1. Copy Object
     */
    BasicCopyObject(args[0], args[1]);

  }

}
