package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/aws/aws-sdk-go-v2/service/s3/types"
)

func BucketLoggingTest(buckets [3]string) {
	acl := &s3.PutBucketAclInput{
		Bucket: &buckets[1],
		ACL:    "log-delivery-write",
	}
	_, putBucketAclErr := Client.PutBucketAcl(context.TODO(), acl)
	if putBucketAclErr != nil {
		fmt.Println("Got an error PutBucketAcl item:")
		fmt.Println(putBucketAclErr)
		return
	}
	input := &s3.PutBucketLoggingInput{
		Bucket: &buckets[0],
		BucketLoggingStatus: &types.BucketLoggingStatus{
			LoggingEnabled: &types.LoggingEnabled{
				TargetBucket: &buckets[1],
				TargetGrants: []types.TargetGrant{
					{
						Grantee: &types.Grantee{
							ID:   aws.String("Enter_your_canonical_ID"),
							Type: "CanonicalUser",
						},
						Permission: "FULL_CONTROL",
					},
				},
				TargetPrefix: aws.String("MyBucketLogs/"),
			},
		},
	}
	_, putbucketloggingErr := Client.PutBucketLogging(context.TODO(), input)
	if putbucketloggingErr != nil {
		fmt.Println("Got an error PutBucketLogging item:")
		fmt.Println(putbucketloggingErr)
		return
	}
	output := &s3.GetBucketLoggingInput{
		Bucket: &buckets[0],
	}
	result, getbucketloggingErr := Client.GetBucketLogging(context.TODO(), output)
	if getbucketloggingErr != nil {
		fmt.Println("Got an error PutBucketLogging item:")
		fmt.Println(getbucketloggingErr)
		return
	}
	fmt.Println("Get " + buckets[0] + " BucketLogging:")
	fmt.Println("  TargetBucket :", *result.LoggingEnabled.TargetBucket)
	fmt.Println("  TargetPrefix :", *result.LoggingEnabled.TargetPrefix)
	fmt.Println("  TargetGrants :")
	fmt.Println("    Grantee :", *result.LoggingEnabled.TargetGrants[0].Grantee.DisplayName)
	fmt.Println("    Permission :", result.LoggingEnabled.TargetGrants[0].Permission)
	fmt.Println("")
}
