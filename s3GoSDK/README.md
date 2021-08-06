# hicloud S3 Go SDK

## Environment setup
* Go >= 1.15

## Go SDK Serial Test Read me
* Fill in AccessKey, SecretKey in「sample/Client.go」
	```go
	var cfg, _ = config.LoadDefaultConfig(
		config.WithCredentialsProvider(credentials.NewStaticCredentialsProvider("Enter your AccessKey", "Enter your SecretKey", "")),
	)
	```
* Fill in user ID in「sample/BucketLoggingSerialTesting.go」
	```go
	Grantee: &types.Grantee{
		ID:   aws.String("Enter your user ID"),
		Type: "CanonicalUser",
	},
	```
* Set bucketName and filePath information in「sample/GoSerialTest.go」
	```go
	buckets := [3]string{"Enter your bucket name1", "Enter your bucket name2", "Enter your bucket name3",}
	filePath := [3]string{"enter your filePath name1", "enter your filePath name2", "enter your filePath name3"}
	```
* If you run「sample/GoSerialTest.go」,all the sample programs will be run once
	```sh
	$ cd sample/
	$ go mod init mys3test
	$ go get
	$ go run *.go
	```
