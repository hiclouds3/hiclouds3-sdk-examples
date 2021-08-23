use std::process;

mod client;

use structopt::StructOpt;
use tracing_subscriber::fmt::format::FmtSpan;
use tracing_subscriber::fmt::SubscriberBuilder;

#[derive(Debug, StructOpt)]
struct Opt {
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
        verbose,
    } = Opt::from_args();

    if verbose {
        println!("S3 client version: {}", s3::PKG_VERSION);

        SubscriberBuilder::default()
            .with_env_filter("info")
            .with_span_events(FmtSpan::CLOSE)
            .init();
    }

    let mut num_buckets = 0;

    match client::client()
        .list_buckets()
        .send()
        .await {
        Ok(resp) => {
            println!("\nBuckets:\n");

            let buckets = resp.buckets.unwrap_or_default();

            for bucket in &buckets {
                match &bucket.name {
                    None => {}
                    Some(b) => {
                        println!("{}", b);
                        num_buckets += 1;
                    }
                }
            }

            println!("\nFound {} buckets globally", num_buckets);
        }
        Err(e) => {
            println!("Got an error listing buckets:");
            println!("{}", e);
            process::exit(1);
        }
    };
}