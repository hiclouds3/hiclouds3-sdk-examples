use std::process;
use s3::Client;

pub async fn getobjectacl(client:Client,bucket:String,key:String) {
    match client
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
                    for r#type in grantee.r#type{
                        println!("  Type:      {}", r#type.as_str());
                    }
                }
                for permission in grant.permission{
                    println!("  Permission:{}\n", permission.as_str());
                }
            }
        }
        Err(e) => {
            println!("Got an error get object acl");
            println!("{}", e);
            process::exit(1);
        }
    }
}