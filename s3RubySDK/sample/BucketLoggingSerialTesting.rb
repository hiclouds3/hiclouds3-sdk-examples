# Set the logging parameters for a bucket and to specify permissions for who can view and modify the logging parameters. 
# To set the logging status of a bucket, you must be the bucket owner.

def bucketloggingserialtesting(s3, buckets, creds)
  
  cleanup
  
  
  resp = s3.create_bucket({
    acl: "log-delivery-write ", # accepts private, public-read, public-read-write, authenticated-read, log-delivery-write
    bucket: buckets[0], # required
    create_bucket_configuration: {
      location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
    }
    })
    
#  resp = s3.get_bucket_acl({
#    bucket: buckets[0], # required
#  })
#
#  puts resp.grants #=> Array
#  puts resp.owner.display_name #=> String
#  puts resp.owner.id #=> String

s3.put_bucket_acl({
  bucket: buckets[0],
  access_control_policy: {
    grants: [
      {
        grantee: {
          type: "Group", # required, accepts CanonicalUser, AmazonCustomerByEmail, Group
          uri: "http://acs.amazonaws.com/groups/s3/LogDelivery",
        },
        permission: "WRITE", # accepts FULL_CONTROL, WRITE, WRITE_ACP, READ, READ_ACP
      },{
        grantee: {
          type: "Group", # required, accepts CanonicalUser, AmazonCustomerByEmail, Group
          uri: "http://acs.amazonaws.com/groups/s3/LogDelivery",
        },
        permission: "READ_ACP", # accepts FULL_CONTROL, WRITE, WRITE_ACP, READ, READ_ACP
        },{
        grantee: {
          type: "CanonicalUser", # required, accepts CanonicalUser, AmazonCustomerByEmail, Group
          id: creds['Owner ID'],
        },
        permission: "FULL_CONTROL", # accepts FULL_CONTROL, WRITE, WRITE_ACP, READ, READ_ACP
      },
    ],
    owner: {
      display_name: creds['Owner Name'],
      id: creds['Owner ID'],
    },
  },
})

    
  resp = s3.put_bucket_logging({
    bucket: buckets[0], # required
    bucket_logging_status: { # required
      logging_enabled: {
        target_bucket: buckets[0],
        target_grants: [
          {
            grantee: {
              id: creds['Grantee Canonical ID'],
              type: "CanonicalUser", # required, accepts CanonicalUser, AmazonCustomerByEmail, Group
            },
            permission: "READ", # accepts FULL_CONTROL, READ, WRITE
          },
        ],
        target_prefix: "logs/",
      },
    },
#    content_md5: "ContentMD5",
  })
 
  
  resp = s3.get_bucket_logging({
    bucket: buckets[0], # required
  })
  puts resp.logging_enabled.target_bucket #=> String
#  puts resp.logging_enabled.target_grants #=> Array
  puts resp.logging_enabled.target_grants[0].grantee.display_name #=> String
#  resp.logging_enabled.target_grants[0].grantee.email_address #=> String
  puts resp.logging_enabled.target_grants[0].grantee.id #=> String
  puts resp.logging_enabled.target_grants[0].grantee.type #=> String, one of "CanonicalUser", "AmazonCustomerByEmail", "Group"
#  resp.logging_enabled.target_grants[0].grantee.uri #=> String
  puts resp.logging_enabled.target_grants[0].permission #=> String, one of "FULL_CONTROL", "READ", "WRITE"
  puts resp.logging_enabled.target_prefix #=> String

  # clean all buckets
  cleanup
end