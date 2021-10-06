# hicloud S3 Go SDK

## Environment setup
* Go >= 1.15

## Go SDK Serial Test Read me
* Fill in AccessKey, SecretKey in「sample/Client.go」
	```go
	var cfg, _ = config.LoadDefaultConfig(
		config.WithCredentialsProvider(
			credentials.NewStaticCredentialsProvider("Enter_your_AccessKey", "Enter_your_SecretKey", "")
		),
	)
	```
* Fill in user ID in「sample/BucketLoggingSerialTesting.go」
	```go
	Grantee: &types.Grantee{
		ID:   aws.String("Enter_your_canonical_ID"),
		Type: "CanonicalUser",
	},
	```
* If you run「sample/GoSerialTest.go」,all the sample programs will be run once
	```sh
	$ cd sample/
	$ go mod init mys3test
	$ go get
	$ go run *.go
	```
## Additional Resource
* [開發指南](documentation/hicloudS3-go-sdk-開發指南.pdf)
* [Developer Guide](documentation/hicloudS3-go-sdk-DeveloperGuide.pdf)