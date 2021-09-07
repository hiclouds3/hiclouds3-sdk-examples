package clientSampleSerial;

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
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * 此範例程式為示範如何使用hicloud S3 JAVA SDK發送基本的request至hicloud S3
 * 
 * 前提：必須先取得hicloud S3開發所需之AccessKey與SecretKey 取得AccessKey與SecretKey之流程，請參考"hicloud S3 Quick Start"文件
 * : https://userportal.hicloud.hinet.net/cloud/document/files/hicloud-S3-QuickStart.pdf
 *
 * 重要：在運行此範例程式前，請務必確認已將hicloud S3 access credentials填入AwsCredentials.properties檔案中
 */
public class S3Sample {

  public static void main(String[] args) throws IOException {
    /*
     * 重要：在運行此範例程式前，請務必確認已將hicloud S3 access credentials填入AwsCredentials.properties檔案中
     */

    AmazonS3 s3 = S3Client.getClient();

    String bucketName = "my-first-s3-bucket-" + UUID.randomUUID();
    String key = "MyObjectKey";

    System.out.println("===========================================");
    System.out.println("Getting Started with Amazon S3");
    System.out.println("===========================================\n");

    try {
      /*
       * 創建Bucket - Bucket 名稱必須是唯一，若 Bucket 名稱已被其他使用者所使用時，將無法成功建立相同名稱的Bucket
       */
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      /*
       * 列出帳號下所有的Bucket
       */
      System.out.println("Listing buckets");
      for (Bucket bucket : s3.listBuckets()) {
        System.out.println(" - " + bucket.getName());
      }
      System.out.println();

      /*
       * 上傳Object到所建立的Bucket - 可採用file格式或InputStream格式來上傳檔案
       * 在上傳檔案的同時，也能夠設定個人化metadata，如content-type、content-encoding等metadata
       */
      System.out.println("Uploading a new object to S3 from a file\n");
      s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));

      /*
       * 下載Object - 當下載Object時，會將Object相關metadata及Object內容都下載回來
       *
       * GetObjectRequest也提供條件下載的選項，如可選擇Object修改時間在某時間後的Object下載，或選擇特定ETags的Object下載，或只下載部分Object
       */
      System.out.println("Downloading an object");
      S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
      System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
      displayTextInputStream(object.getObjectContent());

      /*
       * 列出Bucket中所有Prefix為"My"的Object
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
       * 刪除Object - 除非在Versioning機制開啟的情況下，任何刪除Object的動作都是無法回復的，因此必須謹慎的選擇欲刪除的Object
       */
      System.out.println("Deleting an object\n");
      s3.deleteObject(bucketName, key);

      /*
       * 刪除Bucket - 欲刪除Bucket，此Bucket必須是已清空的，因此欲刪除Bucket前請先確認Bucket中是否仍存在任何Object
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
   * 建立暫存檔，用於範例中上傳至hicloud S3
   *
   * @return 回傳一 File Object，代表暫時建立的文字檔
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
   * 印出input stream的內容
   *
   * @param input 欲顯示的input stream
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
