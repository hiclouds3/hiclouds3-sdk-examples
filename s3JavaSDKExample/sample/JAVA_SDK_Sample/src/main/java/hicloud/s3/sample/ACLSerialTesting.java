package hicloud.s3.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;
import com.amazonaws.services.s3.model.VersionListing;

public class ACLSerialTesting {

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

  private static void basicPutBucketACL(String bucketName) throws IOException {
    AmazonS3 s3 = S3Client.getClient();
    Properties prop = new Properties();
    prop.load(ACLSerialTesting.class.getClassLoader().getResourceAsStream("config.properties"));

    AccessControlList acl = new AccessControlList();
    Owner owner = new Owner();
    owner.setDisplayName(prop.getProperty("ownerDisplayName"));
    owner.setId(prop.getProperty("ownerUserID")); // User ID

    try {
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      acl.setOwner(owner);
      acl.grantPermission(new CanonicalGrantee(prop.getProperty("ownerCanonicalID")),
          Permission.FullControl); // Canonical ID
      acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
      acl.grantPermission(new EmailAddressGrantee(prop.getProperty("userAEmail")),
          Permission.WriteAcp); // e-mail

      System.out.println("put bucket ACL");
      s3.setBucketAcl(bucketName, acl);

      System.out.println("put bucket ACL with Canned ACL");
      s3.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);

      System.out.println("Tear down..");
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

  private static void vPutObjectACL(String bucketName, String fileName) throws IOException {
    Properties prop = new Properties();
    prop.load(ACLSerialTesting.class.getClassLoader().getResourceAsStream("config.properties"));

    AccessControlList acl = new AccessControlList();

    Owner owner = new Owner();
    owner.setDisplayName(prop.getProperty("ownerDisplayName"));
    owner.setId(prop.getProperty("ownerCanonicalID")); // Canonical ID

    AmazonS3 s3 = S3Client.getClient();

    try {
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      // Enable versioning
      System.out.println("basic put bucket versioning");
      BucketVersioningConfiguration config = new BucketVersioningConfiguration();
      config.setStatus("Enabled");
      SetBucketVersioningConfigurationRequest version =
          new SetBucketVersioningConfigurationRequest(bucketName, config);
      s3.setBucketVersioningConfiguration(version);

      System.out.println("Uploading a new object to S3 from a file\n");
      PutObjectResult object =
          s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
      String vid = object.getVersionId();

      acl.setOwner(owner);
      acl.grantPermission(new CanonicalGrantee(prop.getProperty("userACanonicalID")),
          Permission.ReadAcp); // Canonical ID
      acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
      acl.grantPermission(new EmailAddressGrantee(prop.getProperty("userBEmail")),
          Permission.WriteAcp);
      acl.grantPermission(new CanonicalGrantee(prop.getProperty("ownerCanonicalID")),
          Permission.FullControl); // Canonical ID
      // set Object ACL
      System.out.println("Put object ACL...\n");
      s3.setObjectAcl(bucketName, fileName, vid, acl);

      // set Canned ACL for the object with specific version
      System.out.println("Put object ACL with canned acl + version id \n");
      s3.setObjectAcl(bucketName, fileName, vid, CannedAccessControlList.PublicReadWrite);


      System.out.println("Tear down..");
      // list all versions of the objects in the bucket
      VersionListing clear = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName));
      for (S3VersionSummary s : clear.getVersionSummaries()) {
        System.out.println(s.getBucketName());
        System.out.println(s.getKey());
        System.out.println(s.getVersionId());
        // delete the specific version of a object
        s3.deleteVersion(s.getBucketName(), s.getKey(), s.getVersionId());
      }
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


  private static void basicPutObjectACL(String bucketName, String fileName) throws IOException {
    Properties prop = new Properties();
    prop.load(ACLSerialTesting.class.getClassLoader().getResourceAsStream("config.properties"));

    AccessControlList acl = new AccessControlList();

    Owner owner = new Owner();
    owner.setDisplayName(prop.getProperty("ownerDisplayName"));
    owner.setId(prop.getProperty("ownerCanonicalID")); // Canonical ID


    AmazonS3 s3 = S3Client.getClient();

    try {
      System.out.println("Creating bucket " + bucketName + "\n");
      s3.createBucket(bucketName);

      System.out.println("Uploading a new object to S3 from a file\n");
      s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));

      acl.setOwner(owner);
      acl.grantPermission(new CanonicalGrantee(prop.getProperty("userACanonicalID")),
          Permission.ReadAcp); // Canonical ID
      acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
      acl.grantPermission(new EmailAddressGrantee(prop.getProperty("userBEmail")),
          Permission.WriteAcp);
      acl.grantPermission(new CanonicalGrantee(prop.getProperty("ownerCanonicalID")),
          Permission.FullControl); // Canonical ID

      System.out.println("Put object ACL...\n");
      s3.setObjectAcl(bucketName, fileName, acl);


      System.out.println("Put object ACL with canned acl...\n");
      s3.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicReadWrite);

      System.out.println("Tear down..");

      s3.deleteObject(bucketName, fileName);

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

  public static void main(String args[]) throws IOException {
    System.out.println("hello world");

    /*
     * test 1. PutBucketACL with user defined ACL 2. PutBucketACL with canned ACL 3. GetBucketACL
     */
    basicPutBucketACL(args[0]);

    /*
     * test 1. PutObjectACL with user defined ACL 2. PutObjectACL with canned ACL 3. GetObjectACL
     */
    basicPutObjectACL(args[0], args[1]);

    /*
     * test 1. PutObjectACL with user defined ACL with vid 2. PutObjectACL with canned ACL with vid
     * 3. GetObjectACL with vid
     */
    vPutObjectACL(args[0], args[1]);

  }

}
