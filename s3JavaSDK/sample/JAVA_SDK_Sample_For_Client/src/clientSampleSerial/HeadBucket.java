package clientSampleSerial;


import java.io.IOException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class HeadBucket{
	
	private static void BasicHeadBucket(String bucketName) throws IOException
	{
		System.out.println("basic head bucket");
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(HeadBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Heading bucket " + bucketName + "\n");
	        boolean result = s3.doesBucketExist(bucketName);
	        if(result){
	        	System.out.println("bucket exists and you have permission to access it");
	        }else{
	        	System.out.println("bucket does not exists or you don't have permission to access it");
	        }

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
		BasicHeadBucket(args[0]);
	}
}