def websiteserialtesting(s3, buckets, creds)
  cleanup
  
  
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
    create_bucket_configuration: {
      location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
    }
    })
  
  
  
  resp = s3.put_bucket_website({
    bucket: buckets[0], # required
#    content_md5: "ContentMD5",
    website_configuration: { # required
      error_document: {
        key: "error.html", # required
      },
      index_document: {
        suffix: "index.html", # required
      },
#      redirect_all_requests_to: {
#        host_name: "example.com", # required
#        protocol: "https", # accepts http, https
#      },
#      routing_rules: [
#        {
#          condition: {
#            http_error_code_returned_equals: "HttpErrorCodeReturnedEquals",
#            key_prefix_equals: "KeyPrefixEquals",
#          },
#          redirect: { # required
#            host_name: "example.com",
#            http_redirect_code: "HttpRedirectCode",
#            protocol: "http", # accepts http, https
#            replace_key_prefix_with: "ReplaceKeyPrefixWith",
#            replace_key_with: "ReplaceKeyWith",
#          },
#        },
#      ],
    },
  })
  
  resp = s3.get_bucket_website({
    bucket: buckets[0], # required
  })
      
#  puts resp.redirect_all_requests_to.host_name #=> String
#  puts resp.redirect_all_requests_to.protocol #=> String, one of "http", "https"
  puts resp.index_document.suffix #=> String
  puts resp.error_document.key #=> String
#  puts resp.routing_rules[0].condition.http_error_code_returned_equals #=> String
#  puts resp.routing_rules[0].condition.key_prefix_equals #=> String
#  puts resp.routing_rules[0].redirect.host_name #=> String
#  puts resp.routing_rules[0].redirect.http_redirect_code #=> String
#  puts resp.routing_rules[0].redirect.protocol #=> String, one of "http", "https"
#  puts resp.routing_rules[0].redirect.replace_key_prefix_with #=> String
#  puts resp.routing_rules[0].redirect.replace_key_with #=> String

  resp = s3.delete_bucket_website({
    bucket: buckets[0], # required
  })
  
  # clean all buckets
  cleanup
end