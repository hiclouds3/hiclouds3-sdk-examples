def mpuserialtesting(s3, buckets, creds)
  cleanup
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
    create_bucket_configuration: {
      location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
    }
    })
  
  #Initiates a multipart upload and returns an upload ID.
  resp = s3.create_multipart_upload({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read, bucket-owner-read, bucket-owner-full-control
    bucket: buckets[0], # required
#    cache_control: "CacheControl",
#    content_disposition: "ContentDisposition",
#    content_encoding: "ContentEncoding",
#    content_language: "ContentLanguage",
#    content_type: "ContentType",
#    expires: "Thu, 01 Dec 2015 16:00:00 GMT",
#    grant_full_control: "GrantFullControl",
#    grant_read: "GrantRead",
#    grant_read_acp: "GrantReadACP",
#    grant_write_acp: "GrantWriteACP",
    key: "ObjectKey", # required
    metadata: {
      "flower" => "lily",
    },
#    server_side_encryption: "AES256", # accepts AES256
#    storage_class: "STANDARD", # accepts STANDARD, REDUCED_REDUNDANCY
#    website_redirect_location: "WebsiteRedirectLocation",
#    sse_customer_algorithm: "aws:kms",
#    sse_customer_key: "SSECustomerKey",
#    sse_customer_key_md5: "SSECustomerKeyMD5",
#    ssekms_key_id: "SSEKMSKeyId",
#     request_payer: "requester", # accepts requester, owners need not specify this parameter in their requests
  })
  
  puts resp.bucket #=> String
  puts resp.key #=> String
  puts resp.upload_id #=> String

  
  uploadId = resp.upload_id


  resp = s3.upload_part({
    body: File.open('castle.jpg.001','rb'), # file/IO object, or string data
    bucket: buckets[0], # required
#    content_length: 1,
#    content_md5: "ContentMD5",
    key: "ObjectKey", # required
    part_number: 1, # required
    upload_id: uploadId, # required
#    sse_customer_algorithm: "SSECustomerAlgorithm",
#    sse_customer_key: "SSECustomerKey",
#    sse_customer_key_md5: "SSECustomerKeyMD5",
#    request_payer: "requester", # accepts requester
  })
  
  tag1 = resp.etag 
  
  
  resp = s3.list_parts({
    bucket: buckets[0], # required
    key: "ObjectKey", # required
  #    max_parts: 3,
  #    part_number_marker: 1,
    upload_id: uploadId, # required
    request_payer: "requester", # accepts requester
  })
  
  puts "upload parts, list parts info"
  puts "In bucket "+ resp.bucket
  print "part 1 info"
  print resp.parts[0].part_number #=> Integer
  print resp.parts[0].last_modified #=> Time
  print resp.parts[0].etag #=> String
  print resp.parts[0].size #=> Integer
  
  
  resp = s3.upload_part({
    body: File.open('castle.jpg.002','rb'), # file/IO object, or string data
    bucket: buckets[0], # required
#    content_length: 1,
#    content_md5: "ContentMD5",
    key: "ObjectKey", # required
    part_number: 2, # required
    upload_id: uploadId, # required
#    sse_customer_algorithm: "SSECustomerAlgorithm",
#    sse_customer_key: "SSECustomerKey",
#    sse_customer_key_md5: "SSECustomerKeyMD5",
#    request_payer: "requester", # accepts requester
  })
  tag2 = resp.etag 
  
  resp = s3.upload_part({
    body: File.open('castle.jpg.003','rb'), # file/IO object, or string data
    bucket: buckets[0], # required
#    content_length: 1,
#    content_md5: "ContentMD5",
    key: "ObjectKey", # required
    part_number: 3, # required
    upload_id: uploadId, # required
#    sse_customer_algorithm: "SSECustomerAlgorithm",
#    sse_customer_key: "SSECustomerKey",
#    sse_customer_key_md5: "SSECustomerKeyMD5",
#    request_payer: "requester", # accepts requester
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
    request_payer: "requester", # accepts requester
  })

  # clean all buckets
  cleanup 
end