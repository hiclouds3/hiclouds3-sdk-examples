package hicloud.s3.sample;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;


public class UploadBigFileByMPU {

  private static void autoUploadBigFileByPart(String bucketName, String fileName,
      String resourceName) throws IOException {
    List<PartETag> partETags = new ArrayList<PartETag>();


    InitiateMultipartUploadRequest initRequest =
        new InitiateMultipartUploadRequest(bucketName, fileName);

    AmazonS3 s3 = S3Client.getClient();

    try {
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      System.out.println("basic initial MPU");
      InitiateMultipartUploadResult initResponse = s3.initiateMultipartUpload(initRequest);
      String UploadID = initResponse.getUploadId();

      System.out.println("Upload Part");

      long contentLength = 6270544;
      long partSize = 5 * 1024 * 1024; // Set part size to 5 MB.
      long filePosition = 0;
      try (InputStream is = Utils.getSampleFileIS(resourceName)) {
        for (int i = 1; filePosition < contentLength; i++) {
          // Last part can be less than 5 MB. Adjust part size.
          partSize = Math.min(partSize, (contentLength - filePosition));

          // Create request to upload a part.
          UploadPartRequest uploadRequest = new UploadPartRequest().withBucketName(bucketName)
              .withKey(fileName).withUploadId(UploadID).withPartNumber(i).withInputStream(is)
              .withPartSize(partSize);

          // Upload part and add response to our list.
          partETags.add(s3.uploadPart(uploadRequest).getPartETag());
          filePosition += partSize;
        }
      }

      // Complete MPU
      System.out.println("basic Complete MPU");
      CompleteMultipartUploadRequest compRequest =
          new CompleteMultipartUploadRequest(bucketName, fileName, UploadID, partETags);
      s3.completeMultipartUpload(compRequest);

      // Check File
      System.out.println("\nCheck file, file info:");

      ObjectMetadata object = s3.getObjectMetadata(bucketName, fileName);
      System.out.println("Content-Length: " + object.getContentLength());
      System.out.println("raw-metadata: " + object.getRawMetadata());
      System.out.println("ETag: " + object.getETag());
      System.out.println();

      System.out.println("Tear down..");
      // Delete Object
      s3.deleteObject(bucketName, fileName);
      // Delete Bucket
      s3.deleteBucket(bucketName);
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
     * test 1. PutBucket 2. Initial MPU 3. UploadParts 4. Complete MPU
     */
    autoUploadBigFileByPart(args[0], args[2], args[5]);

    System.gc(); // grabage collection
  }

}
