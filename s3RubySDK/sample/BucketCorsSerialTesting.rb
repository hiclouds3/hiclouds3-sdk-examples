
#Supports configuring your bucket to allow cross-origin requests
def bucketcorsserialtesting(s3, buckets, creds)
  
  cleanup
  
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
    create_bucket_configuration: {
      location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
    }
    })
    

  
  resp = s3.put_bucket_cors({
    bucket: buckets[0], # required
    cors_configuration: {
      cors_rules: [
        {
          allowed_headers: ["AllowedHeader"],
          allowed_methods: ['GET', 'HEAD'],
          allowed_origins: ["http://yourdomain.com"],
          expose_headers: ["ExposeHeader"],
          max_age_seconds: 1,
        },
      ],
    },
#    content_md5: "ContentMD5",
  })  
  
  resp = s3.get_bucket_cors({
    bucket: buckets[0], # required
  })
  
  puts "allow header "+ resp.cors_rules[0].allowed_headers[0] #=> String
  puts "allow method "+ resp.cors_rules[0].allowed_methods[0] #=> String
  puts "allow origin "+ resp.cors_rules[0].allowed_origins[0] #=> String
  puts "expose header "+ resp.cors_rules[0].expose_headers[0] #=> String
  puts "max age seconds "+ resp.cors_rules[0].max_age_seconds.to_s #=> Integer
  
  #delete bucket cors
  
  resp = s3.delete_bucket_cors({
    bucket: buckets[0], # required
  })

  
end