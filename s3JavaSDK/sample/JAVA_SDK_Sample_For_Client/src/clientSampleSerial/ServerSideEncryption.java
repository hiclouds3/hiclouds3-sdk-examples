package clientSampleSerial;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class ServerSideEncryption{
    
	private static InputStream ReadFile(String filePath) throws IOException {
    	File file = new File(filePath);
    
        FileInputStream fin = new FileInputStream(file);
        return fin;
    }
	
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
    
	private static void SSEPutObject(String bucketName, String objectName, String filePath) throws IOException
	{
		System.out.println("SSE PutObject...");
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(ServerSideEncryption.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Create bucket " + bucketName + "\n");
			s3.createBucket(bucketName);
			
			System.out.println("Uploading a object to S3 with Server Side Encryption\n");
			ObjectMetadata metadata = new ObjectMetadata() ;
            metadata.setServerSideEncryption("AES256");
            s3.putObject(new PutObjectRequest(bucketName, objectName, ReadFile(filePath),metadata));
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
	
	private static void SSEGetObject(String bucketName, String objectName, String filePath) throws IOException
	{
		System.out.println("SSE GetObject...");
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(ServerSideEncryption.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Getting Object");
            S3Object object = s3.getObject(bucketName,objectName);
            System.out.println("ObjectName: "  + object.getKey());
            System.out.println("ETag: "  + object.getObjectMetadata().getETag());
            System.out.println("ServerSideEncryption: "  + object.getObjectMetadata().getServerSideEncryption());
            //displayTextInputStream(object.getObjectContent()); //Optional
            System.out.println();
            
            //tear down
            System.out.println("Tear down");
            s3.deleteObject(bucketName, objectName);
            s3.deleteBucket(bucketName);
            System.out.println("END");
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
		
		SSEPutObject(args[0], args[2], args[5]);
		SSEGetObject(args[0], args[2], args[6]);
	}
}