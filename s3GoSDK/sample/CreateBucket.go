package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func CreateBucket(buckets [3]string) {
	for _, bucket := range buckets {
		fmt.Printf("%s\n", bucket)
		fmt.Println("-----------------------------------------------------------------------")
		input := &s3.CreateBucketInput{
			ACL:    "log-delivery-write",
			Bucket: &bucket,
		}
		_, createErr := Client.CreateBucket(context.TODO(), input)
		if createErr != nil {
			fmt.Println("Could not create bucket " + bucket)
		}
	}
	fmt.Println("")
}
