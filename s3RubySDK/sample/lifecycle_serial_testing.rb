def lifecycle_serial_testing(s3, buckets)
  resp = s3.create_bucket({
    acl: "public-read", # accepts private, public-read, public-read-write, authenticated-read
    bucket: buckets[0], # required
  })

  resp = s3.put_object({
    body: "Hello World!",
    bucket: buckets[0],
    key: "hello",
  })

  resp = s3.put_bucket_lifecycle({
    bucket: buckets[0], # required
    lifecycle_configuration: {
      rules: [ # required
        {
          id: "life_cycle_expire_rule", # unique identifier for the rule cannot longer then 255 chars
          prefix: "hello", # required
          status: "Enabled", # required, accepts Enabled, Disabled
          expiration: {
            days: 730,
          },
        },
        {
          id: "life_cycle_transition_rule", # unique identifier for the rule cannot longer then 255 chars
          prefix: "hello", # required
          status: "Enabled", # required, accepts Enabled, Disabled
          transition: {
            days: 365,
            storage_class: "GLACIER", # accepts GLACIER
          },
        },
      ],
    },
  })

  resp = s3.get_bucket_lifecycle({
    bucket: buckets[0], # required
  })

  puts "expiration.days: " + resp.rules[0].expiration.days.to_s #=> Integer
  puts "rule id: " + resp.rules[0].id #=> String
  puts "prefix of rule apply on: " + resp.rules[0].prefix #=> String
  puts "status: " + resp.rules[0].status #=> String, one of "Enabled", "Disabled"

  puts resp.rules[1].transition.date #=> Time
  puts "transition days (should less then expiration days): " + resp.rules[1].transition.days.to_s #=> Integer
  puts "storage after transition: " + resp.rules[1].transition.storage_class #=> String, one of "GLACIER"

  resp = s3.delete_bucket_lifecycle({
    bucket: buckets[0], # required
  })

  # clean all buckets
  cleanup(s3, buckets)
end
