package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func ListObjects(buckets [3]string) {
	for _, bucket := range buckets {
		input := &s3.ListObjectsV2Input{
			Bucket: aws.String(bucket),
		}
		result, listobjectsErr := Client.ListObjectsV2(context.TODO(), input)
		if listobjectsErr != nil {
			fmt.Println("Got error retrieving list of objects:")
			fmt.Println(listobjectsErr)
			return
		}
		fmt.Println("Objects in " + bucket + ":")
		for _, item := range result.Contents {
			fmt.Println("Name:          ", *item.Key)
			fmt.Println("Last modified: ", *item.LastModified)
			fmt.Println("Size:          ", item.Size)
			fmt.Println("Storage class: ", item.StorageClass)
			fmt.Println("")
		}
		fmt.Println("Found", len(result.Contents), "items in bucket", bucket)
		fmt.Println("")
	}
}
