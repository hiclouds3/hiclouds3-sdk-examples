package main

import (
	"context"
	"fmt"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func GetBucketAcl(arg [3]string) {
	input := &s3.GetBucketAclInput{
		Bucket: aws.String(arg[0]),
	}
	result, err := Client.GetBucketAcl(context.TODO(), input)
	if err != nil {
		fmt.Println("Got an error retrieving ACL for " + arg[0])
		return
	}
	fmt.Println("")
	fmt.Println("Get " + arg[0] + " ACL:")
	fmt.Println("Owner:", *result.Owner.DisplayName)
	fmt.Println("Grants")
	for _, g := range result.Grants {
		// If we add a canned ACL, the name is nil
		if g.Grantee.DisplayName == nil {
			fmt.Println("  Grantee:    EVERYONE")
		} else {
			fmt.Println("  Grantee:   ", *g.Grantee.DisplayName)
		}
		fmt.Println("  Type:      ", string(g.Grantee.Type))
		fmt.Println("  Permission:", string(g.Permission))
		fmt.Println("")
	}
}
