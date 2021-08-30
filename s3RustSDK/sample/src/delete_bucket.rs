use s3::Client;
use std::process;

pub async fn deletebucket(client: Client, bucket: String) {
    match client.delete_bucket().bucket(&bucket).send().await {
        Ok(_) => {
            println!("Deleted bucket name: {}\n", bucket);
        }
        Err(e) => {
            println!("Got an error deleting bucket");
            println!("{}", e);
            process::exit(1);
        }
    };
}
