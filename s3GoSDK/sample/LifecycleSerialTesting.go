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
	ExpirationInput := &s3.PutBucketLifecycleConfigurationInput{
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
	_, PutBucketlifecycle_Expiration_err := Client.PutBucketLifecycleConfiguration(context.TODO(), ExpirationInput)
	if PutBucketlifecycle_Expiration_err != nil {
		fmt.Println("Got an error PutBucketlifecycle_Expiration item:")
		fmt.Println(PutBucketlifecycle_Expiration_err)
		return
	}
	TransitionsInput := &s3.PutBucketLifecycleConfigurationInput{
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
	_, PutBucketlifecycle_Transitions_err := Client.PutBucketLifecycleConfiguration(context.TODO(), TransitionsInput)
	if PutBucketlifecycle_Transitions_err != nil {
		fmt.Println("Got an error PutBucketlifecycle_Transitions item:")
		fmt.Println(PutBucketlifecycle_Transitions_err)
		return
	}
	ExpirationOutput := &s3.GetBucketLifecycleConfigurationInput{
		Bucket: &buckets[0],
	}
	result_expiration, GetBucketlifecycle_Expiration_err := Client.GetBucketLifecycleConfiguration(context.TODO(), ExpirationOutput)
	if GetBucketlifecycle_Expiration_err != nil {
		fmt.Println("Got an error GetBucketlifecycle_Expiration item:")
		fmt.Println(GetBucketlifecycle_Expiration_err)
		return
	}
	TransitionsOutput := &s3.GetBucketLifecycleConfigurationInput{
		Bucket: &buckets[1],
	}
	result_transitions, GetBucketlifecycle_Transitions_err := Client.GetBucketLifecycleConfiguration(context.TODO(), TransitionsOutput)
	if GetBucketlifecycle_Transitions_err != nil {
		fmt.Println("Got an error GetBucketlifecycle_Transitions item:")
		fmt.Println(GetBucketlifecycle_Transitions_err)
		return
	}
	for _, data_expiration := range result_expiration.Rules {
		fmt.Println("Get", buckets[0], "Lifecycle: ")
		fmt.Println("  Status: ", data_expiration.Status)
		fmt.Println("  ID: ", *data_expiration.ID)
		fmt.Println("  Prefix: ", *data_expiration.Prefix)
		fmt.Println("  Expiration:")
		fmt.Println("    Day:", data_expiration.Expiration.Days)
		fmt.Println("")
	}
	for _, data_transitions := range result_transitions.Rules {
		fmt.Println("Get", buckets[1], "Lifecycle: ")
		fmt.Println("  Status: ", data_transitions.Status)
		fmt.Println("  ID: ", *data_transitions.ID)
		fmt.Println("  Prefix: ", *data_transitions.Prefix)
		fmt.Println("  Transitions:")
		fmt.Println("    Days: ", data_transitions.Transitions[0].Days)
		fmt.Println("    StorageClass: ", data_transitions.Transitions[0].StorageClass)
		fmt.Println("")
	}
}
