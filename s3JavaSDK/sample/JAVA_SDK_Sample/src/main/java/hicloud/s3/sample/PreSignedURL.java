package hicloud.s3.sample;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

public class PreSignedURL {

  private static void UploadObject(URL url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setDoOutput(true);
    connection.setRequestMethod("PUT");
    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
    out.write("object content....");
    out.close();
    int responseCode = connection.getResponseCode();
    System.out.println(responseCode + " " + connection.getResponseMessage());

  }

  private static void deleteObject(URL url) throws IOException {
    HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
    httpCon.setRequestMethod("DELETE");
    httpCon.connect();

    int responseCode = httpCon.getResponseCode();
    System.out.println(responseCode);
  }

  private static void DownloadObject(URL url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    // 欲設定下載 Object 的範圍時可使用以下方法
    // connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
    // InputStream inputstream = connection.getInputStream();


    int responseCode = connection.getResponseCode();
    System.out.println(responseCode);
  }

  public static void main(String args[]) throws IOException {
    AmazonS3 s3 = S3Client.getClient();

    try {
      s3.createBucket(args[0]);

      // 產生preSignedURL，並用此URL上傳Object
      GeneratePresignedUrlRequest request =
          new GeneratePresignedUrlRequest(args[0], args[2], HttpMethod.PUT);
      request.setExpiration(new Date(System.currentTimeMillis() + (120 * 60 * 1000)));
      URL preSignUrl = s3.generatePresignedUrl(request);
      System.out.println(preSignUrl.toString());
      UploadObject(preSignUrl);

      // 產生preSignedURL，並用此URL下載Object
      request = new GeneratePresignedUrlRequest(args[0], args[2], HttpMethod.GET);
      request.setExpiration(new Date(System.currentTimeMillis() + (120 * 60 * 1000)));
      preSignUrl = s3.generatePresignedUrl(request);
      System.out.println(preSignUrl.toString());
      DownloadObject(preSignUrl);


      // 產生preSignedURL，並用此URL刪除Object
      request = new GeneratePresignedUrlRequest(args[0], args[2], HttpMethod.DELETE);
      request.setExpiration(new Date(System.currentTimeMillis() + (120 * 60 * 1000)));
      preSignUrl = s3.generatePresignedUrl(request);
      System.out.println(preSignUrl.toString());
      deleteObject(preSignUrl);

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
    } catch (ConnectException ex) {
      System.out.println("Caught an ConnectException, check if you are behind a proxy.");
      System.out.println("Error Message: " + ex.getMessage());
    } finally {
      s3.deleteBucket(args[0]);
    }
  }

}
