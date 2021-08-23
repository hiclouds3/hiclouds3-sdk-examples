use std::process;

mod client;

use structopt::StructOpt;
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

    match client::client()
        .delete_object()
        .bucket(&bucket)
        .key(&key)
        .send()
        .await
    {
        Ok(_) => {
            println!("Deleted object {}/{}" ,bucket ,key);
        }
        
        Err(e) => {
            println!("Got an error deleting object:");
            println!("{}", e);
            process::exit(1);
        }
    };
}
