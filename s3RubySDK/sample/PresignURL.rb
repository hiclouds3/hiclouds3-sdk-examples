def presignurl(s3, buckets, creds)

  #s3 = Aws::S3::Client.new(region: "ap-northeast-1", require_https_for_sse_cpk: false,
  #access_key_id: creds['Access Key ID'], secret_access_key: creds['Secret Access Key'], endpoint:"http://s3.hicloud.net.tw?<http://s3.hicloud.net.tw/> ",signature_version: 's3',http_idle_timeout: 5, http_open_timeout: 3, log_level:"debug")
  
  signer = Aws::S3::Presigner.new(client:Aws::S3::Client.new(region: "ap-northeast-1",signature_version: 's3',access_key_id: creds['Access Key ID'], secret_access_key: creds['Secret Access Key'], endpoint: "http://s3.hicloud.net.tw"))
  
  url = signer.presigned_url(:get_object, bucket: "testjsbucket1",key: "new", secure:false)
  puts url
  
end