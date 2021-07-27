package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func DeleteObject(buckets [3]string, filePaths [3]string) {
	input := &s3.DeleteObjectInput{
		Bucket: &buckets[0],
		Key:    &filePaths[0],
	}
	input2 := &s3.DeleteObjectInput{
		Bucket: &buckets[1],
		Key:    &filePaths[1],
	}
	_, deleteobject_err := Client.DeleteObject(context.TODO(), input)
	if deleteobject_err != nil {
		fmt.Println("Got an error deleting item:")
		fmt.Println(deleteobject_err)
		return
	}
	_, deleteobject_err2 := Client.DeleteObject(context.TODO(), input2)
	if deleteobject_err2 != nil {
		fmt.Println("Got an error deleting item:")
		fmt.Println(deleteobject_err2)
		return
	}
	fmt.Println("Deleted " + filePaths[0] + " from " + buckets[0])
	fmt.Println("Deleted " + filePaths[1] + " from " + buckets[1])
}
