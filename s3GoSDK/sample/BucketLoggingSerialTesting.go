package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/aws/aws-sdk-go-v2/service/s3/types"
)

func BucketLoggingTest(buckets [3]string) {
	input := &s3.PutBucketLoggingInput{
		Bucket: &buckets[0],
		BucketLoggingStatus: &types.BucketLoggingStatus{
			LoggingEnabled: &types.LoggingEnabled{
				TargetBucket: &buckets[1],
				TargetGrants: []types.TargetGrant{
					{
						Grantee: &types.Grantee{
							ID:   aws.String("tester20210713"),
							Type: "CanonicalUser",
						},
						Permission: "FULL_CONTROL",
					},
				},
				TargetPrefix: aws.String("MyBucketLogs/"),
			},
		},
	}
	_, PutBucketLogging_err := Client.PutBucketLogging(context.TODO(), input)
	if PutBucketLogging_err != nil {
		fmt.Println("Got an error PutBucketLogging item:")
		fmt.Println(PutBucketLogging_err)
		return
	}
	output := &s3.GetBucketLoggingInput{
		Bucket: &buckets[0],
	}
	result, GetBucketLogging_err := Client.GetBucketLogging(context.TODO(), output)
	if GetBucketLogging_err != nil {
		fmt.Println("Got an error PutBucketLogging item:")
		fmt.Println(GetBucketLogging_err)
		return
	}
	fmt.Println("Get BucketLogging:")
	fmt.Println("  TargetBucket :", *result.LoggingEnabled.TargetBucket)
	fmt.Println("  TargetPrefix :", *result.LoggingEnabled.TargetPrefix)
	fmt.Println("  TargetGrants :")
	fmt.Println("    Grantee :", *result.LoggingEnabled.TargetGrants[0].Grantee.DisplayName)
	fmt.Println("    Permission :", result.LoggingEnabled.TargetGrants[0].Permission)
	fmt.Println("")
}
