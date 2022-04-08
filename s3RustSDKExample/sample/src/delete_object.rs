use s3::Client;
use std::process;

pub async fn deleteobject(client: Client, bucket: String, key: String) {
    match client
        .delete_object()
        .bucket(&bucket)
        .key(&key)
        .send()
        .await
    {
        Ok(_) => {
            println!("Deleted object {}/{}\n", bucket, key);
        }
        Err(e) => {
            println!("Got an error deleting object");
            println!("{}", e);
            process::exit(1);
        }
    };
}
