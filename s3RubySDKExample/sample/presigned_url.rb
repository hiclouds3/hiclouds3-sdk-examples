def presigned_url(s3, buckets)
  signer = Aws::S3::Presigner.new(client: s3)

  url = signer.presigned_url(:get_object, bucket: "testjsbucket1", key: "hello", secure: false)
  puts url
end
