package clients

import (
	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/credentials"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)
client := s3.New(s3.Options{
	Region :            "ap-southeast-1",
	Credentials :       aws.NewCredentialsCache(credentials.NewStaticCredentialsProvider("IgZvbove5gZJHfzX8BUyUC0irS2ENDZ", "3u22C1gb0p1P0Nwg2iQSausvihuSBClcH2F1BCovdaSuKdAZH9FgBlf0Fq1Gs1Etu")),
})