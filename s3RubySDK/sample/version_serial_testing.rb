def version_serial_testing(s3, buckets)
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  ############### enable bucket to have version##############
  resp = s3.put_bucket_versioning({
    bucket: buckets[0], # required
    versioning_configuration: { # required
      mfa_delete: "Disabled", # accepts Enabled, Disabled
      status: "Enabled", # accepts Enabled, Suspended
    },
  })

  resp = s3.get_bucket_versioning({
    bucket: buckets[0], # required
  })

  puts "status " + resp.status #=> String, one of "Enabled", "Suspended"

  ################ put object ######################
  # from a string
  resp = s3.put_object({
    body: "Hello World!",
    bucket: buckets[0],
    key: "hello",
  })
  version_id_1 = resp.version_id

  resp = s3.list_object_versions({
    bucket: buckets[0], # required
  })

  puts "object version1 info "
  puts "etag " + resp.versions[0].etag #=> String
  puts "size " + resp.versions[0].size.to_s #=> Integer
  puts "storage_class " + resp.versions[0].storage_class #=> String, one of "STANDARD"
  puts "key " + resp.versions[0].key #=> String
  puts "version_id " + resp.versions[0].version_id #=> String
  puts "is_latest " + resp.versions[0].is_latest.to_s #=> true/false
  puts "last_modified " + resp.versions[0].last_modified.to_s #=> Time
  puts "owner " + resp.versions[0].owner.display_name #=> String
  puts "owner id" + resp.versions[0].owner.id #=> String
  puts "======================================================================="

  resp = s3.put_object({
    body: "Hello Hello World!",
    bucket: buckets[0],
    key: "hello",
  })
  version_id_2 = resp.version_id

  resp = s3.list_object_versions({
    bucket: buckets[0], # required
  })

  puts "object version2 info "
  puts "etag " + resp.versions[0].etag #=> String
  puts "size " + resp.versions[0].size.to_s #=> Integer
  puts "storage_class " + resp.versions[0].storage_class #=> String, one of "STANDARD"
  puts "key " + resp.versions[0].key #=> String
  puts "version_id " + resp.versions[0].version_id #=> String
  puts "is_latest " + resp.versions[0].is_latest.to_s #=> true/false
  puts "last_modified " + resp.versions[0].last_modified.to_s #=> Time
  puts "owner " + resp.versions[0].owner.display_name #=> String
  puts "owner id" + resp.versions[0].owner.id #=> String

  ###############get object from different version################

  resp = s3.get_object(
    {
      bucket: buckets[0], # required
      key: "hello", # required
      version_id: version_id_1,

    }
  )
  puts "===============list objects of different version ==============="
  puts "version id " + resp.version_id #=> String
  puts resp.body.read

  resp = s3.get_object(
    {
      bucket: buckets[0], # required
      key: "hello", # required
      version_id: version_id_2,

    }
  )

  puts "version id " + resp.version_id #=> String
  puts resp.body.read

  # clean all buckets
  cleanup(s3, buckets)
end
