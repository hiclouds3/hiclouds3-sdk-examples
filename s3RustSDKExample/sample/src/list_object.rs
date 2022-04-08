use s3::Client;
use std::process;

pub async fn listobject(client: Client, bucket: String) {
    match client.list_objects().bucket(&bucket).send().await {
        Ok(resp) => {
            println!("{} Objects:", bucket);
            for object in resp.contents.unwrap_or_default() {
                println!("  {}", object.key.expect("objects have keys"));
            }
            println!("");
        }
        Err(e) => {
            println!("Got an error retrieving objects for bucket");
            println!("{}", e);
            process::exit(1);
        }
    }
}
