package main

import (
	"context"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/credentials"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

var customResolver = aws.EndpointResolverFunc(func(service, region string) (aws.Endpoint, error) {
	return aws.Endpoint{
		URL:           "http://s3.hicloud.net.tw",
		SigningName:   "s3",
		SigningRegion: "us-east-1",
	}, nil
})
var cfg, _ = config.LoadDefaultConfig(
	context.TODO(),
	config.WithRegion("us-east-1"),
	config.WithEndpointResolver(customResolver),
	config.WithCredentialsProvider(
		credentials.NewStaticCredentialsProvider("Enter_your_AccessKey", "Enter_your_SecretKey", ""),
	),
)
var Client = s3.NewFromConfig(cfg)
