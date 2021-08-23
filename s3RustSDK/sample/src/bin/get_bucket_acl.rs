use std::process;

mod client;

use s3::model::{BucketCannedAcl};

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

/// Lists the objects in an Amazon S3 bucket.
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
        verbose,
    } = Opt::from_args();

    if verbose {
        println!("S3 client version: {}", s3::PKG_VERSION);

        SubscriberBuilder::default()
            .with_env_filter("info")
            .with_span_events(FmtSpan::CLOSE)
            .init();
    }

    let acl = BucketCannedAcl::from("log-delivery-write");

    match client::client()
        .put_bucket_acl()
        .bucket(&bucket)
        .acl(acl)
        .send()
        .await{
            Ok(_) => {
                println!("Put bucket acl ");
            }
            Err(e) => {
                println!("Got an error put bucket acl:");
                println!("{}", e);
                process::exit(1);
            }
        }

    match client::client()
        .get_bucket_acl()
        .bucket(&bucket)
        .send()
        .await {
        Ok(resp) => {
            println!("{}'s Acl:",bucket);
            for grant in resp.grants.unwrap_or_default(){
                for grantee in grant.grantee{
                    println!(" Grantee:   {}", grantee.display_name.expect("Grantee have display_name"));
                    for r#type in grantee.r#type{
                        println!(" Type:      {}", r#type.as_str());
                    }
                }
                for permission in grant.permission{
                    println!(" Permission:{}", permission.as_str());
                }
            }
        }
        Err(e) => {
            println!("Got an error retrieving bucket acl:");
            println!("{}", e);
            process::exit(1);
        }
    }
}