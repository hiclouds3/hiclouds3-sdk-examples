use std::process;

use s3::Credentials;

use s3::model::{VersioningConfiguration,MfaDelete};
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

    /// The bucket name
    #[structopt(short, long)]
    bucket: String,

    /// Whether to display additional information
    #[structopt(short, long)]
    verbose: bool,
}

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
        
    let cfg=VersioningConfiguration::builder()
        .mfa_delete(MfaDelete::Disabled)
        .status(s3::model::BucketVersioningStatus::Enabled)
        .build();
    let client = Client::from_conf(config);

    match client
        .put_bucket_versioning()
        .versioning_configuration(cfg)
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