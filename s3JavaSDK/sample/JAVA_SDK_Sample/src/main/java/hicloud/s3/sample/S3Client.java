package hicloud.s3.sample;


import java.io.IOException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3Client {
  public static AmazonS3 getClient() throws IOException {
    AWSCredentials credentials = new PropertiesCredentials(
        S3Client.class.getClassLoader().getResourceAsStream("AwsCredentials.properties"));
    AWSStaticCredentialsProvider credentialsProvider =
        new AWSStaticCredentialsProvider(credentials);
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    // clientConfiguration.setSignerOverride("S3SignerType"); // Uncomment to use SigV2
    // clientConfiguration.setProxyHost("your proxy host");
    // clientConfiguration.setProxyPort(8000);


    AwsClientBuilder.EndpointConfiguration endpointConfiguration =
        new AwsClientBuilder.EndpointConfiguration("http://s3.hicloud.net.tw", "us-east-1");
    AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider)
        .withClientConfiguration(clientConfiguration).withChunkedEncodingDisabled(true)
        .withEndpointConfiguration(endpointConfiguration).build();

    return s3;
  }
}
