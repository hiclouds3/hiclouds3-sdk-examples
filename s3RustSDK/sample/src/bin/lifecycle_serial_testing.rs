use std::process;

mod client;

use s3::model::{BucketLifecycleConfiguration, LifecycleRule, LifecycleExpiration};

use structopt::StructOpt;
use tracing_subscriber::fmt::format::FmtSpan;
use tracing_subscriber::fmt::SubscriberBuilder;
#[derive(Debug, StructOpt)]
struct Opt {
    /// The bucket's names
    #[structopt(short, long)]
    bucket: String,

    /// Whether to display additional information
    #[structopt(short, long)]
    verbose: bool,
}

/// Creates an Amazon S3 bucket
/// # Arguments
///
/// * `-n NAME` - The name of the bucket.
/// * `[-d DEFAULT-REGION]` - The region containing the bucket.
///   If not supplied, uses the value of the **AWS_DEFAULT_REGION** environment variable.
///   If the environment variable is not set, defaults to **us-west-2**.
/// * `[-v]` - Whether to display additional information.
#[tokio::main]
async fn main() {
    let Opt {
        bucket,
        verbose,
    } = Opt::from_args();

    if verbose {
        println!("S3 client version: {}", s3::PKG_VERSION);
    
        SubscriberBuilder::default()
            .with_env_filter("info")
            .with_span_events(FmtSpan::CLOSE)
            .init();
    }

    let expitation =LifecycleExpiration::builder()
        .days(1)
        .build();

    let rule = LifecycleRule::builder()
        .expiration(expitation)
        .prefix("documents/")
        .id("Test1")
        .status(s3::model::ExpirationStatus::Enabled)
        .build();

    let cfg = BucketLifecycleConfiguration::builder()
        .rules(rule)
        .build();

    match client::client()
        .put_bucket_lifecycle_configuration()
        .lifecycle_configuration(cfg)
        .bucket(&bucket)
        .send()
        .await
    {
        Ok(_) => {
            println!("Created bucket {} Lifecycle", bucket);
        }
        
        Err(e) => {
            println!("Got an error creating bucket lifecycle:");
            println!("{}", e);
            process::exit(1);
        }
    };
}
