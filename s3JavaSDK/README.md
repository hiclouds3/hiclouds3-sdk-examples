# hicloud S3 Java SDK

## Prerequisites
- Apache Maven 3+
- JDK 8

## Getting Started

### Setup
* Import the sample project into Eclipse (Optional)
  - File > Open Projects From File System... > Directory > select `sample/JAVA_SDK_Sample/`

* Fill in AccessKey, SecretKey in「AwsCredentials.properties」

* Fill in user information in「config.properties」

* Set bucketName, objectName, and filePath information in「SerialTest.java」

### Building the project

```sh
$ cd sample/JAVA_SDK_Sample/

$ mvn clean package
```

### Running
- S3Sample: Demo of simple S3 operations

    ```sh
    $ java -classpath ./target/s3sample-0.0.1-SNAPSHOT-jar-with-dependencies.jar hicloud.s3.sample.S3Sample
    ```

- SerialTest

    ```sh
    $ java -classpath ./target/s3sample-0.0.1-SNAPSHOT-jar-with-dependencies.jar hicloud.s3.sample.SerialTest
    ```

## Additional Resource
* [開發指南](documentation/hicloudS3-java-sdk-開發指南.pdf)
* [Developer Guide](documentation/hicloudS3-java-sdk-DeveloperGuide.pdf)
