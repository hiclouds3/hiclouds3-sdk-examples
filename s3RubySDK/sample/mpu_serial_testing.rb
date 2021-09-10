def mpu_serial_testing(s3, buckets)
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  #Initiates a multipart upload and returns an upload ID.
  resp = s3.create_multipart_upload({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read, bucket-owner-read, bucket-owner-full-control
    bucket: buckets[0], # required
    key: "ObjectKey", # required
    metadata: {
      "flower" => "lily",
    },
  })

  puts resp.bucket #=> String
  puts resp.key #=> String
  puts resp.upload_id #=> String

  uploadId = resp.upload_id

  resp = s3.upload_part({
    body: File.open("castle.jpg.001", "rb"), # file/IO object, or string data
    bucket: buckets[0], # required
    key: "ObjectKey", # required
    part_number: 1, # required
    upload_id: uploadId, # required
  })

  tag1 = resp.etag

  resp = s3.list_parts({
    bucket: buckets[0], # required
    key: "ObjectKey", # required
    upload_id: uploadId, # required
    request_payer: "requester", # accepts requester
  })

  puts "upload parts, list parts info"
  puts "In bucket " + resp.bucket
  print "part 1 info"
  print resp.parts[0].part_number #=> Integer
  print resp.parts[0].last_modified #=> Time
  print resp.parts[0].etag #=> String
  print resp.parts[0].size #=> Integer

  resp = s3.upload_part({
    body: File.open("castle.jpg.002", "rb"), # file/IO object, or string data
    bucket: buckets[0], # required
    key: "ObjectKey", # required
    part_number: 2, # required
    upload_id: uploadId, # required
  })
  tag2 = resp.etag

  resp = s3.upload_part({
    body: File.open("castle.jpg.003", "rb"), # file/IO object, or string data
    bucket: buckets[0], # required
    key: "ObjectKey", # required
    part_number: 3, # required
    upload_id: uploadId, # required
  })

  tag3 = resp.etag

  # Completes a multipart upload by assembling previously uploaded parts.
  resp = s3.complete_multipart_upload({
    bucket: buckets[0], # required
    key: "ObjectKey", # required
    multipart_upload: {
      parts: [
        {
          etag: tag1,
          part_number: 1,
        },
        {
          etag: tag2,
          part_number: 2,
        },
        {
          etag: tag3,
          part_number: 3,
        },
      ],
    },
    upload_id: uploadId, # required
  })

  # clean all buckets
  cleanup(s3, buckets)
end
