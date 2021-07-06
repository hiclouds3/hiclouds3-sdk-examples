package clientSampleSerial;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.ListPartsRequest;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.amazonaws.services.s3.model.VersionListing;
import com.amazonaws.services.s3.transfer.TransferManager;


public class UploadBigFileByMPU{
	
	private static void autoUploadBigFileByPart(String bucketName, String fileName, String filePath) throws IOException
	{
		List<PartETag> partETags = new ArrayList<PartETag>();

		
		InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, fileName);

		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(UploadBigFileByMPU.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
		    //創建Bucket
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            //此範例示範情境：使用者有一個大檔欲採用MPU上傳至hicloud S3
            //此程式自行分隔大檔為每part為5MB的part檔並分別上傳至hicloud S3，最後使用CompleteMPU將part檔組合為原始大檔
            //欲自行分割part檔並分別上傳至hicloud S3，請參考MPUSerial.java
            System.out.println("basic initial MPU");
            InitiateMultipartUploadResult initResponse = s3.initiateMultipartUpload(initRequest);     
			String UploadID = initResponse.getUploadId();
			
			//自動切割大檔為Part檔並使用UploadPart上傳至hicloud S3
			System.out.println("Upload Part");
			
			File file = new File(filePath);
			long contentLength = file.length();
			long partSize = 5 * 1024 * 1024; // Set part size to 5 MB.
		    long filePosition = 0;
		    
		    for (int i = 1; filePosition < contentLength; i++) {
		        // Last part can be less than 5 MB. Adjust part size.
		    	partSize = Math.min(partSize, (contentLength - filePosition));
		    	
		        // Create request to upload a part.
		        UploadPartRequest uploadRequest = new UploadPartRequest()
		            .withBucketName(bucketName).withKey(fileName)
		            .withUploadId(UploadID).withPartNumber(i)
		            .withFileOffset(filePosition)
		            .withFile(file)
		            .withPartSize(partSize);

		        // Upload part and add response to our list.
		        partETags.add(s3.uploadPart(uploadRequest).getPartETag());
		        filePosition += partSize;
	        }

			
			
			
			//Complete MPU
			System.out.println("basic Complete MPU");
			CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, fileName, UploadID, partETags);
			s3.completeMultipartUpload(compRequest);
			
			//Check File
			System.out.println("\nCheck file, file info:");
			
			ObjectMetadata object = s3.getObjectMetadata(bucketName,fileName);
            System.out.println("Content-Length: "  + object.getContentLength());
            System.out.println("raw-metadata: "  + object.getRawMetadata());
            System.out.println("ETag: "  + object.getETag());          
            System.out.println();
			
			System.out.println("Tear down..");
			//Delete Object
			s3.deleteObject(bucketName, fileName);
			//Delete Bucket
	        s3.deleteBucket(bucketName);
	        System.out.println("DONE"); 
		}
		catch (AmazonServiceException ase) {
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
	

	
	public static void main(String args[]) throws IOException
	{
		System.out.println("hello world");
		
		/* 
		 * test 1. PutBucket
		 *      2. Initial MPU
		 *      3. UploadParts 
		 *      4. Complete MPU 
		 */
		autoUploadBigFileByPart(args[0], args[2], args[5]);

		System.gc(); //grabage collection	
	}
		
}
