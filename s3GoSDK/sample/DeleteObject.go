package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func DeleteObject(buckets [3]string, filePaths [3]string) {
	for _, bucket := range buckets {
		input := &s3.ListObjectsInput{
			Bucket: &bucket,
		}
		obj, listobjectsErr := Client.ListObjects(context.TODO(), input)
		if listobjectsErr != nil {
			fmt.Println("Could not list object " + bucket)
		}
		for _, object := range obj.Contents {
			output := &s3.DeleteObjectInput{
				Bucket: &bucket,
				Key:    object.Key,
			}
			_, deleteobjectErr := Client.DeleteObject(context.TODO(), output)
			if deleteobjectErr != nil {
				fmt.Println("Got an error deleting item:")
				fmt.Println(deleteobjectErr)
				return
			}
			fmt.Println("Deleted " + *object.Key + " from " + bucket)
		}
	}
	fmt.Println("")
}
