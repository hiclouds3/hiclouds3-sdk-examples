package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func ListBucket() {
	input := &s3.ListBucketsInput{}
	result, listbucketErr := Client.ListBuckets(context.TODO(), input)
	if listbucketErr != nil {
		fmt.Println("Got an error retrieving buckets:")
		fmt.Println(listbucketErr)
		return
	}

	fmt.Println("List Buckets:")
	for _, bucket := range result.Buckets {
		fmt.Println(*bucket.Name + ": " + bucket.CreationDate.Format("2006-01-02 15:04:05 Monday"))
	}
	fmt.Println("")
}
