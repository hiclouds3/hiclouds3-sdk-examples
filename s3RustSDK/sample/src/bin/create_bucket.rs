use std::process;

mod client;

use s3::model::{BucketLocationConstraint, CreateBucketConfiguration};

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

    let constraint = BucketLocationConstraint::from(client::region().as_ref());

    let cfg = CreateBucketConfiguration::builder()
        .location_constraint(constraint)
        .build();

    match client::client()
        .create_bucket()
        .create_bucket_configuration(cfg)
        .bucket(&bucket)
        .send()
        .await
    {
        Ok(_) => {
            println!("Created bucket {}", bucket);
        }
        
        Err(e) => {
            println!("Got an error creating bucket:");
            println!("{}", e);
            process::exit(1);
        }
    };
}
