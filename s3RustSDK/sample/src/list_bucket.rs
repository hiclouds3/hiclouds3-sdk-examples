use std::process;
use s3::Client;

pub async fn listbucket(client:Client) {
    let mut num_buckets = 0;
    match client
        .list_buckets()
        .send()
        .await {
        Ok(resp) => {
            println!("Buckets:");
            let buckets = resp.buckets.unwrap_or_default();
            for bucket in &buckets {
                match &bucket.name {
                    None => {}
                    Some(b) => {
                        println!("  {}", b);
                        num_buckets += 1;
                    }
                }
            }
            println!("  Found {} buckets globally\n", num_buckets);
        }
        Err(e) => {
            println!("Got an error list buckets");
            println!("{}", e);
            process::exit(1);
        }
    };
}