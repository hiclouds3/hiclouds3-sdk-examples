def acl_serial_testing(s3, buckets, creds)
  # create a bucket
  resp = s3.create_bucket({
    #  acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  # get bucket acl
  resp = s3.get_bucket_acl({
    bucket: buckets[0], # required
  })

  # list bucket acl information
  puts "receive from get bucket acl"
  puts "owner name " + resp.owner.display_name #=> String
  puts "owner id " + resp.owner.id #=> String
  #  puts resp.grants #=> Array
  puts "grantee name " + resp.grants[0].grantee.display_name #=> String
  #  puts "grantee email "+ resp.grants[0].grantee.email_address #=> String
  puts "grantee id " + resp.grants[0].grantee.id #=> String
  puts "grantee type " + resp.grants[0].grantee.type #=> String, one of "CanonicalUser", "AmazonCustomerByEmail", "Group"
  #  puts "grantee uri "+resp.grants[0].grantee.uri #=> String
  puts "grantee permission " + resp.grants[0].permission #=> String, one of "FULL_CONTROL", "WRITE", "WRITE_ACP", "READ", "READ_ACP"

  #Specify a canned ACL, or
  #Specify the permission for each grantee explicitly
  #Specify a canned ACL
  s3.put_bucket_acl({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  s3.put_bucket_acl({
    bucket: buckets[0],
    access_control_policy: {
      grants: [
        {
          grantee: {
            type: "Group", # required, accepts CanonicalUser, AmazonCustomerByEmail, Group
            uri: "http://acs.amazonaws.com/groups/s3/LogDelivery",
          },
          permission: "WRITE_ACP", # accepts FULL_CONTROL, WRITE, WRITE_ACP, READ, READ_ACP
        },
        {
          grantee: {
            type: "CanonicalUser", # required, accepts CanonicalUser, AmazonCustomerByEmail, Group
            id: creds["Owner ID"],
          },
          permission: "FULL_CONTROL", # accepts FULL_CONTROL, WRITE, WRITE_ACP, READ, READ_ACP
        },
      ],
      owner: {
        display_name: creds["Owner Name"],
        id: creds["Owner ID"],
      },
    },
  })

  # get bucket acl
  resp = s3.get_bucket_acl({
    bucket: buckets[0], # required
  })

  # list bucket acl information
  puts "receive from get bucket acl"
  puts "owner name " + resp.owner.display_name #=> String
  puts "owner id " + resp.owner.id #=> String
  #  puts resp.grants #=> Array
  #  puts "grantee name " + resp.grants[0].grantee.display_name #=> String
  #  puts "grantee email "+ resp.grants[0].grantee.email_address #=> String
  #  puts "grantee id "+ resp.grants[0].grantee.id #=> String
  puts "grantee type " + resp.grants[0].grantee.type #=> String, one of "CanonicalUser", "AmazonCustomerByEmail", "Group"
  #  puts "grantee uri "+resp.grants[0].grantee.uri #=> String
  puts "grantee permission " + resp.grants[0].permission #=> String, one of "FULL_CONTROL", "WRITE", "WRITE_ACP", "READ", "READ_ACP"

  resp = s3.put_object({
    body: "Hello World!",
    bucket: buckets[0],
    key: "hello",
  })

  s3.put_object_acl({
    bucket: buckets[0],
    key: "hello", # required
    access_control_policy: {
      grants: [
        {
          grantee: {
            id: creds["Grantee Canonical ID"],
            type: "CanonicalUser", # required, accepts CanonicalUser, AmazonCustomerByEmail, Group
          },
          permission: "FULL_CONTROL", # accepts FULL_CONTROL, WRITE, WRITE_ACP, READ, READ_ACP
        },
        {
          grantee: {
            type: "CanonicalUser", # required, accepts CanonicalUser, AmazonCustomerByEmail, Group
            id: creds["Owner ID"],
          },
          permission: "FULL_CONTROL", # accepts FULL_CONTROL, WRITE, WRITE_ACP, READ, READ_ACP
        },
      ],
      owner: {
        display_name: creds["Owner Name"],
        id: creds["Owner ID"],
      },
    },
  })

  resp = s3.get_object_acl({
    bucket: buckets[0], # required
    key: "hello", # required
  })
  puts "----------------------------------------------------"
  puts "receive from get object acl"
  puts resp.owner.display_name #=> String
  puts resp.owner.id #=> String
  puts resp.grants[0].grantee.display_name #=> String
  puts resp.grants[0].grantee.id #=> String

  cleanup(s3, buckets)
end
