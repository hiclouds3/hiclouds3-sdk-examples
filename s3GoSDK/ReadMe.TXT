## Sample Code

## Environment setup
* Go >= 1.15

## Go SDK Serial Test Read me
* 在「sample/Client.go」填入AccessKey, SecretKey資訊
* 在「sample/GoSerialTest.go」設定bucketName/filePath等資訊
* 執行「sample/GoSerialTest.go」將會將所有範例程式執行過一遍
	* cd sample/
	* go mod init mys3test
	* go get
	* go run *.go
