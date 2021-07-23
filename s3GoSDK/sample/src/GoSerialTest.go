package main

import (
	"fmt"
)

func main() {
	buckets := [3]string{
		"yuyuman1", "yuyuman2", "yuyuman3",
	}
	//userAInfo := [2]string{"","",}
	//userAInfo := [2]string{"","",}
	//ownerInfo := [2]string{"","",}
	filePath := [3]string{"castle.jpg.001", "castle.jpg.002", "castle.jpg.003"}
	fmt.Printf("S3 Python SDK Serial Test-\nbucketname1: %s  bucketname2: %s\n", buckets[0], buckets[1])
	fmt.Println("-----------------------------------------------------------------------")

	CreateBucket(buckets)
	ListBucket()
	GetBucketAcl(buckets)
	PutObject(buckets, filePath)
	CopyObject(buckets, filePath)
	ListObjects(buckets)
	GetObjectAcl(buckets, filePath)
	DeleteObject(buckets, filePath)
	DeleteBucket(buckets)
}
