use std::process;

use s3::Credentials;

use s3::model::{BucketLoggingStatus, LoggingEnabled, TargetGrant, Grantee,BucketLogsPermission,Type};

use s3::{Client, Config, Region};

use aws_types::region::ProvideRegion;

use structopt::StructOpt;
use tracing_subscriber::fmt::format::FmtSpan;
use tracing_subscriber::fmt::SubscriberBuilder;

#[derive(Debug, StructOpt)]
struct Opt {
    /// The default region
    #[structopt(short, long)]
    default_region: Option<String>,

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

    let r#type = Type::CanonicalUser;

    let grantee=Grantee::builder()
        .id("")
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

    match client
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