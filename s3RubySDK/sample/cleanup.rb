
def cleanup
  creds = JSON.load(File.read('config.json'))
  s3 = Aws::S3::Resource.new(
    credentials: Aws::Credentials.new(creds['Access Key ID'], creds['Secret Access Key']),
    region: 'ap-northeast-1',
    endpoint: "http://s3.hicloud.net.tw", 
    signature_version: 's3'
  )
  s3.buckets.each do |bucket|
    puts "delete #{bucket.name}"
    bucket.delete! #Deletes all objects and then delete bucket
    puts "delete success"
  end
end