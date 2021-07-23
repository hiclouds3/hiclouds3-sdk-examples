package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func DeleteObject(arg [3]string, filePath [3]string) {
	input := &s3.DeleteObjectInput{
		Bucket: &arg[0],
		Key:    &filePath[0],
	}
	input2 := &s3.DeleteObjectInput{
		Bucket: &arg[1],
		Key:    &filePath[1],
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
	fmt.Println("Deleted " + filePath[0] + " from " + arg[0])
	fmt.Println("Deleted " + filePath[1] + " from " + arg[1])
}
