use std::process;

mod client;

use s3::model::{BucketLoggingStatus, LoggingEnabled, TargetGrant, Grantee,BucketLogsPermission,Type};

use structopt::StructOpt;
use tracing_subscriber::fmt::format::FmtSpan;
use tracing_subscriber::fmt::SubscriberBuilder;

#[derive(Debug, StructOpt)]
struct Opt {
    /// The name of the bucket
    #[structopt(short, long)]
    bucket: String,

    /// Whether to display additional information
    #[structopt(short, long)]
    verbose: bool,
}

/// Lists your Amazon S3 buckets
/// # Arguments
///
/// * `[-d DEFAULT-REGION]` - The region containing the buckets.
///   If not supplied, uses the value of the **AWS_DEFAULT_REGION** environment variable.
///   If the environment variable is not set, defaults to **us-west-2**.
/// * `[-g]` - Whether to display buckets in all regions.
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

    let r#type = Type::CanonicalUser;

    let grantee=Grantee::builder()
        .id("d1e8b71ffe12661ac2df52d5a96fbf5d6188dace9657d120c8253a61394bc51d")
        .r#type(r#type)
        .build();

    let premission = BucketLogsPermission::FullControl;

    let targetgrant=TargetGrant::builder()
        .grantee(grantee)
        .permission(premission)
        .build();

    let loggingenable=LoggingEnabled::builder()
        .target_bucket("yuyuman2")
        .target_grants(targetgrant)
        .target_prefix("MyBucketLogs/")
        .build();

    let bucketloggingstatus=BucketLoggingStatus::builder()
        .logging_enabled(loggingenable)
        .build();

    match client::client()
        .put_bucket_logging()
        .bucket_logging_status(bucketloggingstatus)
        .bucket(&bucket)
        .send()
        .await 
    {
        Ok(_) => {
            println!("Created bucket {} policy", bucket);
        }
          
        Err(e) => {
            println!("Got an error creating bucket policy:");
            println!("{}", e);
            process::exit(1);
        }
    };
}