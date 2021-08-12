use std::process;

use s3::{Client, Config, Region,Credentials};

use aws_types::region::ProvideRegion;

use structopt::StructOpt;
use tracing_subscriber::fmt::format::FmtSpan;
use tracing_subscriber::fmt::SubscriberBuilder;

#[derive(Debug, StructOpt)]
struct Opt {
    /// The default region
    #[structopt(short, long)]
    default_region: Option<String>,

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
        default_region,
        bucket,
        key,
        verbose,
    } = Opt::from_args();

    let credentials = Credentials::new("","", None,None, "STATIC_CREDENTIALS");

    let region = default_region
        .as_ref()
        .map(|region| Region::new(region.clone()))
        .or_else(|| aws_types::region::default_provider().region())
        .unwrap_or_else(|| Region::new("us-west-2"));

    if verbose {
        println!("S3 client version: {}", s3::PKG_VERSION);
        println!("Region:            {:?}", &region);

        SubscriberBuilder::default()
            .with_env_filter("info")
            .with_span_events(FmtSpan::CLOSE)
            .init();
    }

    let config = Config::builder()
        .credentials_provider(credentials)
        .region(&region)
        .build();

    let client = Client::from_conf(config);

    match client.get_object_acl()
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