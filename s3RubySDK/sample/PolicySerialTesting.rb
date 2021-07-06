def policyserialtesting(s3, buckets, creds)
  cleanup
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
    create_bucket_configuration: {
      location_constraint: "ap-northeast-1", # accepts EU, eu-west-1, us-west-1, us-west-2, ap-southeast-1, ap-southeast-2, ap-northeast-1, sa-east-1, cn-north-1, eu-central-1
    }
    })
  
  
  myPolicy = '{
  "Id": "Policy1436779373666",
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "Stmt1436779368306",
      "Action": [
        "s3:GetObject"
      ],
      "Effect": "Deny",
      "Resource": "arn:aws:s3:::'+buckets[0]+'/*",
      "Principal": "*"
    }
  ]
  }'
  

  resp = s3.put_bucket_policy({
    bucket: buckets[0], # required
  #    content_md5: "ContentMD5",
    policy: myPolicy, # required
  })


  resp = s3.get_bucket_policy({
    bucket: buckets[0], # required
  })
  
  puts resp.policy

  resp = s3.delete_bucket_policy({
    bucket: buckets[0], # required
  })
  
  # clean all buckets
  cleanup
end