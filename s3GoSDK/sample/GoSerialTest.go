package main

import (
	"fmt"
)

func main() {
	buckets := [3]string{
		"Enter your bucket name1", "Enter your bucket name2", "Enter your bucket name3",
	}
	filePath := [3]string{"castle.jpg.001", "castle.jpg.002", "castle.jpg.003"}
	fmt.Printf("S3 GO SDK Serial Test-\nbucketname1: %s  bucketname2: %s\n", buckets[0], buckets[1])
	fmt.Println("-----------------------------------------------------------------------")
	CreateBucket(buckets)
	ListBucket()
	BucketLoggingTest(buckets)
	GetBucketAcl(buckets)
	GeneratePresignedURL(buckets, filePath)
	LifecycleSerialTest(buckets)
	PutObject(buckets, filePath)
	CopyObject(buckets, filePath)
	ListObjects(buckets)
	GetObjectAcl(buckets, filePath)
	PolicySerialTest(buckets)
	DeleteObject(buckets, filePath)
	DeleteBucket(buckets)
	BucketVersionTest(buckets, filePath)
}
