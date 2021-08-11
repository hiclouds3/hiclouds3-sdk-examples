use s3::{Client, Config, Region,Credentials};

use aws_types::region::ProvideRegion;

use structopt::StructOpt;

use std::error::Error;
use tracing_subscriber::fmt::format::FmtSpan;
use tracing_subscriber::fmt::SubscriberBuilder;

#[derive(Debug, StructOpt)]
struct Opt {
    /// The region. Overrides environment variable AWS_DEFAULT_REGION.
    #[structopt(short, long)]
    default_region: Option<String>,

    /// Specifies the bucket
    #[structopt(short, long)]
    bucket: String,

    /// Specifies the object in the bucket
    #[structopt(short, long)]
    key: String,

    /// Specifies the copy source
    #[structopt(short, long)]
    copy_source: String,

    /// Whether to display additional runtime information
    #[structopt(short, long)]
    verbose: bool,
}

/// Lists your buckets and uploads a file to a bucket.
/// # Arguments
///
/// * `-b BUCKET` - The bucket to which the file is uploaded.
/// * `-k KEY` - The file to upload to the bucket.
/// * `[-d DEFAULT-REGION]` - The region in which the client is created.
///    If not supplied, uses the value of the **AWS_DEFAULT_REGION** environment variable.
///    If the environment variable is not set, defaults to **us-west-2**.
/// * `[-v]` - Whether to display additional information.
#[tokio::main]
async fn main() -> Result<(), Box<dyn Error>> {
    let Opt {
        bucket,
        default_region,
        key,
        copy_source,
        verbose,
    } = Opt::from_args();

    let credentials = Credentials::new("","", None,None, "STATIC_CREDENTIALS");

    let region = default_region
        .as_ref()
        .map(|region| Region::new(region.clone()))
        .or_else(|| aws_types::region::default_provider().region())
        .unwrap_or_else(|| Region::new("us-west-2"));

    if verbose {
        println!("S3 client version: {}\n", s3::PKG_VERSION);
        println!("Region:            {:?}", &region);
        println!("Bucket:            {}", bucket);
        println!("Key:               {}", key);

        SubscriberBuilder::default()
            .with_env_filter("info")
            .with_span_events(FmtSpan::CLOSE)
            .init();
    }

    let conf = Config::builder()
        .credentials_provider(credentials)
        .region(region)
        .build();

    let client = Client::from_conf(conf);

    let resp = client
        .copy_object()
        .bucket(&bucket)
        .key(&key)
        .copy_source(&copy_source)
        .send()
        .await?;

    println!("Upload success. Version: {:?}", resp.version_id);
    Ok(())
}