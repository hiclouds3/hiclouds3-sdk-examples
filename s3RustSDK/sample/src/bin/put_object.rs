use s3::{ByteStream};

mod client;

use structopt::StructOpt;

use std::error::Error;
use std::path::Path;
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
        .list_buckets()
        .send()
        .await?;

    for bucket in resp.buckets.unwrap_or_default() {
        println!("bucket: {:?}", bucket.name.expect("buckets have names"))
    }

    let body = ByteStream::from_path(Path::new("HelloWorld.txt")).await?;

    let resp = client::client()
        .put_object()
        .bucket(&bucket)
        .key(&key)
        .body(body)
        .send()
        .await?;

    println!("Upload success. Version: {:?}", resp.version_id);

    let resp = client::client()
        .get_object()
        .bucket(bucket)
        .key(key)
        .send()
        .await?;
    let data = resp.body.collect().await?;
    println!("data: {:?}", data.into_bytes());
    Ok(())
}