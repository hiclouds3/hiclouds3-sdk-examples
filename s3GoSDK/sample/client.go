package clients

import (
	"context"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/credentials"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func main() {
	customResolver := aws.EndpointResolverFunc(func(service, region string) (aws.Endpoint, error) {
		return aws.Endpoint{
			URL: "http://s3.hicloud.net.tw",
			//HostnameImmutable: true,
		}, nil
	})

	cfg, err := config.LoadDefaultConfig(context.TODO(), config.WithEndpointResolver(customResolver))

	print(err)
	Client := s3.NewFromConfig(cfg, func(o *s3.Options) {
		o.Region = "us-west-2"
		o.Credentials = aws.NewCredentialsCache(credentials.NewStaticCredentialsProvider("", "", ""))
	})

}
