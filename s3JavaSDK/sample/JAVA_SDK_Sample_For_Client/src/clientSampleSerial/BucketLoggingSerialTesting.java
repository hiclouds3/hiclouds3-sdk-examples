package clientSampleSerial;


import java.io.IOException;
import java.util.Properties;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;


public class BucketLoggingSerialTesting{
	
	private static void basicPutBucketandACL(String bucketName, String bucketName2) throws IOException
	{	    	
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(BucketLoggingSerialTesting.class.getResourceAsStream("AwsCredentials.properties")));
		Properties prop = new Properties();  
		prop.load(ACLSerialTesting.class.getResourceAsStream("config.properties"));  
		
		//創建將開啟Log功能的Bucket
		System.out.println("Creating source bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
		
		//創建Log存放的Bucket
		System.out.println("Creating target bucket " + bucketName2 + "\n");
	    s3.createBucket(bucketName2);
		AccessControlList acl = new AccessControlList();
		Owner owner = new Owner();
		owner.setDisplayName(prop.getProperty("ownerDisplayName"));
		owner.setId(prop.getProperty("ownerUserID"));
		
		try
		{
			acl.setOwner(owner);
			acl.grantPermission(GroupGrantee.LogDelivery, Permission.ReadAcp);
			acl.grantPermission(GroupGrantee.LogDelivery, Permission.Write);
			acl.grantPermission(new CanonicalGrantee(prop.getProperty("ownerCanonicalID")), Permission.FullControl);
			//設定Bucket ACL
			s3.setBucketAcl(bucketName2, acl);          
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
	
    private static void basicPutBucketLogging(String bucketName, String bucketName2) throws IOException
    {
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(BucketLoggingSerialTesting.class.getResourceAsStream("AwsCredentials.properties")));
		       
		//Log Config設定	   
        BucketLoggingConfiguration config = new BucketLoggingConfiguration();
        config.setDestinationBucketName(bucketName2);
		config.setLogFilePrefix("chttest-log-");
		
		
		try
		{
			System.out.println("basic put bucket logging");
			//開啟Bucket Log功能
			SetBucketLoggingConfigurationRequest request = new SetBucketLoggingConfigurationRequest(bucketName,config);
			s3.setBucketLoggingConfiguration(request);
	        System.out.println();			
	        
			//取得Bucket Log設定
			System.out.println("basic get bucket logging");
			config = s3.getBucketLoggingConfiguration(bucketName);  
	        System.out.println(config.toString());
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
 
    
    private static void basicDisableBucketLogging(String bucketName, String bucketName2) throws IOException
    {
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(BucketLoggingSerialTesting.class.getResourceAsStream("AwsCredentials.properties")));
		       
        BucketLoggingConfiguration config = new BucketLoggingConfiguration();
        config.setDestinationBucketName(null);
        config.setLogFilePrefix(null);
		
		try
		{
		    //設定Bucket Logging狀態為Disable
			System.out.println("basic disable bucket logging");
			SetBucketLoggingConfigurationRequest request = new SetBucketLoggingConfigurationRequest(bucketName,config);
			s3.setBucketLoggingConfiguration(request);
	        System.out.println();			
	        
			//取得Bucket Logging狀態
			System.out.println("basic get bucket logging");
			System.out.println(config.toString());
	        System.out.println();
	        
	        s3.deleteBucket(bucketName);
	        s3.deleteBucket(bucketName2); 
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
		 * set target bucket acl
		 */
		basicPutBucketandACL(args[0],args[1]); 
		
		/*
		 * test 1. PutBucketLogging 
		 *      2. GetBucketLogging  
		 */
		basicPutBucketLogging(args[0],args[1]);
		
		/*
		 * test 1. DisableBucketLogging
		 * 		2. GetBucketLogging
		 */
		basicDisableBucketLogging(args[0], args[1]);
		
	}
		
}
