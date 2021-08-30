use s3::Client;
use std::process;

pub async fn policyserialtesting(client: Client, bucket: String) {
    match client
        .put_bucket_policy()
        .policy(String::from("{\"Version\": \"2012-10-17\", \"Statement\": [{ \"Sid\": \"DenyPublicREAD\",\"Effect\": \"Deny\",\"Principal\": {\"AWS\": \"*\"}, \"Action\": \"s3:GetObject\", \"Resource\": [\"arn:aws:s3:::") + &bucket + &"/*\" ] } ]}")
        .bucket(&bucket)
        .send()
        .await
    {
        Ok(_) => {
            println!("Created {}'s policy\n", bucket);
        }
        Err(e) => {
            println!("Got an error creating bucket policy");
            println!("{}", e);
            process::exit(1);
        }
    };
}
