def website_serial_testing(s3, buckets)
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  resp = s3.put_bucket_website({
    bucket: buckets[0], # required
    #    content_md5: "ContentMD5",
    website_configuration: { # required
      error_document: {
        key: "error.html", # required
      },
      index_document: {
        suffix: "index.html", # required
      },
    },
  })

  resp = s3.get_bucket_website({
    bucket: buckets[0], # required
  })

  puts resp.index_document.suffix #=> String
  puts resp.error_document.key #=> String

  resp = s3.delete_bucket_website({
    bucket: buckets[0], # required
  })

  # clean all buckets
  cleanup(s3, buckets)
end
