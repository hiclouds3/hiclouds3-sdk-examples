use std::process;
use s3::Client;

pub async fn policyserialtesting(client:Client,bucket:String) {
    match client
        .put_bucket_policy()
        .policy("{\"Version\": \"2012-10-17\", \"Statement\": [{ \"Sid\": \"DenyPublicREAD\",\"Effect\": \"Deny\",\"Principal\": {\"AWS\": \"*\"}, \"Action\": \"s3:GetObject\", \"Resource\": [\"arn:aws:s3:::yuyuman1/*\" ] } ]}")
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
