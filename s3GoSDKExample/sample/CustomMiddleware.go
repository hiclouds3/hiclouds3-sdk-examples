package main

import (
	"context"
	"fmt"
	"strings"

	"github.com/aws/smithy-go/middleware"
	smithyhttp "github.com/aws/smithy-go/transport/http"
)

type customRequestHeaderMiddleware struct {
	request *smithyhttp.Request
}

func (*customRequestHeaderMiddleware) ID() string { return "s3:customRequestHeaderMiddleware" }

func (rm *customRequestHeaderMiddleware) HandleBuild(
	ctx context.Context, in middleware.BuildInput, next middleware.BuildHandler,
) (
	out middleware.BuildOutput, metadata middleware.Metadata, err error,
) {
	req, ok := in.Request.(*smithyhttp.Request)

	// Un-canonicalize X-Amz-* headers
	// ex. Modify "X-Amz-Copy-Source" to "x-amz-copy-source"
	headers := req.Header.Clone()
	for key, _ := range headers {
		if strings.HasPrefix(key, "X-Amz-") {
			req.Header.Del(key)
			keyLowerCase := strings.ToLower(key)
			req.Header[keyLowerCase] = append(req.Header[keyLowerCase], headers.Get(key))
		}
	}
	if !ok {
		return out, metadata, fmt.Errorf("unknown request type %T", req)
	}
	rm.request = req
	return next.HandleBuild(ctx, in)
}

func CustomizeRequestHeaders() func(stack *middleware.Stack) error {
	fm := customRequestHeaderMiddleware{}
	return func(stack *middleware.Stack) error {
		stack.Build.Add(&fm, middleware.After)
		return nil
	}
}
