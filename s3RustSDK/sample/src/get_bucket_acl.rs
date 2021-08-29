use std::process;
use s3::Client;
use s3::model::{BucketCannedAcl};

pub async fn getbucketacl(client:Client,bucket:String) {
    let acl = BucketCannedAcl::from("log-delivery-write");
    match client
        .put_bucket_acl()
        .bucket(&bucket)
        .acl(acl)
        .send()
        .await{
            Ok(_) => {
                println!("Put bucket acl is log-delivery-write");
            }
            Err(e) => {
                println!("Got an error put bucket acl");
                println!("{}", e);
                process::exit(1);
            }
        }
    match client
        .get_bucket_acl()
        .bucket(&bucket)
        .send()
        .await {
        Ok(resp) => {
            println!("{}'s Acl:",bucket);
            for grant in resp.grants.unwrap_or_default(){
                for grantee in grant.grantee{
                    for r#type in grantee.r#type{
                        println!(" Type:      {}", r#type.as_str());
                    }
                }
                for permission in grant.permission{
                    println!(" Permission:{}\n", permission.as_str());
                }
            }
        }
        Err(e) => {
            println!("Got an error get bucket acl");
            println!("{}", e);
            process::exit(1);
        }
    }
}