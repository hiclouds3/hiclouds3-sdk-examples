package main

import (
	"fmt"
)

func main() {
	buckets := [3]string{"testgolangbucket1", "testgolangbucket2", "testgolangbucket3"}
	filePath := [3]string{"castle.jpg.001", "castle.jpg.002", "castle.jpg.003"}
	fmt.Printf("S3 GO SDK Serial Test-\nbucketname1: %s  bucketname2: %s bucketname3: %s\n", buckets[0], buckets[1], buckets[2])
	fmt.Println("-----------------------------------------------------------------------")
	CleanUp(buckets)
	CreateBucket(buckets)
	ListBucket()
	GetBucketAcl(buckets)
	BucketLoggingTest(buckets)
	LifecycleSerialTest(buckets)
	PutObject(buckets, filePath)
	CopyObject(buckets, filePath)
	ListObjects(buckets)
	GeneratePresignedURL(buckets, filePath)
	GetObjectAcl(buckets, filePath)
	PolicySerialTest(buckets)
	DeleteObject(buckets, filePath)
	DeleteBucket(buckets)
	BucketVersioningTest(buckets, filePath)
}
