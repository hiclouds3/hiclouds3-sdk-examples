package clientSampleSerial;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.ListPartsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;

public class MPUSerialTesting {

  // 讀取檔案供Upload Part使用(檔案大小需大於5M)
  private static File createSampleFile(String filePath) throws IOException {
    File file = new File(filePath);
    return file;
  }

  private static void basicUploadPart(String bucketName, String fileName, String uploadID,
      int partNumber, String filePath) throws IOException {
    System.out.println("basic Upload Part");

    // 設定UPloadPart資訊
    UploadPartRequest config = new UploadPartRequest();
    config.setBucketName(bucketName);
    config.setKey(fileName);
    config.setPartNumber(partNumber); // part number
    config.setUploadId(uploadID);
    config.setFile(createSampleFile(filePath));
    config.setPartSize(6270544);

    AmazonS3 s3 = S3Client.getClient();
    try {
      // UploadPart
      UploadPartResult result = s3.uploadPart(config);
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
    System.gc();
  }

  private static int basicListParts(String bucketName, String fileName, String uploadID)
      throws IOException {
    System.out.println("basic Upload Part");

    ListPartsRequest request = new ListPartsRequest(bucketName, fileName, uploadID);
    int count = 0;

    AmazonS3 s3 = S3Client.getClient();
    try {
      // 列出一Object中包含的Part File
      PartListing result = s3.listParts(request);
      for (PartSummary s : result.getParts()) {
        System.out.println("partNumber: " + s.getPartNumber());
      }
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
    System.gc();
    return count;
  }

  private static int MaxListParts(String bucketName, String fileName, String uploadID, int MaxPart)
      throws IOException {
    System.out.println("MaxListParts");

    ListPartsRequest request =
        new ListPartsRequest(bucketName, fileName, uploadID).withMaxParts(MaxPart);
    int count = 0;

    AmazonS3 s3 = S3Client.getClient();
    try {
      // 列出一Object中包含的Part File，搭配Max Parts參數過濾結果
      PartListing result = s3.listParts(request);
      for (PartSummary s : result.getParts()) {
        System.out.println("partNumber: " + s.getPartNumber());
      }
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
    System.gc();
    return count;
  }

  private static int MarkerListParts(String bucketName, String fileName, String uploadID,
      int Marker) throws IOException {
    System.out.println("Marker ListParts");

    ListPartsRequest request =
        new ListPartsRequest(bucketName, fileName, uploadID).withPartNumberMarker(Marker);
    int count = 0;

    AmazonS3 s3 = S3Client.getClient();
    try {
      // 列出一Object中包含的Part File，搭配Marker參數過濾結果
      PartListing result = s3.listParts(request);
      for (PartSummary s : result.getParts()) {
        System.out.println("partNumber: " + s.getPartNumber());
      }
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
    System.gc();
    return count;
  }

  private static void basicCompleteMPU(String bucketName, String fileName, String uploadID)
      throws IOException {
    System.out.println("basic complete MPU");
    ListPartsRequest request = new ListPartsRequest(bucketName, fileName, uploadID);
    List<PartETag> list = new ArrayList<PartETag>(); // etag
    int counter = 0;

    AmazonS3 s3 = S3Client.getClient();

    try {
      // 列出一Object中包含的Part File，並取得UploadID供CompleteMPU使用
      PartListing result = s3.listParts(request);
      for (PartSummary s : result.getParts()) {
        counter++;
        list.add(new PartETag(counter, s.getETag()));
      }

      // CompleteMPU
      CompleteMultipartUploadRequest config =
          new CompleteMultipartUploadRequest(bucketName, fileName, uploadID, list);
      CompleteMultipartUploadResult object = s3.completeMultipartUpload(config);
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

  private static void AbortMPU(String bucketName, String fileName, String filePath)
      throws IOException {
    ObjectMetadata meta = new ObjectMetadata();
    CannedAccessControlList acl = null;
    meta.setHeader("x-amz-color", "red");

    InitiateMultipartUploadRequest config =
        new InitiateMultipartUploadRequest(bucketName, fileName, meta)
            .withCannedACL(acl.AuthenticatedRead).withStorageClass(StorageClass.Standard);

    AmazonS3 s3 = S3Client.getClient();

    try {
      // 創建Bucket
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      // 初始化一個MPU
      System.out.println("basic initial MPU");
      InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);
      String UploadID = initRequest.getUploadId();

      // UploadPart
      System.out.println("Upload Part -1");
      basicUploadPart(bucketName, fileName, UploadID, 1, filePath);
      // UploadPart
      System.out.println("Upload Part -2");
      basicUploadPart(bucketName, fileName, UploadID, 2, filePath);

      // 中斷MPU
      System.out.println("basic abort MPU");
      AbortMultipartUploadRequest abort =
          new AbortMultipartUploadRequest(bucketName, fileName, UploadID);
      s3.abortMultipartUpload(abort);

      System.out.println("Tear down..");
      // 刪除Object
      s3.deleteObject(bucketName, fileName);
      // 刪除Bucket
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

  private static void CompleteMPU(String bucketName, String fileName, String filePath)
      throws IOException {
    ObjectMetadata meta = new ObjectMetadata();
    CannedAccessControlList Cannedacl = null;
    meta.setHeader("x-amz-color", "red");

    InitiateMultipartUploadRequest config =
        new InitiateMultipartUploadRequest(bucketName, fileName, meta)
            .withCannedACL(Cannedacl.AuthenticatedRead).withObjectMetadata(meta);

    AmazonS3 s3 = S3Client.getClient();

    try {
      // 創建Bucket
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      // 初始化MPU,此範例示範情境：使用者自行準備三個Part檔準備上傳至hicloud S3中，並在hicloud S3上組合為一完整檔案
      // 欲自行將大檔分割並上傳，請參考UploadBigFileByMPU.java
      System.out.println("basic initial MPU");
      InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);
      String UploadID = initRequest.getUploadId();

      // UploadPart
      System.out.println("Upload Part -1");
      basicUploadPart(bucketName, fileName, UploadID, 1, filePath);

      // UploadPart
      System.out.println("Upload Part -2");
      basicUploadPart(bucketName, fileName, UploadID, 2, filePath);

      // UploadPart
      System.out.println("Upload Part -3");
      basicUploadPart(bucketName, fileName, UploadID, 3, filePath);

      // 列出一Object中包含的Part File，搭配Max Part參數過濾結果
      MaxListParts(bucketName, fileName, UploadID, 2);

      // 列出一Object中包含的Part File，搭配Part Number Marker參數過濾結果
      MarkerListParts(bucketName, fileName, UploadID, 2);

      // Complete MPU
      System.out.println("basic Complete MPU");
      basicCompleteMPU(bucketName, fileName, UploadID);

      System.out.println("Tear down..");
      // 刪除Object
      s3.deleteObject(bucketName, fileName);
      // 刪除Bucket
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

  private static void basicUploadPartCopy(String sbucketName, String dbucketName, String sfileName,
      String dfileName, String uploadID, int count) throws IOException // with If-Match &
                                                                       // If-Modify-Since
  {
    long firstByte = 1;
    long lastByte = 6270544;
    List<String> list = new ArrayList<String>(); // etag
    list.add("339bbff383f4a3a129037a2029f62bf5");
    Date date = new Date();
    date.setYear(date.getYear() - 10);
    date.setMonth(date.getMonth() + 1);
    date.setDate(date.getDate());


    AmazonS3 s3 = S3Client.getClient();
    try {
      System.out.println("basic Upload Part Copy...\n");
      CopyPartRequest request = new CopyPartRequest();
      request.setDestinationBucketName(dbucketName);
      request.setDestinationKey(dfileName);
      request.setUploadId(uploadID);
      request.setPartNumber(count);
      request.setSourceBucketName(sbucketName);
      request.setSourceKey(sfileName);
      request.setMatchingETagConstraints(list);
      request.setModifiedSinceConstraint(date);

      // Upload Part Copy
      CopyPartResult result = s3.copyPart(request);
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

  private static void NonUploadPartCopy(String sbucketName, String dbucketName, String sfileName,
      String dfileName, String uploadID, int count) throws IOException // with If-Non-Match &
                                                                       // If-Nonmodify-Since
  {
    long firstByte = 1;
    long lastByte = 6270544;
    List<String> list = new ArrayList<String>(); // etag
    list.add("339bbff383f4a3a129037a2029f62bf4"); // wrong etag
    Date date = new Date();
    date.setYear(date.getYear());
    date.setMonth(date.getMonth() + 2);
    date.setDate(date.getDate());


    AmazonS3 s3 = S3Client.getClient();
    try {
      System.out.println("basic Upload Part Copy...\n");
      CopyPartRequest request = new CopyPartRequest();
      request.setDestinationBucketName(dbucketName);
      request.setDestinationKey(dfileName);
      request.setUploadId(uploadID);
      request.setPartNumber(count);
      request.setSourceBucketName(sbucketName);
      request.setSourceKey(sfileName);
      request.setNonmatchingETagConstraints(list);
      request.setUnmodifiedSinceConstraint(date);
      // Upload Part Copy
      CopyPartResult result = s3.copyPart(request);
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

  private static void CopyPart(String bucketName, String sbucketName, String fileName,
      String sfileName, String filePath) throws IOException {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(6270544);

    InitiateMultipartUploadRequest config =
        new InitiateMultipartUploadRequest(bucketName, fileName);

    AmazonS3 s3 = S3Client.getClient();

    try {
      // 創建Bucket
      System.out.println("Creating bucket" + "\n");
      s3.createBucket(bucketName);
      s3.createBucket(sbucketName);

      // 上傳Object作為複製時的來源檔案
      System.out.println("Put source Object" + "\n");
      PutObjectResult object =
          s3.putObject(new PutObjectRequest(sbucketName, sfileName, createSampleFile(filePath))
              .withFile(createSampleFile(filePath)).withMetadata(metadata));
      System.out.println(object.getETag());

      // 初始一個MPU
      System.out.println("basic initial MPU");
      InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);
      String UploadID = initRequest.getUploadId();

      // UploadPartCopy，搭配If-Match及If-Modified-Since Header
      basicUploadPartCopy(sbucketName, bucketName, sfileName, fileName, UploadID, 1);
      // UploadPartCopy，搭配If-Non-Match及If-Unmodified-Since Header
      NonUploadPartCopy(sbucketName, bucketName, sfileName, fileName, UploadID, 2);

      // 中斷MPU
      AbortMultipartUploadRequest abort =
          new AbortMultipartUploadRequest(bucketName, fileName, UploadID);
      s3.abortMultipartUpload(abort);

      System.out.println("Tear down..");
      // 刪除Object
      s3.deleteObject(bucketName, fileName);
      s3.deleteObject(sbucketName, sfileName);
      // 刪除Bucket
      s3.deleteBucket(sbucketName);
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

  private static void PrefixListMPUs(String bucketName, String Prefix, String Delimiter)
      throws IOException // Prefix & Delimeter
  {
    System.out.println("basic list MPUs");

    ListMultipartUploadsRequest request =
        new ListMultipartUploadsRequest(bucketName).withPrefix(Prefix).withDelimiter(Delimiter);

    AmazonS3 s3 = S3Client.getClient();
    try {
      // 列出Bucket中所有Part File，搭配Prefix及Delimiter參數過濾結果
      MultipartUploadListing result = s3.listMultipartUploads(request);
      for (MultipartUpload s : result.getMultipartUploads()) {
        System.out.println(s.getKey());
        System.out.println(s.getUploadId());
      }
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

  private static void MaxListMPUs(String bucketName, String KeyMarker, int MaxUpload)
      throws IOException // MaxUpload & KeyMarker
  {
    System.out.println("list MPUs with MaxUpload & KeyMarker");

    ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName)
        .withMaxUploads(MaxUpload).withKeyMarker(KeyMarker);

    AmazonS3 s3 = S3Client.getClient();
    try {
      // 列出Bucket中所有Part File，搭配Max Upload及Key Marker參數過濾結果
      MultipartUploadListing result = s3.listMultipartUploads(request);
      for (MultipartUpload s : result.getMultipartUploads()) {
        System.out.println(s.getKey());
        System.out.println(s.getUploadId());
      }
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

  private static void MarkerListMPUs(String bucketName, String IDMarker) throws IOException // Upload
                                                                                            // ID
                                                                                            // Marker
  {
    System.out.println("list MPUs with Upload ID Marker");

    ListMultipartUploadsRequest request =
        new ListMultipartUploadsRequest(bucketName).withUploadIdMarker(IDMarker);

    AmazonS3 s3 = S3Client.getClient();
    try {
      // 列出Bucket中所有Part File，搭配Upload ID Marker參數過濾結果
      MultipartUploadListing result = s3.listMultipartUploads(request);
      for (MultipartUpload s : result.getMultipartUploads()) {
        System.out.println(s.getKey());
        System.out.println(s.getUploadId());
      }
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

  private static void ListMPUs(String bucketName) throws IOException {
    String fileName1 = "photos/2006/January/sample.jpg";
    String fileName2 = "photos/2006/February/sample.jpg";
    String fileName3 = "photos/2006/March/sample.jpg";
    String fileName4 = "videos/2006/March/sample.wmv";
    String fileName5 = "sample.jpg";
    int count = 0;

    InitiateMultipartUploadRequest config1 =
        new InitiateMultipartUploadRequest(bucketName, fileName1);
    InitiateMultipartUploadRequest config2 =
        new InitiateMultipartUploadRequest(bucketName, fileName2);
    InitiateMultipartUploadRequest config3 =
        new InitiateMultipartUploadRequest(bucketName, fileName3);
    InitiateMultipartUploadRequest config4 =
        new InitiateMultipartUploadRequest(bucketName, fileName4);
    InitiateMultipartUploadRequest config5 =
        new InitiateMultipartUploadRequest(bucketName, fileName5);

    AmazonS3 s3 = S3Client.getClient();

    try {
      // 創建Bucket
      System.out.println("Creating bucket" + "\n");
      s3.createBucket(bucketName);

      // 初始化5個MPU
      System.out.println("basic initial MPU");
      InitiateMultipartUploadResult initRequest1 = s3.initiateMultipartUpload(config1);
      InitiateMultipartUploadResult initRequest2 = s3.initiateMultipartUpload(config2);
      InitiateMultipartUploadResult initRequest3 = s3.initiateMultipartUpload(config3);
      InitiateMultipartUploadResult initRequest4 = s3.initiateMultipartUpload(config4);
      InitiateMultipartUploadResult initRequest5 = s3.initiateMultipartUpload(config5);
      // 紀錄Upload ID
      String UploadID1 = initRequest1.getUploadId();
      String UploadID2 = initRequest2.getUploadId();
      String UploadID3 = initRequest3.getUploadId();
      String UploadID4 = initRequest4.getUploadId();
      String UploadID5 = initRequest5.getUploadId();

      // 列出Bucket中所有MPU，搭配Delimiter & Prefix過濾結果
      PrefixListMPUs(bucketName, "/", "photos/2006/"); // Delimiter & prefix

      // 列出Bucket中所有MPU，搭配Key Marker & Max Upload過濾結果
      MaxListMPUs(bucketName, "photos/2006/January/sample.jpg", 2);

      // 列出Bucket中所有MPU，搭配Upload ID Marker過濾結果
      MarkerListMPUs(bucketName, UploadID1);

      System.out.println("Tear down..");
      // 中斷MPU
      AbortMultipartUploadRequest abort1 =
          new AbortMultipartUploadRequest(bucketName, fileName1, UploadID1);
      AbortMultipartUploadRequest abort2 =
          new AbortMultipartUploadRequest(bucketName, fileName2, UploadID2);
      AbortMultipartUploadRequest abort3 =
          new AbortMultipartUploadRequest(bucketName, fileName3, UploadID3);
      AbortMultipartUploadRequest abort4 =
          new AbortMultipartUploadRequest(bucketName, fileName4, UploadID4);
      AbortMultipartUploadRequest abort5 =
          new AbortMultipartUploadRequest(bucketName, fileName5, UploadID5);
      s3.abortMultipartUpload(abort1);
      s3.abortMultipartUpload(abort2);
      s3.abortMultipartUpload(abort3);
      s3.abortMultipartUpload(abort4);
      s3.abortMultipartUpload(abort5);
      // 刪除Object
      s3.deleteObject(bucketName, fileName1);
      s3.deleteObject(bucketName, fileName2);
      s3.deleteObject(bucketName, fileName3);
      s3.deleteObject(bucketName, fileName4);
      s3.deleteObject(bucketName, fileName5);
      // 刪除Bucket
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
     * test 1. PutBucket 2. Initial MPU with metadata + CannedACL + StorageClass 3. UploadParts 4.
     * basic ListParts
     */
    AbortMPU(args[0], args[2], args[5]);

    /*
     * test 1. PutBucket 2. Initial MPU with metadata + CannedACL + StorageClass + ACL 3.
     * UploadParts 4. basic ListParts + Parameters: maxPart & partNumberMarker 5. Complete MPU
     */
    CompleteMPU(args[0], args[2], args[5]);

    /*
     * test 1. PutBucket 2. Initial MPU 3. UploadPartCopy & parameters 4. Abort MPU
     */
    CopyPart(args[0], args[1], args[2], args[3], args[5]);

    /*
     * test 1. PutBucket 2. Initial MPU 3. List MPUs & parameters 4. Abort MPU
     */
    ListMPUs(args[0]);

    System.gc(); // grabage collection

  }

}
