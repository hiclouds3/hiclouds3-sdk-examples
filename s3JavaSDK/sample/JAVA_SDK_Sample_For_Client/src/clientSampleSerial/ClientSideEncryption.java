package clientSampleSerial;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

/*
 * This example demonstrates how to create a key pair (a public key and a private key) and use it as
 * your asymmetric key for a client-side encrypted upload to hicloud S3. This code generates a key
 * pair, saves the key pair locally, loads the key pair, and uses the key pair to upload a test
 * file. In a typical use case, you would need to generate your key pair only once and then save it
 * in a secure location.
 * 
 * This example creates a key pair that uses the RSA algorithm and a key size of 1024 bits. You can
 * change the code to use a different algorithm and key size.
 * 
 * Note: If you get a cipher encryption error message when you use the encryption API for the first
 * time, then your version of the JDK may have a Java Cryptography Extension (JCE) jurisdiction
 * policy file that limits the maximum key length for encryption and decryption transformations to
 * 128 bits. To check your maximum key length, use the getMaxAllowedKeyLength method of the
 * javax.crypto.Cipher class. To remove the key length restriction, install the Java Cryptography
 * Extension (JCE) Unlimited Strength Jurisdiction Policy Files at the Java SE download page. (For
 * JAVA 7 the download from
 * http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html, Copy the two
 * downloaded jars in Java\jdk1.7.0_10\jre\lib\security. Take a backup of older jars to be on safer
 * side.)
 */

public class ClientSideEncryption {

  private static String encryptionAlgorithm = "RSA";
  private static String downloadDir = System.getProperty("user.dir"); // provide local directory
  private static String key = "keypairtest.txt";

  private static File createSampleFile() throws IOException {
    File file = File.createTempFile("encryptiontest", ".txt");
    file.deleteOnExit();

    Writer writer = new OutputStreamWriter(new FileOutputStream(file));
    writer.write(new java.util.Date().toString() + "\n");
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.write("01234567890112345678901234\n");
    writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
    writer.write("01234567890112345678901234\n");
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.close();

    return file;
  }

  public static void SaveS3ObjectContent(S3Object obj, String path, String filename)
      throws IOException {
    InputStream stream = obj.getObjectContent();
    FileOutputStream fos = new FileOutputStream(path + "\\" + filename);
    byte[] buffer = new byte[1024];
    int len;
    while ((len = stream.read(buffer)) != -1) {
      fos.write(buffer, 0, len);
    }
    fos.close();
  }

  public static void saveKeyPair(String path, KeyPair keyPair) throws IOException {
    PublicKey publicKey = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();

    // Save public key to file.
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
    FileOutputStream keyfos = new FileOutputStream(path + "\\public.key");
    keyfos.write(x509EncodedKeySpec.getEncoded());
    keyfos.close();

    // Save private key to file.
    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
    keyfos = new FileOutputStream(path + "\\private.key");
    keyfos.write(pkcs8EncodedKeySpec.getEncoded());
    keyfos.close();

  }

  public static KeyPair loadKeyPair(String path, String algorithm)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    // Read public key from file.
    FileInputStream keyfis = new FileInputStream(path + "\\public.key");
    byte[] encodedPublicKey = new byte[keyfis.available()];
    keyfis.read(encodedPublicKey);
    keyfis.close();

    // Read private key from file.
    keyfis = new FileInputStream(path + "\\private.key");
    byte[] encodedPrivateKey = new byte[keyfis.available()];
    keyfis.read(encodedPrivateKey);
    keyfis.close();

    // Generate KeyPair from public and private keys.
    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

    return new KeyPair(publicKey, privateKey);

  }


  public static void main(String args[])
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    System.out.println("hello world");

    // Specify values in KeyPairSampleEncryptAndUploadDataToS3.properties file.
    AmazonS3 s3 = S3Client.getClient();

    // Generate a key pair. In a typical scenario, you would just generate this once.
    KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(encryptionAlgorithm);
    keyGenerator.initialize(1024, new SecureRandom());
    KeyPair myKeyPair = keyGenerator.generateKeyPair();

    // Save key pair so we can use key later.
    saveKeyPair(downloadDir, myKeyPair);

    // Create encryption materials and instantiate client.
    EncryptionMaterials encryptionMaterials = new EncryptionMaterials(myKeyPair);
    AmazonS3 encryptedS3Client = S3Client.getEncryptionClient(encryptionMaterials);

    // Create Bucket and Upload a test file.
    System.out.println("Upload a file");
    s3.createBucket(args[0]);
    encryptedS3Client.putObject(new PutObjectRequest(args[0], key, createSampleFile()));

    // Load the test key from file to show that key was saved correctly.
    KeyPair myKeyPairRetrieved = loadKeyPair(downloadDir, encryptionAlgorithm);

    // Create new encryption materials using the retrieved key pair and a
    // new client based on the new materials.
    encryptionMaterials = new EncryptionMaterials(myKeyPairRetrieved);
    AmazonS3 encryptedS3Client2 = S3Client.getEncryptionClient(encryptionMaterials);

    // Download the object.
    // When you use the getObject method with the encrypted client,
    // the data retrieved from Amazon S3 is automatically decrypted on the fly.
    System.out.println("Download a file");
    S3Object downloadedObject = encryptedS3Client2.getObject(new GetObjectRequest(args[0], key));
    SaveS3ObjectContent(downloadedObject, downloadDir, key);

    s3.deleteObject(args[0], key);
    s3.deleteBucket(args[0]);

    System.out.println("END");
  }
}
