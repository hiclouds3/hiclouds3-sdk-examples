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
			ACL:    "public-read",
			Bucket: &bucket,
		}
		_, create_err := Client.CreateBucket(context.TODO(), input)
		if create_err != nil {
			fmt.Println("Could not create bucket " + bucket)
		}
	}
}
