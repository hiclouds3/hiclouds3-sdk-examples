require 'digest'
def lifecycleserialtesting(s3, buckets, creds)
  cleanup
  
  resp = s3.create_bucket({
  acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
  bucket: buckets[0], # required
  create_bucket_configuration: {
    location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
  }
  })
  
  s3Resource = Aws::S3::Resource.new(
    credentials: Aws::Credentials.new(creds['Access Key ID'], creds['Secret Access Key']),
    region: 'ap-northeast-1', endpoint: "http://s3.hicloud.net.tw", signature_version: 's3'
  )
  obj = s3Resource.bucket(buckets[0]).object('hello')
  obj.put(body: 'Hello World!')

  
#  puts Digest::MD5.base64digest('hello')
  
  
  ################setting only have 1 version object bucket ###################
  resp = s3.put_bucket_lifecycle({
    bucket: buckets[0], # required
#    content_md5: Base64.encode64(Digest::MD5.digest(body)).chomp!,
    lifecycle_configuration: {
      rules: [ # required
        {
          expiration: {
#            date: '2020-10-17T00:00:00.000Z',
            days: 730,
          },
          id: "life_cycle_rule",# unique identifier for the rule cannot longer then 255 chars
          prefix: 'hello', # required
          status: "Enabled", # required, accepts Enabled, Disabled
          # Not Support
#          transition: { 
#            date: Time.now,
#            days: 365,
#            storage_class: "GLACIER", # accepts GLACIER
#          },
#          noncurrent_version_transition: {
#            noncurrent_days: 1,
#            storage_class: "GLACIER", # accepts GLACIER
#          },
#          noncurrent_version_expiration: {
#            noncurrent_days: 1,
#          },
        },
      ],
    },
  })
  

  resp = s3.get_bucket_lifecycle({
    bucket: buckets[0], # required
  })
#  puts resp.rules[0].expiration.date #=> Time
  puts "expiration.days: "+resp.rules[0].expiration.days.to_s #=> Integer
  puts "rule id: "+resp.rules[0].id #=> String
  puts "prefix of rule apply on: "+resp.rules[0].prefix #=> String
  puts "status: "+resp.rules[0].status #=> String, one of "Enabled", "Disabled"
  # Not Support 
  #  puts resp.rules[0].transition.date #=> Time
  #  puts "transition days (should less then expiration days): "+resp.rules[0].transition.days.to_s #=> Integer
  #  puts "storage after transition: "+resp.rules[0].transition.storage_class #=> String, one of "GLACIER"
  #  puts resp.rules[0].noncurrent_version_transition.noncurrent_days #=> Integer
  #  puts resp.rules[0].noncurrent_version_transition.storage_class #=> String, one of "GLACIER"
  #  puts resp.rules[0].noncurrent_version_expiration.noncurrent_days #=> Integer

  resp = s3.delete_bucket_lifecycle({
    bucket: buckets[0], # required
  })

  # clean all buckets
  cleanup
end