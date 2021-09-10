def object_serial_testing(s3, buckets)
  # create a bucket
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  # from an IO object
  File.open(File.expand_path("hinet1.jpg", ""), "rb") do |file|
    resp = s3.put_object({
      body: file,
      bucket: buckets[0],
      key: "hinet1.jpg",
      acl: "public-read",
    })
  end

  # from a string
  resp = s3.put_object({
    body: "Hello World!",
    bucket: buckets[0],
    key: "hello",
  })

  resp = s3.get_object(bucket: buckets[0], key: "hello")
  puts resp.body.read

  File.open("download1.jpg", "wb") do |file|
    reap = s3.get_object({ bucket: buckets[0], key: "hinet1.jpg" }, target: file)
  end

  # delete multiple objects from a bucket using a single HTTP request
  resp = s3.delete_objects({
    bucket: buckets[0], # required
    delete: { # required
      objects: [ # required
        {
          key: "hello",
        },
        {
          key: "hinet1.jpg",
        },
      ],
      quiet: true,
    },
  })

  # delete all buckets
  cleanup(s3, buckets)
end
