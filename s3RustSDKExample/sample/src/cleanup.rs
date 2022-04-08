use s3::Client;
use std::process;

pub async fn cleanup(client: Client) {
    match client.list_buckets().send().await {
        Ok(resp) => {
            let buckets = resp.buckets.unwrap_or_default();
            for bucket in &buckets {
                match &bucket.name {
                    None => {}
                    Some(b) => match client.list_objects().bucket(b).send().await {
                        Ok(resp) => {
                            let objects = resp.contents.unwrap_or_default();
                            for object in &objects {
                                match &object.key {
                                    None => {}
                                    Some(k) => {
                                        match client.delete_object().bucket(b).key(k).send().await {
                                            Ok(_) => {
                                                println!("Deleted object {}/{}\n", *b, *k);
                                            }
                                            Err(e) => {
                                                println!("Got an error deleting object");
                                                println!("{}", e);
                                                process::exit(1);
                                            }
                                        }
                                    }
                                }
                            }
                            match client.delete_bucket().bucket(b).send().await {
                                Ok(_) => {
                                    println!("Deleted bucket name: {}\n", *b);
                                }
                                Err(e) => {
                                    println!("Got an error deleting bucket");
                                    println!("{}", e);
                                    process::exit(1);
                                }
                            };
                        }
                        Err(e) => {
                            println!("Got an error get objects for bucket");
                            println!("{}", e);
                            process::exit(1);
                        }
                    },
                }
            }
        }
        Err(e) => {
            println!("Got an error list buckets");
            println!("{}", e);
            process::exit(1);
        }
    };
}
