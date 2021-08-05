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
	EnabledInput := &s3.PutBucketVersioningInput{
		Bucket: &buckets[0],
		VersioningConfiguration: &types.VersioningConfiguration{
			MFADelete: types.MFADeleteDisabled,
			Status:    types.BucketVersioningStatusEnabled,
		},
	}
	_, PutBucketVersioning_Enabled_err := Client.PutBucketVersioning(context.TODO(), EnabledInput)
	if PutBucketVersioning_Enabled_err != nil {
		fmt.Println("Got an error PutBucketVersioning item:")
		fmt.Println(PutBucketVersioning_Enabled_err)
		return
	}
	ObjectInput1 := &s3.PutObjectInput{
		Bucket: &buckets[0],
		Key:    aws.String("HelloWorld"),
		Body:   file1,
	}
	_, putobject_err1 := Client.PutObject(context.TODO(), ObjectInput1)
	if putobject_err1 != nil {
		fmt.Println("Got error uploading file:")
		fmt.Println(putobject_err1)
		return
	}
	ObjectInput2 := &s3.PutObjectInput{
		Bucket: &buckets[0],
		Key:    aws.String("HelloWorld"),
		Body:   file2,
	}
	_, putobject_err2 := Client.PutObject(context.TODO(), ObjectInput2)
	if putobject_err2 != nil {
		fmt.Println("Got error uploading file:")
		fmt.Println(putobject_err2)
		return
	}
	ObjectInput3 := &s3.PutObjectInput{
		Bucket: &buckets[0],
		Key:    aws.String("HelloWorld"),
		Body:   file3,
	}
	_, putobject_err3 := Client.PutObject(context.TODO(), ObjectInput3)
	if putobject_err3 != nil {
		fmt.Println("Got error uploading file:")
		fmt.Println(putobject_err3)
		return
	}
	SuspendedInput := &s3.PutBucketVersioningInput{
		Bucket: &buckets[0],
		VersioningConfiguration: &types.VersioningConfiguration{
			MFADelete: types.MFADeleteDisabled,
			Status:    types.BucketVersioningStatusSuspended,
		},
	}
	_, PutBucketVersioning_Suspended_err := Client.PutBucketVersioning(context.TODO(), SuspendedInput)
	if PutBucketVersioning_Suspended_err != nil {
		fmt.Println("Got an error PutBucketVersioning item:")
		fmt.Println(PutBucketVersioning_Suspended_err)
		return
	}
	output := &s3.ListObjectVersionsInput{
		Bucket: &buckets[0],
	}
	version, GetBucketVersioning_err := Client.ListObjectVersions(context.TODO(), output)
	if GetBucketVersioning_err != nil {
		fmt.Println("Got an error GetBucketVersioning item:")
		fmt.Println(GetBucketVersioning_err)
		return
	}
	for _, ver := range version.Versions {
		output := &s3.DeleteObjectInput{
			Bucket:    &buckets[0],
			Key:       aws.String("HelloWorld"),
			VersionId: ver.VersionId,
		}
		_, deleteobject_err := Client.DeleteObject(context.TODO(), output)
		if deleteobject_err != nil {
			fmt.Println("Got an error deleting item:")
			fmt.Println(deleteobject_err)
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
