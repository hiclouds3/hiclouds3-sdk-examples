def bucket_serial_testing(s3, buckets)
  # create a bucket with specific acl
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[1], # required
  })

  # list buckets
  resp = s3.list_buckets()
  bucketname = resp.buckets.map(&:name)
  puts bucketname

  # basic put object
  resp = s3.put_object({
    body: "Hello World!",
    bucket: buckets[0],
    key: "hello",
  })

  #  delete multiple objects from a bucket using a single HTTP request
  resp = s3.delete_objects({
    bucket: buckets[0], # required
    delete: { # required
      objects: [ # required

        {
          key: "hello",
        },
      ],
      quiet: true,
    },
  })

  # delete specific bucket(need the bucket to have no object inside)
  resp = s3.delete_bucket({
    bucket: buckets[0], # required
  })

  cleanup(s3, buckets)
end
