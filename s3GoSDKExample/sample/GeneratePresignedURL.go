package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func GeneratePresignedURL(buckets [3]string, filePaths [3]string) {
	input := &s3.GetObjectInput{
		Bucket: aws.String(buckets[0]),
		Key:    aws.String(filePaths[0]),
	}
	psClient := s3.NewPresignClient(Client)
	resp, err := psClient.PresignGetObject(context.TODO(), input)
	if err != nil {
		fmt.Println("Got an error retrieving pre-signed object:")
		fmt.Println(err)
		return
	}
	fmt.Println("Get " + filePaths[0] + " URL: ")
	fmt.Println(resp.URL)
	fmt.Println("")
}
