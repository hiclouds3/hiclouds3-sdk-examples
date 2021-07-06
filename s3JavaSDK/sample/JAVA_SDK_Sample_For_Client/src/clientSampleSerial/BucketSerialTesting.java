package clientSampleSerial;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;


public class BucketSerialTesting{
	
	//準備一個測試檔給上傳object使用
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
    
    private static void RegionPutBucket(String bucketName, String bucketNameS) throws IOException
    {
    	System.out.println("put bucket with region..\n");    	
	
		String region="EU";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(BucketSerialTesting.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
		    //Create Bucket A
			System.out.println("Creating bucket " + bucketNameS + "\n");
            s3.createBucket(bucketNameS,region);
            
			//Create Bucket B
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName,region);
            
			//列出使用者所有的Bucket
	        System.out.println("Listing buckets");
	        for (Bucket bucket : s3.listBuckets()) {
	            System.out.println(" - " + bucket.getName());
	        }
	        
			System.out.println("Tear down..");
			//Delete Bucket A
	        s3.deleteBucket(bucketNameS);
			//Delete Bucket B
	        s3.deleteBucket(bucketName);
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
 
    private static void ACLPutBucket(String bucketName, String bucketName2, String fileName, String fileName2, String fileName3) throws IOException
    {
    	System.out.println("put bucket with canned acl");

		CannedAccessControlList acl = null;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(BucketSerialTesting.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket...\n");
			//Create Bucket
            s3.createBucket(new CreateBucketRequest(bucketName).withCannedAcl(acl.PublicRead));
            Bucket bucket = s3.createBucket(bucketName2);
            
			//分別上傳三個 Object
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName2, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName3, createSampleFile()));
            
			//列出 Bucket 中所有的 object (預設為前 1000 筆，若需印出更多的 object 須搭配其他參數使用)
            System.out.println("Listing object from bucket\n");
            ObjectListing objectListing = s3.listObjects(bucketName);
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")"); 
            }

			System.out.println("Tear down..");
			//刪除上傳的 Object
	        s3.deleteObject(bucketName, fileName);
	        s3.deleteObject(bucketName, fileName2);
	        s3.deleteObject(bucketName, fileName3);
			//刪除 Bucket
	        s3.deleteBucket(bucketName);
	        s3.deleteBucket(bucketName2);
	        
            System.out.println();
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
    
    private static void ParameterPutBucket(String bucketName) throws IOException
    {
		System.out.println("Get bucket with Parameters");

		String fileName="apple.jpg";
		String fileName2="photos/2006/January/sample.jpg";
		String fileName3="photos/2006/February/sample2.jpg";
		String fileName4="asset.txt";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(BucketSerialTesting.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
		    //Create Bucket
			System.out.println("Creating bucket " + bucketName + "\n");
	        s3.createBucket(bucketName);
	        
            //分別上傳四個 Object		
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName2, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName3, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName4, createSampleFile()));
            
            //列出 Bucket中 object(由 prefix 參數決定列出開頭為  "photos/" 的檔案列表)
            System.out.println("Listing object from bucket (prefix)\n");
            ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix("photos/"));                      
 
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +"(size = " + objectSummary.getSize() + ")");
            }

            //列出 Bucket中 object(由 prefix 參數決定列出開頭為  "photos/" 的檔案列表)
	        ObjectListing objectListing2 = s3.listObjects(bucketName,"photos/");
            for (S3ObjectSummary objectSummary : objectListing2.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +"(size = " + objectSummary.getSize() + ")");
            }	        
	        
			//列出 Bucket中 object(使用 delimeter 參數的情境)
            System.out.println("Listing object from bucket (delimeter)\n"); 
            objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withDelimiter("/"));  
            
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }
	        
	        //列出 Bucket中 object(使用 delimeter & prefix 參數的情境)
            System.out.println("Listing object from bucket (delimeter & prefix)\n");
            objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withDelimiter("/").withPrefix("photos/2006/"));           
            
			for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }
	        
			//列出 Bucket中 object(使用 MaxKey 參數的情境)
            System.out.println("Listing object from bucket (max key)\n"); 
            objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withMaxKeys(2));           
            
			for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }

	        //列出 Bucket中 object(使用 marker 參數的情境)
            System.out.println("Listing object from bucket (marker)\n"); //test marker
            objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withMarker(fileName));           
           
		   for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }
            
			System.out.println("Tear down..");
			//Delete Object
	        s3.deleteObject(bucketName, fileName);
	        s3.deleteObject(bucketName, fileName2);
	        s3.deleteObject(bucketName, fileName3);
	        s3.deleteObject(bucketName, fileName4);
			//Delete Bucket
	        s3.deleteBucket(bucketName);      
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
		 * test 1. bucket with region(type Region & String) 
		 *      2. Get Service
		 *      3. Delete Bucket
		 */
		RegionPutBucket(args[0], args[1]); 
		
		/*
		 * test 1. bucket with Canned ACL & normal put bucket
		 *      2. Normal GetBucket
		 */
		ACLPutBucket(args[0], args[1], args[2], args[3], args[4]);
		
		/*
		 * test 1. GetBucket with parameters
		 */
		ParameterPutBucket(args[0]);
		
	}
		
}
