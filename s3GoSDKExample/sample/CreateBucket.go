package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func CreateBucket(buckets [3]string) {
	for _, bucket := range buckets {
		fmt.Printf("Creating bucket: %s\n", bucket)

		input := &s3.CreateBucketInput{
			Bucket: &bucket,
		}
		_, createErr := Client.CreateBucket(context.TODO(), input)
		if createErr != nil {
			fmt.Println("Could not create bucket " + bucket)
		}
	}
	fmt.Println("")
}
