package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func Cleanup(arg [3]string) {
	for _, bucket := range arg {
		fmt.Printf("%s\n", bucket)
		fmt.Println("-----------------------------------------------------------------------")
		input := &s3.CreateBucketInput{
			Bucket: &bucket,
		}
		result, create_err := Client.CreateBucket(context.TODO(), input)
		if create_err != nil {
			fmt.Println("Could not create bucket " + bucket)
			fmt.Println(create_err)
		}
		fmt.Println(result)
		delete := &s3.DeleteBucketInput{
			Bucket: &bucket,
		}
		result1, delete_err := Client.DeleteBucket(context.TODO(), delete)
		if delete_err != nil {
			fmt.Println("Could not delete bucket " + bucket)
		}
		fmt.Println(result1)
	}
}
