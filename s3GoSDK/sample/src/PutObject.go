package main

import (
	"context"
	"fmt"
	"os"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func PutObject(arg [3]string, filePath [3]string) {
	file, err := os.Open(filePath[0])
	if err != nil {
		fmt.Println("Unable to open file " + filePath[0])
		return
	}
	defer file.Close()
	input := &s3.PutObjectInput{
		ACL:    "private",
		Bucket: aws.String(arg[0]),
		Key:    aws.String(filePath[0]),
		Body:   file,
	}
	_, putobject_err := Client.PutObject(context.TODO(), input)
	if putobject_err != nil {
		fmt.Println("Got error uploading file:")
		fmt.Println(putobject_err)
		return
	}
}
