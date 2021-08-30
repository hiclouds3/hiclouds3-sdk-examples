use s3::{ByteStream, Client};
use std::path::Path;
use std::process;

pub async fn putobject(client: Client, bucket: String, key: String) {
    let body = ByteStream::from_path(Path::new(&key))
        .await
        .unwrap_or_default();
    match client
        .put_object()
        .bucket(&bucket)
        .key(&key)
        .body(body)
        .send()
        .await
    {
        Ok(_) => {
            println!("Put object {} in {}\n", key, bucket);
        }
        Err(e) => {
            println!("Got an error put object");
            println!("{}", e);
            process::exit(1);
        }
    };
}
