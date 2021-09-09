package hicloud.s3.sample;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import com.amazonaws.util.IOUtils;

public class Utils {

  public static InputStream getSampleFileIS(String resourceName) throws IOException {
    InputStream is = MPUSerialTesting.class.getClassLoader().getResourceAsStream(resourceName);
    return new ByteArrayInputStream(IOUtils.toByteArray(is));
  }
}
