use s3::model::{BucketLifecycleConfiguration, LifecycleExpiration, LifecycleRule};
use s3::Client;
use std::process;

pub async fn lifecycleserialtesting(client: Client, bucket: String) {
    let expitation = LifecycleExpiration::builder().days(1).build();
    let rule = LifecycleRule::builder()
        .expiration(expitation)
        .prefix("documents/")
        .id("Test1")
        .status(s3::model::ExpirationStatus::Enabled)
        .build();
    let cfg = BucketLifecycleConfiguration::builder().rules(rule).build();
    match client
        .put_bucket_lifecycle_configuration()
        .lifecycle_configuration(cfg)
        .bucket(&bucket)
        .send()
        .await
    {
        Ok(_) => {
            println!("Created {}'s Lifecycle\n", bucket);
        }

        Err(e) => {
            println!("Got an error creating bucket lifecycle");
            println!("{}", e);
            process::exit(1);
        }
    };
}
