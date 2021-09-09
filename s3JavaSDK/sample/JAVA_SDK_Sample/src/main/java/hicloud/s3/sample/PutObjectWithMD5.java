package hicloud.s3.sample;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class PutObjectWithMD5 {

  private static String calculateMD5(InputStream is) throws IOException {
    DigestInputStream dis = null;
    byte[] buff = new byte[1024];


    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");

      dis = new DigestInputStream(is, md5);

      // Read bytes from the file.
      while (dis.read(buff) != -1);

      byte[] md5Digests = md5.digest();
      String result = new String(Base64.encodeBase64(md5Digests));
      return result;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      buff = null;
      if (dis != null)
        dis.close();
    }
    return null;

  }



  private static void basicPutObject(String bucketName, String fileName, String resourceName)
      throws IOException {
    System.out.println("basic put bucket");

    ObjectMetadata metadata = new ObjectMetadata();

    AmazonS3 s3 = S3Client.getClient();
    try {
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);


      System.out.println("Uploading a object to S3 with MD5\n");
      metadata.setContentMD5(calculateMD5(Utils.getSampleFileIS(resourceName)));
      s3.putObject(new PutObjectRequest(bucketName, fileName, Utils.getSampleFileIS(resourceName), metadata));

      // tear down
      System.out.println("tear down");
      s3.deleteObject(bucketName, fileName);
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
    basicPutObject(args[0], args[2], args[5]);
  }

}
