package hicloud.s3.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
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



  private static void basicUploadPart(String bucketName, String fileName, String uploadID,
      int partNumber, String resourceName) throws IOException {
    System.out.println("basic Upload Part");

    InputStream bis = Utils.getSampleFileIS(resourceName);

    UploadPartRequest config = new UploadPartRequest().withBucketName(bucketName).withKey(fileName)
        .withPartNumber(partNumber).withUploadId(uploadID).withPartSize(6270544)
        .withInputStream(bis);

    AmazonS3 s3 = S3Client.getClient();
    try {
      // UploadPart
      UploadPartResult result = s3.uploadPart(config);
      System.out.println(result);
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

  private static int MaxListParts(String bucketName, String fileName, String uploadID, int MaxPart)
      throws IOException {
    System.out.println("MaxListParts");

    ListPartsRequest request =
        new ListPartsRequest(bucketName, fileName, uploadID).withMaxParts(MaxPart);
    int count = 0;

    AmazonS3 s3 = S3Client.getClient();
    try {
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
      // List all uploaded parts
      PartListing result = s3.listParts(request);
      for (PartSummary s : result.getParts()) {
        counter++;
        list.add(new PartETag(counter, s.getETag()));
      }

      // CompleteMPU
      CompleteMultipartUploadRequest config =
          new CompleteMultipartUploadRequest(bucketName, fileName, uploadID, list);
      CompleteMultipartUploadResult completeResult = s3.completeMultipartUpload(config);
      System.out.println("CompleteMultipartUploadResult: " + completeResult);
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

  private static void AbortMPU(String bucketName, String fileName, String resourceName)
      throws IOException {
    ObjectMetadata meta = new ObjectMetadata();
    meta.setHeader("x-amz-color", "red");

    InitiateMultipartUploadRequest config =
        new InitiateMultipartUploadRequest(bucketName, fileName, meta)
            .withCannedACL(CannedAccessControlList.AuthenticatedRead)
            .withStorageClass(StorageClass.Standard);

    AmazonS3 s3 = S3Client.getClient();

    try {
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      // initiate MPU
      System.out.println("basic initial MPU");
      InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);
      String UploadID = initRequest.getUploadId();

      // UploadPart
      System.out.println("Upload Part -1");
      basicUploadPart(bucketName, fileName, UploadID, 1, resourceName);
      // UploadPart
      System.out.println("Upload Part -2");
      basicUploadPart(bucketName, fileName, UploadID, 2, resourceName);

      // Abort MPU
      System.out.println("basic abort MPU");
      AbortMultipartUploadRequest abort =
          new AbortMultipartUploadRequest(bucketName, fileName, UploadID);
      s3.abortMultipartUpload(abort);

      System.out.println("Tear down..");
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

  private static void CompleteMPU(String bucketName, String fileName, String resourceName)
      throws IOException {
    ObjectMetadata meta = new ObjectMetadata();
    meta.setHeader("x-amz-color", "red");

    InitiateMultipartUploadRequest config =
        new InitiateMultipartUploadRequest(bucketName, fileName, meta)
            .withCannedACL(CannedAccessControlList.AuthenticatedRead).withObjectMetadata(meta);

    AmazonS3 s3 = S3Client.getClient();

    try {
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      // Upload 3 file fragments to be joined into 1 single MPU, we use the same file here for demo
      // purpose.
      // To upload a single big file directly, see UploadBigFileByMPU.
      System.out.println("basic initial MPU");
      InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);
      String UploadID = initRequest.getUploadId();

      // UploadPart
      System.out.println("Upload Part -1");
      basicUploadPart(bucketName, fileName, UploadID, 1, resourceName);

      // UploadPart
      System.out.println("Upload Part -2");
      basicUploadPart(bucketName, fileName, UploadID, 2, resourceName);

      // UploadPart
      System.out.println("Upload Part -3");
      basicUploadPart(bucketName, fileName, UploadID, 3, resourceName);

      MaxListParts(bucketName, fileName, UploadID, 2);

      MarkerListParts(bucketName, fileName, UploadID, 2);

      // Complete MPU
      System.out.println("basic Complete MPU");
      basicCompleteMPU(bucketName, fileName, UploadID);

      System.out.println("Tear down..");

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

  private static void basicUploadPartCopy(String sbucketName, String dbucketName, String sfileName,
      String dfileName, String uploadID, int count) throws IOException // with If-Match &
                                                                       // If-Modify-Since
  {
    List<String> list = new ArrayList<String>(); // etag
    list.add("339bbff383f4a3a129037a2029f62bf5");
    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(Calendar.YEAR) - 10, cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));


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
      request.setModifiedSinceConstraint(cal.getTime());

      // Upload Part Copy
      CopyPartResult result = s3.copyPart(request);
      System.out.println("CopyPartResult: " + result);
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
    List<String> list = new ArrayList<String>(); // etag
    list.add("339bbff383f4a3a129037a2029f62bf4"); // wrong etag

    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 2, cal.get(Calendar.DATE));

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
      request.setUnmodifiedSinceConstraint(cal.getTime());
      // Upload Part Copy
      CopyPartResult result = s3.copyPart(request);
      System.out.println("CopyPartResult: " + result);
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
      String sfileName, String resourceName) throws IOException {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(6270544);

    InitiateMultipartUploadRequest config =
        new InitiateMultipartUploadRequest(bucketName, fileName);

    AmazonS3 s3 = S3Client.getClient();

    try {
      System.out.println("Creating bucket" + "\n");
      s3.createBucket(bucketName);
      s3.createBucket(sbucketName);

      InputStream bis = Utils.getSampleFileIS(resourceName);
      System.out.println("Put source Object" + "\n");
      PutObjectResult object =
          s3.putObject(new PutObjectRequest(sbucketName, sfileName, bis, metadata));
      System.out.println(object.getETag());

      System.out.println("basic initial MPU");
      InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);
      String UploadID = initRequest.getUploadId();

      basicUploadPartCopy(sbucketName, bucketName, sfileName, fileName, UploadID, 1);
      NonUploadPartCopy(sbucketName, bucketName, sfileName, fileName, UploadID, 2);

      // abort MPU upload
      AbortMultipartUploadRequest abort =
          new AbortMultipartUploadRequest(bucketName, fileName, UploadID);
      s3.abortMultipartUpload(abort);

      System.out.println("Tear down..");

      s3.deleteObject(bucketName, fileName);
      s3.deleteObject(sbucketName, sfileName);
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
      throws IOException // Demo usage of Prefix & Delimiter
  {
    System.out.println("basic list MPUs");

    ListMultipartUploadsRequest request =
        new ListMultipartUploadsRequest(bucketName).withPrefix(Prefix).withDelimiter(Delimiter);

    AmazonS3 s3 = S3Client.getClient();
    try {

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

      System.out.println("Creating bucket" + "\n");
      s3.createBucket(bucketName);

      System.out.println("basic initial MPU");
      InitiateMultipartUploadResult initRequest1 = s3.initiateMultipartUpload(config1);
      InitiateMultipartUploadResult initRequest2 = s3.initiateMultipartUpload(config2);
      InitiateMultipartUploadResult initRequest3 = s3.initiateMultipartUpload(config3);
      InitiateMultipartUploadResult initRequest4 = s3.initiateMultipartUpload(config4);
      InitiateMultipartUploadResult initRequest5 = s3.initiateMultipartUpload(config5);

      String UploadID1 = initRequest1.getUploadId();
      String UploadID2 = initRequest2.getUploadId();
      String UploadID3 = initRequest3.getUploadId();
      String UploadID4 = initRequest4.getUploadId();
      String UploadID5 = initRequest5.getUploadId();

      PrefixListMPUs(bucketName, "/", "photos/2006/"); // Delimiter & prefix

      MaxListMPUs(bucketName, "photos/2006/January/sample.jpg", 2);

      MarkerListMPUs(bucketName, UploadID1);

      System.out.println("Tear down..");

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

      s3.deleteObject(bucketName, fileName1);
      s3.deleteObject(bucketName, fileName2);
      s3.deleteObject(bucketName, fileName3);
      s3.deleteObject(bucketName, fileName4);
      s3.deleteObject(bucketName, fileName5);

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
