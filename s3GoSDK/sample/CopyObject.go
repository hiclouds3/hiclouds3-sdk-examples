package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func CopyObject(buckets [3]string, filePaths [3]string) {
	source := buckets[0] + "/" + filePaths[0]
	input := &s3.CopyObjectInput{
		Bucket:     &buckets[1],
		CopySource: &source,
		Key:        &filePaths[1],
	}
	_, copyobject_err := Client.CopyObject(context.TODO(), input)
	if copyobject_err != nil {
		fmt.Println("Got an error copying item:")
		fmt.Println(copyobject_err)
		return
	}
}
