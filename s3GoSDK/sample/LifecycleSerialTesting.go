package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/aws/aws-sdk-go-v2/service/s3/types"
)

var (
	day int32 = 1
)

func LifecycleSerialTest(buckets [3]string) {
	expirationInput := &s3.PutBucketLifecycleConfigurationInput{
		Bucket: &buckets[0],
		LifecycleConfiguration: &types.BucketLifecycleConfiguration{
			Rules: []types.LifecycleRule{
				{
					Expiration: &types.LifecycleExpiration{
						Days: day,
					},
					Prefix: aws.String("documents/"),
					ID:     aws.String("Test1"),
					Status: types.ExpirationStatusEnabled,
				},
			},
		},
	}
	_, putbucketlifecycleExpirationErr := Client.PutBucketLifecycleConfiguration(context.TODO(), expirationInput)
	if putbucketlifecycleExpirationErr != nil {
		fmt.Println("Got an error PutBucketLifecycle Expiration item:")
		fmt.Println(putbucketlifecycleExpirationErr)
		return
	}
	transitionsInput := &s3.PutBucketLifecycleConfigurationInput{
		Bucket: &buckets[1],
		LifecycleConfiguration: &types.BucketLifecycleConfiguration{
			Rules: []types.LifecycleRule{
				{
					Prefix: aws.String("documents/"),
					ID:     aws.String("Test2"),
					Status: types.ExpirationStatusEnabled,
					Transitions: []types.Transition{
						{
							Days:         day,
							StorageClass: types.TransitionStorageClassGlacier,
						},
					},
				},
			},
		},
	}
	_, putbucketlifecycleTransitionsErr := Client.PutBucketLifecycleConfiguration(context.TODO(), transitionsInput)
	if putbucketlifecycleTransitionsErr != nil {
		fmt.Println("Got an error PutBucketlifecycle Transitions item:")
		fmt.Println(putbucketlifecycleTransitionsErr)
		return
	}
	expirationOutput := &s3.GetBucketLifecycleConfigurationInput{
		Bucket: &buckets[0],
	}
	resultExpiration, getbucketlifecycleExpirationErr := Client.GetBucketLifecycleConfiguration(context.TODO(), expirationOutput)
	if getbucketlifecycleExpirationErr != nil {
		fmt.Println("Got an error GetBucketlifecycle_Expiration item:")
		fmt.Println(getbucketlifecycleExpirationErr)
		return
	}
	transitionsOutput := &s3.GetBucketLifecycleConfigurationInput{
		Bucket: &buckets[1],
	}
	resultTransitions, getbucketlifecycleTransitionsErr := Client.GetBucketLifecycleConfiguration(context.TODO(), transitionsOutput)
	if getbucketlifecycleTransitionsErr != nil {
		fmt.Println("Got an error GetBucketlifecycle_Transitions item:")
		fmt.Println(getbucketlifecycleTransitionsErr)
		return
	}
	for _, dataExpiration := range resultExpiration.Rules {
		fmt.Println("Get", buckets[0], "Lifecycle: ")
		fmt.Println("  Status: ", dataExpiration.Status)
		fmt.Println("  ID: ", *dataExpiration.ID)
		fmt.Println("  Prefix: ", *dataExpiration.Prefix)
		fmt.Println("  Expiration:")
		fmt.Println("    Day:", dataExpiration.Expiration.Days)
		fmt.Println("")
	}
	for _, dataTransitions := range resultTransitions.Rules {
		fmt.Println("Get", buckets[1], "Lifecycle: ")
		fmt.Println("  Status: ", dataTransitions.Status)
		fmt.Println("  ID: ", *dataTransitions.ID)
		fmt.Println("  Prefix: ", *dataTransitions.Prefix)
		fmt.Println("  Transitions:")
		fmt.Println("    Days: ", dataTransitions.Transitions[0].Days)
		fmt.Println("    StorageClass: ", dataTransitions.Transitions[0].StorageClass)
		fmt.Println("")
	}
}
