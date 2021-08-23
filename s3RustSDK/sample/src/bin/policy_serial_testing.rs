use std::process;

mod client;

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

    match client::client()
        .put_bucket_policy()
        .policy("{\"Version\": \"2012-10-17\", \"Statement\": [{ \"Sid\": \"DenyPublicREAD\",\"Effect\": \"Deny\",\"Principal\": {\"AWS\": \"*\"}, \"Action\": \"s3:GetObject\", \"Resource\": [\"arn:aws:s3:::yuyuman1/*\" ] } ]}")
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
