use s3::Client;
use std::process;

pub async fn copyobject(client: Client, bucket1: String, bucket2: String, key: String) {
    match client
        .copy_object()
        .bucket(&bucket2)
        .key(&key)
        .copy_source(bucket1 + "/" + &key)
        .send()
        .await
    {
        Ok(_) => {
            println!("copyobject success.\n");
        }
        Err(e) => {
            println!("Got an error copy objects");
            println!("{}", e);
            process::exit(1);
        }
    };
}
