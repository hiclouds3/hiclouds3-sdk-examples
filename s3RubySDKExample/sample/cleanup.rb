def cleanup(s3, buckets)
  puts "===============cleanup==============="
  buckets.each do |bucket|
    resp = s3.list_object_versions({
      bucket: bucket,
    })
    resp.versions.each do |obj|
      puts "delete object: #{bucket}/#{obj.key} #{obj.version_id} "
      s3.delete_object({
        bucket: bucket,
        key: obj.key,
        version_id: obj.version_id,
      })
    end
    resp.delete_markers.each do |marker|
      puts "Rm delete marker: #{bucket}/#{marker.key} #{marker.version_id} "
      s3.delete_object({
        bucket: bucket,
        key: marker.key,
        version_id: marker.version_id,
      })
    end
    puts "delete bucket: #{bucket}"
    s3.delete_bucket({
      bucket: bucket,
    })
    puts "delete success"
  rescue Aws::S3::Errors::NoSuchBucket => e
  end
end
