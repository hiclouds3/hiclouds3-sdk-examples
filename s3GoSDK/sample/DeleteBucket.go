package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func DeleteBucket(buckets [3]string) {
	for _, bucket := range buckets {
		fmt.Printf("%s\n", bucket)
		fmt.Println("-----------------------------------------------------------------------")
		input := &s3.DeleteBucketInput{
			Bucket: &bucket,
		}
		_, deleteErr := Client.DeleteBucket(context.TODO(), input)
		if deleteErr != nil {
			fmt.Println("Could not delete bucket " + bucket)
		}
	}
	fmt.Println("")
}
