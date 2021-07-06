
# create bucket 
# put object 
# delete object
# delete bucket



def bucketserialtesting(s3, buckets, creds)
    
  cleanup
  # create a bucket with specific acl
  resp = s3.create_bucket({
  acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
  bucket: buckets[0], # required
  create_bucket_configuration: {
    location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
  }
  })
  
  resp = s3.create_bucket({
  acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
  bucket: buckets[1], # required
  create_bucket_configuration: {
    location_constraint: "ap-northeast-1",
  }
  })
    

  # list buckets
  resp = s3.list_buckets()
  bucketname = resp.buckets.map(&:name)
  puts bucketname
  
  
  s3 = Aws::S3::Resource.new(
    credentials: Aws::Credentials.new(creds['Access Key ID'], creds['Secret Access Key']),
    region: 'ap-northeast-1', endpoint: "http://s3.hicloud.net.tw", signature_version: 's3'
  )
  
  # basic put object
  obj = s3.bucket(buckets[0]).object('hello')
  obj.put(body: 'Hello World!')

  s3 = Aws::S3::Client.new(  region: "ap-northeast-1", require_https_for_sse_cpk: false,
  access_key_id: creds['Access Key ID'], secret_access_key: creds['Secret Access Key'], endpoint: "http://s3.hicloud.net.tw", signature_version: 's3')
  
#  delete multiple objects from a bucket using a single HTTP request
  resp = s3.delete_objects({
    bucket: buckets[0], # required
    delete: { # required
      objects: [ # required

        {
          key: "hello"
        },
      ],
      quiet: true,
    },
    request_payer: "requester", # accepts requester
  })  
    
  # delete specific bucket(need the bucket to have no object inside)
  resp = s3.delete_bucket({
    bucket: bucketname[0], # required
  })
    
  # clean all buckets
  cleanup
end