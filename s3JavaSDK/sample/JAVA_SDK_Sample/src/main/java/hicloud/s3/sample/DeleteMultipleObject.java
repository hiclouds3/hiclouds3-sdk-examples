package hicloud.s3.sample;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class DeleteMultipleObject {

  private static File createSampleFile() throws IOException {
    File file = File.createTempFile("encryptiontest", ".txt");
    file.deleteOnExit();

    Writer writer = new OutputStreamWriter(new FileOutputStream(file));
    writer.write(new java.util.Date().toString() + "\n");
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.write("01234567890112345678901234\n");
    writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
    writer.write("01234567890112345678901234\n");
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.close();

    return file;
  }

  private static void createBucket(String bucketName) throws IOException {
    System.out.println("basic put bucket");

    AmazonS3 s3 = S3Client.getClient();
    try {
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);
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

  private static String basicPutObject(String bucketName, String fileName) throws IOException {
    AmazonS3 s3 = S3Client.getClient();
    try {
      System.out.println("Uploading a new object to S3 from a file\n");
      PutObjectRequest request = new PutObjectRequest(bucketName, fileName, createSampleFile());

      PutObjectResult result = s3.putObject(request);
      System.out.println("Etag: " + result.getETag());
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
    return null;
  }

  private static void basicDeleteMultipleObject(String bucketName, String object1, String object2,
      String object3) throws IOException {
    AmazonS3 s3 = S3Client.getClient();

    DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName);

    // 將要刪除的 object 一筆一筆加入 list 中
    List<KeyVersion> keys = new ArrayList<KeyVersion>();
    keys.add(new KeyVersion(object1));
    keys.add(new KeyVersion(object2));
    keys.add(new KeyVersion(object3));

    multiObjectDeleteRequest.setKeys(keys);
    multiObjectDeleteRequest.setQuiet(true);

    try {
      System.out.println("Delete Multiple Object \n");
      DeleteObjectsResult result = s3.deleteObjects(multiObjectDeleteRequest);
      System.out.println("Successfully deleted: " + result.getDeletedObjects().isEmpty());

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

  private static void BasicDeleteBucket(String bucketName) throws IOException {
    System.out.println("basic delete bucket");

    AmazonS3 s3 = S3Client.getClient();
    try {
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
    // 創建Bucket
    createBucket(args[0]);
    // 上傳3個Object
    basicPutObject(args[0], args[2]);
    basicPutObject(args[0], args[3]);
    basicPutObject(args[0], args[4]);
    // 一次刪除先前上傳的三個Object
    basicDeleteMultipleObject(args[0], args[2], args[3], args[4]);
    // 刪除Bucket
    BasicDeleteBucket(args[0]);
  }

}
