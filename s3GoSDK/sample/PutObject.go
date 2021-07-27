package main

import (
	"context"
	"fmt"
	"os"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func PutObject(buckets [3]string, filePaths [3]string) {
	file, err := os.Open(filePaths[0])
	if err != nil {
		fmt.Println("Unable to open file " + filePaths[0])
		return
	}
	defer file.Close()
	input := &s3.PutObjectInput{
		ACL:    "private",
		Bucket: aws.String(buckets[0]),
		Key:    aws.String(filePaths[0]),
		Body:   file,
	}
	_, putobject_err := Client.PutObject(context.TODO(), input)
	if putobject_err != nil {
		fmt.Println("Got error uploading file:")
		fmt.Println(putobject_err)
		return
	}
}
