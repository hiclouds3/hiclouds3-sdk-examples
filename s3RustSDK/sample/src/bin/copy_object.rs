mod client;

use structopt::StructOpt;

use std::error::Error;
use tracing_subscriber::fmt::format::FmtSpan;
use tracing_subscriber::fmt::SubscriberBuilder;

#[derive(Debug, StructOpt)]
struct Opt {
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
        key,
        copy_source,
        verbose,
    } = Opt::from_args();

    if verbose {
        println!("S3 client version: {}\n", s3::PKG_VERSION);
        println!("Bucket:            {}", bucket);
        println!("Key:               {}", key);

        SubscriberBuilder::default()
            .with_env_filter("info")
            .with_span_events(FmtSpan::CLOSE)
            .init();
    }

    let resp = client::client()
        .copy_object()
        .bucket(&bucket)
        .key(&key)
        .copy_source(&copy_source)
        .send()
        .await?;

    println!("Upload success. Version: {:?}", resp.version_id);
    Ok(())
}