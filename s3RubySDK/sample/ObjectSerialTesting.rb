def objectserialtesting(s3, buckets, creds)
  cleanup
#   create a bucket
  resp = s3.create_bucket({
  acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
  bucket: buckets[0], # required
  create_bucket_configuration: {
    location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
  }
  })
    
  s3 = Aws::S3::Resource.new(
    credentials: Aws::Credentials.new(creds['Access Key ID'], creds['Secret Access Key']),
    region: 'ap-northeast-1', endpoint: "http://s3.hicloud.net.tw", signature_version: 's3'
  )
  
  #create obj
  #castle.jpg.001 is the key
  obj = s3.bucket(buckets[0]).object('hinet1.jpg')
  #upload file
  obj.upload_file((File.expand_path("hinet1.jpg", "")).to_s, acl: 'public-read') # File.expand_path() second parameter = Nil means current file directory
  puts obj.public_url
  
  # from a string
  obj = s3.bucket(buckets[0]).object('hello')
  obj.put(body: 'Hello World!')
  
  # from an IO object
  obj = s3.bucket(buckets[0]).object('hinet2.jpg')
  File.open('hinet2.jpg') do |file|
    obj.put(body: file)
  end
  
  
  s3 = Aws::S3::Client.new(  region: "ap-northeast-1", require_https_for_sse_cpk: false,
  access_key_id: creds['Access Key ID'], secret_access_key: creds['Secret Access Key'], endpoint: "http://s3.hicloud.net.tw", signature_version: 's3')
  
  resp = s3.get_object(bucket: buckets[0], key: 'hello')
  puts resp.body.read
  
  File.open('download1.jpg', 'wb') do |file|
    reap = s3.get_object({ bucket: buckets[0], key: 'hinet1.jpg' }, target: file)
    end
  
  resp = s3.get_object({ bucket: buckets[0], key: 'hinet2.jpg' }, target: 'download2.jpg')
   
  #  delete multiple objects from a bucket using a single HTTP request
  resp = s3.delete_objects({
    bucket: buckets[0], # required
    delete: { # required
      objects: [ # required
        {
          key: "hinet2.jpg"
        },
        {
          key: "hello"
        },
        {
          key: "hinet1.jpg"
        }
      ],
      quiet: true,
    },
    request_payer: "requester", # accepts requester
  })
  
  #delete all buckets
  cleanup

end