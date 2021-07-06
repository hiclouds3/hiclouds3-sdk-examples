


#!/usr/bin/env ruby

# Gemfile
gem 'aws-sdk', '~> 2'
 
# in code
require 'aws-sdk'
require 'aws-sdk-core'
require 'io/console'
require 'json'
#load 'Hello.rb'
load 'cleanup.rb'
#load 'BucketSerialTesting.rb'
#load 'ObjectSerialTesting.rb'
Aws.use_bundled_cert!

puts "S3 Ruby SDK Serial Test\n"
puts "-----------------------------------------------------------------------"

buckets = ["testrubybucket1", "testrubybucket2"]
creds = JSON.load(File.read('config.json'))
#Aws.config[:credentials] = Aws::Credentials.new(creds['Access Key ID'], creds['Secret Access Key'])

#puts creds['Access Key ID']
#puts creds['Secret Access Key']
  

# new client
# signature_version: choose 'v4' to use V4 Singing, or 's3' to use V2 Signing (V4 Signing are not supported) 
s3 = Aws::S3::Client.new(  region: "ap-northeast-1", require_https_for_sse_cpk: false,
access_key_id: creds['Access Key ID'], secret_access_key: creds['Secret Access Key'], endpoint: "http://s3.hicloud.net.tw", signature_version: 's3')
  
  
# create a bucket
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
  region: 'ap-northeast-1'
)

#create obj
#castle.jpg.001 is the key
obj = s3.bucket(buckets[0]).object('hinet1.jpg')
#upload file
obj.upload_file((File.expand_path("hinet1.jpg", "~/workspace/s3RubySDK/")).to_s, acl: 'public-read')
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
access_key_id: creds['Access Key ID'], secret_access_key: creds['Secret Access Key'])

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


#resp = hello("hihi")

resp = s3.delete_bucket({
  bucket: bucketname[0], # required
})


cleanup



 