
#put get delete  ...

#Supports configuring your bucket to allow cross-origin requests
def bucketTaggingSerialTesting(s3, buckets, creds)
  
  cleanup
  
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
    create_bucket_configuration: {
      location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
    }
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
  
  
end