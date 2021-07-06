

#!/usr/bin/env ruby

# Gemfile
gem 'aws-sdk', '~> 2'
 
# in code
require 'aws-sdk'
require 'aws-sdk-core'
require 'io/console'
require 'json'
load 'cleanup.rb'
load 'BucketSerialTesting.rb'
load 'ObjectSerialTesting.rb'
load 'ACLSerialTesting.rb'
load 'LifeCycleSerialTesting.rb'
load 'BucketCorsSerialTesting.rb'
load 'BucketLoggingSerialTesting.rb'
load 'MPUSerialTesting.rb'
load 'PolicySerialTesting.rb' 
load 'VersioningSerialTesting.rb'
load 'WebsiteSerialTesting.rb'
load 'BucketTaggingSerialTesting.rb'
load 'PresignURL.rb'
Aws.use_bundled_cert!

puts "S3 Ruby SDK Serial Test\n"
puts "-----------------------------------------------------------------------"

buckets = ["testrubybucket1", "testrubybucket2"]
creds = JSON.load(File.read('config.json'))

# new client
# signature_version: choose 'v4' to use V4 Singing, or 's3' to use V2 Signing (V4 Signing are not supported) 
s3 = Aws::S3::Client.new(region: "ap-northeast-1", require_https_for_sse_cpk: false,
access_key_id: creds['Access Key ID'], secret_access_key: creds['Secret Access Key'], endpoint: "http://s3.hicloud.net.tw", signature_version: 's3',
http_idle_timeout: 5, http_open_timeout: 3, log_level: "debug")
  
begin
#cleanup
#bucketserialtesting(s3, buckets, creds) 
#objectserialtesting(s3, buckets, creds) 
#aclserialtesting(s3, buckets, creds)
#bucketTaggingSerialTesting(s3, buckets, creds)
#bucketcorsserialtesting(s3, buckets, creds) 
#mpuserialtesting(s3, buckets, creds) 
#policyserialtesting(s3, buckets, creds) 
#websiteserialtesting(s3, buckets, creds) 
#versionserialtesting(s3, buckets, creds) 
#lifecycleserialtesting(s3, buckets, creds) 
#bucketloggingserialtesting(s3, buckets, creds)
presignurl(s3, buckets, creds)
  
rescue Aws::S3::Errors::ServiceError => e
  puts e.message
  puts e.backtrace.join("\n")
end

puts 'Ruby SDK Serial Test Done!'


