# Supports configuring your bucket to allow cross-origin requests
def bucket_cors_serial_testing(s3, buckets)
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  resp = s3.put_bucket_cors({
    bucket: buckets[0], # required
    cors_configuration: {
      cors_rules: [
        {
          allowed_headers: ["AllowedHeader"],
          allowed_methods: ["GET", "HEAD"],
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

  puts "allow header " + resp.cors_rules[0].allowed_headers[0] #=> String
  puts "allow method " + resp.cors_rules[0].allowed_methods[0] #=> String
  puts "allow origin " + resp.cors_rules[0].allowed_origins[0] #=> String
  puts "expose header " + resp.cors_rules[0].expose_headers[0] #=> String
  puts "max age seconds " + resp.cors_rules[0].max_age_seconds.to_s #=> Integer

  #delete bucket cors

  resp = s3.delete_bucket_cors({
    bucket: buckets[0], # required
  })

  cleanup(s3, buckets)
end
