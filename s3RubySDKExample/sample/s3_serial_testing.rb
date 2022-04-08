#!/usr/bin/env ruby

# Gemfile
gem "aws-sdk-s3", "~> 1"

require "aws-sdk-s3"
require "io/console"
require "json"
Aws.use_bundled_cert!

require "./cleanup"
require "./bucket_serial_testing"
require "./object_serial_testing"
require "./acl_serial_testing"
require "./lifecycle_serial_testing"
require "./bucket_tagging_serial_testing"
require "./bucket_cors_serial_testing"
require "./mpu_serial_testing"
require "./policy_serial_testing"
require "./website_serial_testing"
require "./version_serial_testing"
require "./bucket_logging_serial_testing"
require "./presigned_url"

puts "S3 Ruby SDK Serial Test\n"
puts "-----------------------------------------------------------------------"

buckets = ["testrubybucket1", "testrubybucket2"]
creds = JSON.load(File.read("config.json"))

# S3 client
# signature_version: choose 'v4' to use V4 Singing, or 's3' to use V2 Signing
s3 = Aws::S3::Client.new(region: "us-east-1", require_https_for_sse_cpk: false,
                         access_key_id: creds["Access Key ID"], secret_access_key: creds["Secret Access Key"], endpoint: "http://s3.hicloud.net.tw", signature_version: "v4",
                         http_idle_timeout: 5, http_open_timeout: 3, log_level: "debug")

begin
  cleanup(s3, buckets)
  bucket_serial_testing(s3, buckets)
  object_serial_testing(s3, buckets)
  acl_serial_testing(s3, buckets, creds)
  bucket_tagging_serial_testing(s3, buckets)
  bucket_cors_serial_testing(s3, buckets)
  mpu_serial_testing(s3, buckets)
  policy_serial_testing(s3, buckets)
  website_serial_testing(s3, buckets)
  version_serial_testing(s3, buckets)
  lifecycle_serial_testing(s3, buckets)
  bucket_logging_serial_testing(s3, buckets, creds)
  presigned_url(s3, buckets)
rescue Aws::S3::Errors::ServiceError => e
  puts "Error: %s" % e.message
end

puts "S3 Ruby SDK Serial Test Done!"
