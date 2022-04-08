use s3::model::{MfaDelete, VersioningConfiguration};
use s3::Client;
use std::process;

pub async fn versionserialtesting(client: Client, bucket: String) {
    let cfg = VersioningConfiguration::builder()
        .mfa_delete(MfaDelete::Disabled)
        .status(s3::model::BucketVersioningStatus::Enabled)
        .build();
    match client
        .put_bucket_versioning()
        .versioning_configuration(cfg)
        .bucket(&bucket)
        .send()
        .await
    {
        Ok(_) => {
            println!("Created {}'s version\n", bucket);
        }
        Err(e) => {
            println!("Got an error creating bucket version");
            println!("{}", e);
            process::exit(1);
        }
    };
}
