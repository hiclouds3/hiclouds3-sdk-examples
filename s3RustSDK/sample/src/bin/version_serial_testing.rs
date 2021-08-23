use std::process;

mod client;

use s3::model::{VersioningConfiguration,MfaDelete};

use structopt::StructOpt;
use tracing_subscriber::fmt::format::FmtSpan;
use tracing_subscriber::fmt::SubscriberBuilder;
#[derive(Debug, StructOpt)]
struct Opt {
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
        
    let cfg=VersioningConfiguration::builder()
        .mfa_delete(MfaDelete::Disabled)
        .status(s3::model::BucketVersioningStatus::Enabled)
        .build();

    match client::client()
        .put_bucket_versioning()
        .versioning_configuration(cfg)
        .bucket(&bucket)
        .send()
        .await
    {
        Ok(_) => {
            println!("Created bucket {} version", bucket);
        }
        
        Err(e) => {
            println!("Got an error creating bucket version:");
            println!("{}", e);
            process::exit(1);
        }
    };
}