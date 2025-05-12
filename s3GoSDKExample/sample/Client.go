package main

import (
	"context"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/credentials"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/aws/smithy-go/middleware"
	smithyhttp "github.com/aws/smithy-go/transport/http"
)

// withContentMD5 removes all flexible checksum procecdures from an operation,
// instead computing an MD5 checksum for the request payload.
// https://github.com/aws/aws-sdk-go-v2/discussions/2960
func withContentMD5(o *s3.Options) {
	o.APIOptions = append(o.APIOptions, func(stack *middleware.Stack) error {
		stack.Initialize.Remove("AWSChecksum:SetupInputContext")
		stack.Build.Remove("AWSChecksum:RequestMetricsTracking")
		stack.Finalize.Remove("AWSChecksum:ComputeInputPayloadChecksum")
		stack.Finalize.Remove("addInputChecksumTrailer")
		return smithyhttp.AddContentChecksumMiddleware(stack)
	})
}

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
