package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func DeleteBucket(arg [3]string) {
	for _, bucket := range arg {
		fmt.Printf("%s\n", bucket)
		fmt.Println("-----------------------------------------------------------------------")
		input := &s3.DeleteBucketInput{
			Bucket: &bucket,
		}

		result, delete_err := Client.DeleteBucket(context.TODO(), input)
		if delete_err != nil {
			fmt.Println("Could not delete bucket " + bucket)
			fmt.Println(delete_err)
		}
		fmt.Println(result)
	}
}
