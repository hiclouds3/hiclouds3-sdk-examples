package main

import (
	"context"
	"fmt"

	//"net/url"
	"flag"

	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func CopyObject(arg [3]string, filePath [3]string) {
	//source := arg[0] + "/" + filePath[0]
	destinationBucket := flag.String("d", "", arg[0]+"/"+filePath[0])
	input := &s3.CopyObjectInput{
		Bucket:     &arg[1],
		CopySource: destinationBucket,
		//aws.String(url.PathEscape(source))
		Key: &filePath[1],
	}

	_, copyobject_err := Client.CopyObject(context.TODO(), input)
	if copyobject_err != nil {
		fmt.Println("Got an error copying item:")
		fmt.Println(copyobject_err)
		return
	}
}
