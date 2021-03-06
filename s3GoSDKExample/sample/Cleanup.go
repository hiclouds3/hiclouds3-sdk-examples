package main

import (
	"context"
	"errors"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/aws/smithy-go"

)

func CleanUp(buckets [3]string) {
	for _, bucket := range buckets {
		input2 := &s3.ListObjectsInput{
			Bucket: &bucket,
		}
		obj, listobjectsErr := Client.ListObjects(context.TODO(), input2)
		if listobjectsErr != nil {
			fmt.Printf("Could not list object in " + bucket +": ")
			var ae smithy.APIError
			if errors.As(listobjectsErr, &ae) {
				fmt.Println(ae.ErrorCode())
			}
			continue
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
				continue
			}
			fmt.Println("Deleted " + *object.Key + " from " + bucket)
		}
		input3 := &s3.DeleteBucketInput{
			Bucket: &bucket,
		}
		_, deleteErr := Client.DeleteBucket(context.TODO(), input3)
		if deleteErr != nil {
			fmt.Println("Could not delete bucket " + bucket)
			fmt.Println(deleteErr)
			continue
		}
		fmt.Println("Delete bucket : " + bucket)
	}
	fmt.Println("")
}
