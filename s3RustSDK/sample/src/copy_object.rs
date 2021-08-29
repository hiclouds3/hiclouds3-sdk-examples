use std::process;
use s3::Client;

pub async fn copyobject(client:Client,bucket:String,key:String,copy_source:String,){
    match client
        .copy_object()
        .bucket(&bucket)
        .key(&key)
        .copy_source(&copy_source)
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