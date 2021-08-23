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

    /// Specifies the object in the bucket
    #[structopt(short, long)]
    key: String,

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
        key,
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
        .get_object_acl()
        .bucket(&bucket)
        .key(&key)
        .send().await {
        Ok(resp) => {
            println!("{}'s Acl:",key);
            for owner in resp.owner{
                println!(" Owner:      {}", owner.display_name.expect("owner have display_name"));
            }
            println!(" Grants:");
            for grant in resp.grants.unwrap_or_default(){
                for grantee in grant.grantee{
                    println!("  Grantee:   {}", grantee.display_name.expect("Grantee have display_name"));
                    for r#type in grantee.r#type{
                        println!("  Type:      {}", r#type.as_str());
                    }
                }
                for permission in grant.permission{
                    println!("  Permission:{}", permission.as_str());
                }
            }
        }
        Err(e) => {
            println!("Got an error retrieving object acl:");
            println!("{}", e);
            process::exit(1);
        }
    }
}