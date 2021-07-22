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

	_, deleteobject_err := Client.DeleteObject(context.TODO(), input)
	if deleteobject_err != nil {
		fmt.Println("Got an error deleting item:")
		fmt.Println(deleteobject_err)
		return
	}
	fmt.Println("Deleted " + filePath[0] + " from " + arg[0])
}
