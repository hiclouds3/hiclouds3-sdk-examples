package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func PolicySerialTest(buckets [3]string) {
	input := &s3.PutBucketPolicyInput{
		Bucket: &buckets[0],
		Policy: aws.String("{\"Version\": \"2012-10-17\", \"Statement\": [{ \"Sid\": \"DenyPublicREAD\",\"Effect\": \"Deny\",\"Principal\": {\"AWS\": \"*\"}, \"Action\": \"s3:GetObject\", \"Resource\": [\"arn:aws:s3:::" + buckets[0] + "/*\" ] } ]}"),
	}

	_, putbucketpolicyErr := Client.PutBucketPolicy(context.TODO(), input)
	if putbucketpolicyErr != nil {
		fmt.Println("Got an error PutPolicy item:")
		fmt.Println(putbucketpolicyErr)
		return
	}
	output := &s3.GetBucketPolicyInput{
		Bucket: &buckets[0],
	}
	result, getbucketpolicyErr := Client.GetBucketPolicy(context.TODO(), output)
	if getbucketpolicyErr != nil {
		fmt.Println("Got an error GetPolicy item:")
		fmt.Println(getbucketpolicyErr)
		return
	}
	fmt.Println("Get " + buckets[0] + " Policy: \n" + *result.Policy)
	fmt.Println("")
}
