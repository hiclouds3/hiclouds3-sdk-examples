package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func CleanUp() {
	input1 := &s3.ListBucketsInput{}
	result, listbucketErr := Client.ListBuckets(context.TODO(), input1)
	if listbucketErr != nil {
		fmt.Println("Got an error retrieving buckets:")
		fmt.Println(listbucketErr)
		return
	}

	for _, bucket := range result.Buckets {
		input2 := &s3.ListObjectsInput{
			Bucket: bucket.Name,
		}
		obj, listobjectsErr := Client.ListObjects(context.TODO(), input2)
		if listobjectsErr != nil {
			fmt.Println("Could not list object " + *bucket.Name)
		}
		for _, object := range obj.Contents {
			output := &s3.DeleteObjectInput{
				Bucket: bucket.Name,
				Key:    object.Key,
			}
			_, deleteobjectErr := Client.DeleteObject(context.TODO(), output)
			if deleteobjectErr != nil {
				fmt.Println("Got an error deleting item:")
				fmt.Println(deleteobjectErr)
				return
			}
			fmt.Println("Deleted " + *object.Key + " from " + *bucket.Name)
		}
		input3 := &s3.DeleteBucketInput{
			Bucket: bucket.Name,
		}
		_, deleteErr := Client.DeleteBucket(context.TODO(), input3)
		if deleteErr != nil {
			fmt.Println("Could not delete bucket " + *bucket.Name)
			return
		}
		fmt.Println("Delete bucket : " + *bucket.Name)
	}
	fmt.Println("")
}
