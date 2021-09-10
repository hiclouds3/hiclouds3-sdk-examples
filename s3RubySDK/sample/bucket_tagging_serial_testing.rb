#put get delete  ...

#Supports configuring your bucket to allow cross-origin requests
def bucket_tagging_serial_testing(s3, buckets)
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  resp = s3.put_bucket_tagging({
    bucket: buckets[0], # required
    #    content_md5: "ContentMD5",
    tagging: { # required
      tag_set: [ # required
        {
          key: "ObjectKey", # required
          value: "Value", # required
        },
      ],
    },
  })

  resp = s3.get_bucket_tagging({
    bucket: buckets[0], # required
  })

  puts resp.tag_set[0].key #=> String
  puts resp.tag_set[0].value #=> String

  resp = s3.delete_bucket_tagging({
    bucket: buckets[0], # required
  })

  cleanup(s3, buckets)
end
