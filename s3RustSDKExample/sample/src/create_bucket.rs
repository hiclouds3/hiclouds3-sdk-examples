use s3::model::{BucketLocationConstraint, CreateBucketConfiguration};
use s3::Client;
use s3::Region;
use std::process;

pub async fn createbucket(client: Client, region: Region, bucket: String) {
    let constraint = BucketLocationConstraint::from(region.as_ref());
    let cfg = CreateBucketConfiguration::builder()
        .location_constraint(constraint)
        .build();
    match client
        .create_bucket()
        .create_bucket_configuration(cfg)
        .bucket(&bucket)
        .send()
        .await
    {
        Ok(_) => {
            println!("Created bucket name: {}\n", bucket);
        }
        Err(e) => {
            println!("Got an error creating bucket");
            println!("{}", e);
            process::exit(1);
        }
    };
}
