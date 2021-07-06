def versionserialtesting(s3, buckets, creds)
  cleanup
  
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
    create_bucket_configuration: {
      location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
    }
    })
  
    ############### enable bucket to have version##############
  resp = s3.put_bucket_versioning({
    bucket: buckets[0], # required
#    content_md5: "ContentMD5",
#    mfa: myMFA,
    versioning_configuration: { # required
      mfa_delete: "Disabled", # accepts Enabled, Disabled
      status: "Enabled", # accepts Enabled, Suspended
    },
  })
  
  resp = s3.get_bucket_versioning({
    bucket: buckets[0], # required
  })
  
  puts "status " + resp.status #=> String, one of "Enabled", "Suspended"
  # Not Support
  # puts "mfa_delete "+ resp.mfa_delete.to_s #=> String, one of "Enabled", "Disabled"
  
  ################ put object ######################
  
  s3resource = Aws::S3::Resource.new(
    credentials: Aws::Credentials.new(creds['Access Key ID'], creds['Secret Access Key']),
    region: 'ap-northeast-1', endpoint: "http://s3.hicloud.net.tw", signature_version: 's3'
  )
  # from a string
  obj = s3resource.bucket(buckets[0]).object('hello')
  objresp = obj.put(body: 'Hello World!')
  version_id_1 =objresp.version_id
  
  resp = s3.list_object_versions({
    bucket: buckets[0], # required
  })
  
  puts "object version1 info "
  puts "etag "+resp.versions[0].etag #=> String
  puts "size "+ resp.versions[0].size.to_s #=> Integer
  puts "storage_class "+resp.versions[0].storage_class #=> String, one of "STANDARD"
  puts "key "+resp.versions[0].key #=> String
  puts "version_id "+resp.versions[0].version_id #=> String
  puts "is_latest "+resp.versions[0].is_latest.to_s #=> true/false
  puts "last_modified "+resp.versions[0].last_modified.to_s #=> Time
  puts "owner "+resp.versions[0].owner.display_name #=> String
  puts "owner id"+resp.versions[0].owner.id #=> String
  puts "======================================================================="
  
  obj = s3resource.bucket(buckets[0]).object('hello')
  
  objresp = obj.put(body: 'Hello Hello World!')
  version_id_2 =objresp.version_id
      
  resp = s3.list_object_versions({
    bucket: buckets[0], # required
  })
  
  puts "object version2 info "
  puts "etag "+resp.versions[0].etag #=> String
  puts "size "+resp.versions[0].size.to_s #=> Integer
  puts "storage_class "+resp.versions[0].storage_class #=> String, one of "STANDARD"
  puts "key "+resp.versions[0].key #=> String
  puts "version_id "+resp.versions[0].version_id #=> String
  puts "is_latest "+resp.versions[0].is_latest.to_s #=> true/false
  puts "last_modified "+resp.versions[0].last_modified.to_s #=> Time
  puts "owner "+resp.versions[0].owner.display_name #=> String
  puts "owner id"+resp.versions[0].owner.id #=> String
  
  
  ###############get object from different version################
  
  
  resp = s3.get_object(
  {
    bucket: buckets[0], # required
    key: 'hello', # required
    version_id: version_id_1,

  }
  )
  puts "===============list objects of different version ==============="
  puts "version id "+resp.version_id #=> String
  puts resp.body.read
  
  resp = s3.get_object(
  {
    bucket: buckets[0], # required
    key: 'hello', # required
    version_id: version_id_2,
  
  }
  )
  
  puts "version id "+resp.version_id #=> String
  puts resp.body.read
  
  # clean all buckets
  cleanup
end