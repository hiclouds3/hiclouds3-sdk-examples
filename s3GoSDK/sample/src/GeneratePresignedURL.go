package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func GeneratePresignedURL(arg [3]string, filePath [3]string) {
	input := &s3.GetObjectInput{
		Bucket: aws.String(arg[0]),
		Key:    aws.String(filePath[0]),
	}
	psClient := s3.NewPresignClient(Client)
	resp, err := psClient.PresignGetObject(context.TODO(), input)
	if err != nil {
		fmt.Println("Got an error retrieving pre-signed object:")
		fmt.Println(err)
		return
	}
	fmt.Println("The URL: ")
	fmt.Println(resp.URL)
}
