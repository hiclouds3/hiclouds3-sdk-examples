package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func ListBucket() {
	input := &s3.ListBucketsInput{}
	result, listbucket_err := Client.ListBuckets(context.TODO(), input)
	if listbucket_err != nil {
		fmt.Println("Got an error retrieving buckets:")
		fmt.Println(listbucket_err)
		return
	}

	fmt.Println("Buckets:")
	for _, bucket := range result.Buckets {
		fmt.Println(*bucket.Name + ": " + bucket.CreationDate.Format("2006-01-02 15:04:05 Monday"))
	}
}
