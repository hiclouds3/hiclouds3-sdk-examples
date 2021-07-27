package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func GetObjectAcl(buckets [3]string, filePaths [3]string) {
	input := &s3.GetObjectAclInput{
		Bucket: aws.String(buckets[0]),
		Key:    aws.String(filePaths[0]),
	}
	result, err := Client.GetObjectAcl(context.TODO(), input)
	if err != nil {
		fmt.Println("Got an error getting ACL for " + filePaths[0])
		return
	}
	fmt.Println("")
	fmt.Println("Get " + filePaths[0] + " ACL:")
	fmt.Println("Owner:", *result.Owner.DisplayName)
	fmt.Println("Grants")

	for _, g := range result.Grants {
		fmt.Println("  Grantee:   ", *g.Grantee.DisplayName)
		fmt.Println("  Type:      ", string(g.Grantee.Type))
		fmt.Println("  Permission:", string(g.Permission))
		fmt.Println("")
	}
}
