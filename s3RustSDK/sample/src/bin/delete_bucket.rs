use std::process;

mod client;

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

    match client::client()
        .delete_bucket()
        .bucket(&bucket)
        .send()
        .await
    {
        Ok(_) => {
            println!("Deleted bucket {}", bucket);
        }
        
        Err(e) => {
            println!("Got an error deleting bucket:");
            println!("{}", e);
            process::exit(1);
        }
    };
}
