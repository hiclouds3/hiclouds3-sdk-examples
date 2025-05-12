package main

import (
	"fmt"
)

func main() {
	buckets := [3]string{"testgolangbucket1", "testgolangbucket2", "testgolangbucket3"}
	filePath := [3]string{"castle.jpg.001", "castle.jpg.002", "castle.jpg.003"}
	fmt.Printf("S3 GO SDK Serial Test-\nbucketname1: %s  bucketname2: %s bucketname3: %s\n", buckets[0], buckets[1], buckets[2])
	fmt.Println("-----------------------------------------------------------------------")

	fmt.Println("CleanUp...")
	CleanUp(buckets)

	fmt.Println("Running CreateBucket test...")
	CreateBucket(buckets)

	fmt.Println("Running ListBucket test...")
	ListBucket()

	fmt.Println("Running GetBucketAcl test...")
	GetBucketAcl(buckets)

	fmt.Println("Running BucketLoggingTest...")
	BucketLoggingTest(buckets)

	fmt.Println("Running LifecycleSerialTest...")
	LifecycleSerialTest(buckets)

	fmt.Println("Running PutObject test...")
	PutObject(buckets, filePath)

	fmt.Println("Running CopyObject test...")
	CopyObject(buckets, filePath)

	fmt.Println("Running ListObjects test...")
	ListObjects(buckets)

	fmt.Println("Running GetObjectAcl test...")
	GetObjectAcl(buckets, filePath)

	fmt.Println("Running PolicySerialTest...")
	PolicySerialTest(buckets)

	fmt.Println("Running DeleteObject test...")
	DeleteObject(buckets, filePath)

	fmt.Println("Running DeleteBucket test...")
	DeleteBucket(buckets)

	fmt.Println("Running BucketVersioningTest...")
	BucketVersioningTest(buckets, filePath)

	fmt.Println("S3 Go SDK Serial Test Done!")
}
