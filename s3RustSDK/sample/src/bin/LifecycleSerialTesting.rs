use std::process;

use s3::Credentials;

use s3::{Client, Config, Region};

use s3::model::{BucketLifecycleConfiguration, LifecycleRule, LifecycleExpiration};
use aws_types::region::ProvideRegion;

use structopt::StructOpt;
use tracing_subscriber::fmt::format::FmtSpan;
use tracing_subscriber::fmt::SubscriberBuilder;
#[derive(Debug, StructOpt)]
struct Opt {
    /// The default region
    #[structopt(short, long)]
    default_region: Option<String>,

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
        default_region,
        bucket,
        verbose,
    } = Opt::from_args();

    let credentials = Credentials::new("","", None,None, "STATIC_CREDENTIALS");
    
    let region = default_region
        .as_ref()
        .map(|region| Region::new(region.clone()))
        .or_else(|| aws_types::region::default_provider().region())
        .unwrap_or_else(|| Region::new("us-west-2"));

    if verbose {
        println!("S3 client version: {}", s3::PKG_VERSION);
        println!("Region:            {:?}", &region);

        SubscriberBuilder::default()
            .with_env_filter("info")
            .with_span_events(FmtSpan::CLOSE)
            .init();
    }

    let config = Config::builder()
        .credentials_provider(credentials)
        .region(&region)
        .build();

    let client = Client::from_conf(config);
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

    match client
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
