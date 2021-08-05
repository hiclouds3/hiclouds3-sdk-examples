package main

import (
	"context"
	"fmt"
	"os"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/aws/aws-sdk-go-v2/service/s3/types"
)

func BucketVersioningTest(buckets [3]string, filePaths [3]string) {
	for _, bucket := range buckets {
		input := &s3.CreateBucketInput{
			ACL:    "log-delivery-write",
			Bucket: &bucket,
		}
		_, create_err := Client.CreateBucket(context.TODO(), input)
		if create_err != nil {
			fmt.Println("Could not create bucket " + bucket)
		}
	}
	file1, err1 := os.Open(filePaths[0])
	if err1 != nil {
		fmt.Println("Unable to open file " + filePaths[0])
		return
	}
	file2, err2 := os.Open(filePaths[1])
	if err2 != nil {
		fmt.Println("Unable to open file " + filePaths[1])
		return
	}
	file3, err3 := os.Open(filePaths[2])
	if err3 != nil {
		fmt.Println("Unable to open file " + filePaths[2])
		return
	}
	enabledInput := &s3.PutBucketVersioningInput{
		Bucket: &buckets[0],
		VersioningConfiguration: &types.VersioningConfiguration{
			MFADelete: types.MFADeleteDisabled,
			Status:    types.BucketVersioningStatusEnabled,
		},
	}
	_, putbucketversioningEnabledErr := Client.PutBucketVersioning(context.TODO(), enabledInput)
	if putbucketversioningEnabledErr != nil {
		fmt.Println("Got an error PutBucketVersioning item:")
		fmt.Println(putbucketversioningEnabledErr)
		return
	}
	objectInput1 := &s3.PutObjectInput{
		Bucket: &buckets[0],
		Key:    aws.String("HelloWorld"),
		Body:   file1,
	}
	_, putobjectErr1 := Client.PutObject(context.TODO(), objectInput1)
	if putobjectErr1 != nil {
		fmt.Println("Got error uploading file:")
		fmt.Println(putobjectErr1)
		return
	}
	objectInput2 := &s3.PutObjectInput{
		Bucket: &buckets[0],
		Key:    aws.String("HelloWorld"),
		Body:   file2,
	}
	_, putobjectErr2 := Client.PutObject(context.TODO(), objectInput2)
	if putobjectErr2 != nil {
		fmt.Println("Got error uploading file:")
		fmt.Println(putobjectErr2)
		return
	}
	objectInput3 := &s3.PutObjectInput{
		Bucket: &buckets[0],
		Key:    aws.String("HelloWorld"),
		Body:   file3,
	}
	_, putobjectErr3 := Client.PutObject(context.TODO(), objectInput3)
	if putobjectErr3 != nil {
		fmt.Println("Got error uploading file:")
		fmt.Println(putobjectErr3)
		return
	}
	suspendedInput := &s3.PutBucketVersioningInput{
		Bucket: &buckets[0],
		VersioningConfiguration: &types.VersioningConfiguration{
			MFADelete: types.MFADeleteDisabled,
			Status:    types.BucketVersioningStatusSuspended,
		},
	}
	_, putbucketversioningSuspendedErr := Client.PutBucketVersioning(context.TODO(), suspendedInput)
	if putbucketversioningSuspendedErr != nil {
		fmt.Println("Got an error PutBucketVersioning item:")
		fmt.Println(putbucketversioningSuspendedErr)
		return
	}
	output := &s3.ListObjectVersionsInput{
		Bucket: &buckets[0],
	}
	version, getbucketversioningErr := Client.ListObjectVersions(context.TODO(), output)
	if getbucketversioningErr != nil {
		fmt.Println("Got an error GetBucketVersioning item:")
		fmt.Println(getbucketversioningErr)
		return
	}
	for _, ver := range version.Versions {
		output := &s3.DeleteObjectInput{
			Bucket:    &buckets[0],
			Key:       aws.String("HelloWorld"),
			VersionId: ver.VersionId,
		}
		_, deleteobjectErr := Client.DeleteObject(context.TODO(), output)
		if deleteobjectErr != nil {
			fmt.Println("Got an error deleting item:")
			fmt.Println(deleteobjectErr)
			return
		}
	}
	for _, bucket := range buckets {
		input := &s3.DeleteBucketInput{
			Bucket: &bucket,
		}
		_, delete_err := Client.DeleteBucket(context.TODO(), input)
		if delete_err != nil {
			fmt.Println("Could not delete bucket " + bucket)
		}
	}
}
