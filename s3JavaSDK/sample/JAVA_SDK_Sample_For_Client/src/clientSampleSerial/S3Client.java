package clientSampleSerial;

import java.io.IOException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.model.EncryptionMaterials;

public class S3Client {
  public static AmazonS3 getClient() throws IOException {
    AWSCredentials credentials =
        new PropertiesCredentials(S3Client.class.getResourceAsStream("AwsCredentials.properties"));
    ClientConfiguration clientConfiguration = new ClientConfiguration();
//    clientConfiguration.setProxyHost("---your-proxy-host---");
//    clientConfiguration.setProxyPort(8080);

    AmazonS3 s3 = new AmazonS3Client(credentials, clientConfiguration);
    s3.setEndpoint("s3.hicloud.net.tw");

    return s3;
  }

  public static AmazonS3 getEncryptionClient(EncryptionMaterials encryptionMaterials)
      throws IOException {
    AWSCredentials credentials =
        new PropertiesCredentials(S3Client.class.getResourceAsStream("AwsCredentials.properties"));
    ClientConfiguration clientConfiguration = new ClientConfiguration();
//    clientConfiguration.setProxyHost("---your-proxy-host---");
//    clientConfiguration.setProxyPort(8080);

    AmazonS3 s3 = new AmazonS3EncryptionClient(credentials, encryptionMaterials);
    s3.setEndpoint("s3.hicloud.net.tw");

    return s3;
  }
}
