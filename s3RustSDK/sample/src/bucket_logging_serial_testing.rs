use std::process;
use s3::model::{BucketLoggingStatus, LoggingEnabled, TargetGrant, Grantee,BucketLogsPermission,Type};
use s3::Client;

pub async fn bucketloggingserialtesting(client:Client,bucket:String) {
    let r#type = Type::CanonicalUser;
    let grantee=Grantee::builder()
        .id("d1e8b71ffe12661ac2df52d5a96fbf5d6188dace9657d120c8253a61394bc51d")
        .r#type(r#type)
        .build();
    let premission = BucketLogsPermission::FullControl;
    let targetgrant=TargetGrant::builder()
        .grantee(grantee)
        .permission(premission)
        .build();
    let loggingenable=LoggingEnabled::builder()
        .target_bucket("yuyuman2")
        .target_grants(targetgrant)
        .target_prefix("MyBucketLogs/")
        .build();
    let bucketloggingstatus=BucketLoggingStatus::builder()
        .logging_enabled(loggingenable)
        .build();
    match client
        .put_bucket_logging()
        .bucket_logging_status(bucketloggingstatus)
        .bucket(&bucket)
        .send()
        .await 
    {
        Ok(_) => {
            println!("Created {} Logging\n", bucket);
        }
        Err(e) => {
            println!("Got an error creating bucket Logging");
            println!("{}", e);
            process::exit(1);
        }
    };
}