use s3_code_examples::client;
use s3_code_examples::create_bucket::createbucket;
use s3_code_examples::get_bucket_acl::getbucketacl;
use s3_code_examples::delete_bucket::deletebucket;
use s3_code_examples::bucket_logging_serial_testing::bucketloggingserialtesting;
use s3_code_examples::list_bucket::listbucket;
use s3_code_examples::lifecycle_serial_testing::lifecycleserialtesting;
use s3_code_examples::policy_serial_testing::policyserialtesting;
use s3_code_examples::version_serial_testing::versionserialtesting;
use s3_code_examples::put_object::putobject;
use s3_code_examples::list_object::listobject;
use s3_code_examples::delete_object::deleteobject;
use s3_code_examples::copy_object::copyobject;
use s3_code_examples::get_object_acl::getobjectacl;

#[tokio::main]
async fn main(){
    let bucket1="yuyuman1";
    let bucket2="yuyuman2";
    let key="HelloWorld.txt";
    createbucket(client::client(),client::region(),String::from(bucket1)).await;
    createbucket(client::client(),client::region(),String::from(bucket2)).await;
    listbucket(client::client()).await;
    getbucketacl(client::client(),String::from(bucket1)).await;
    getbucketacl(client::client(),String::from(bucket2)).await;
    bucketloggingserialtesting(client::client(),String::from(bucket1),String::from(bucket2)).await;
    lifecycleserialtesting(client::client(),String::from(bucket1)).await;
    putobject(client::client(),String::from(bucket1),String::from(key)).await;
    copyobject(client::client(),String::from(bucket1),String::from(bucket2),String::from(key)).await;
    listobject(client::client(),String::from(bucket1)).await;
    listobject(client::client(),String::from(bucket2)).await;
    getobjectacl(client::client(),String::from(bucket1),String::from(key)).await;
    getobjectacl(client::client(),String::from(bucket2),String::from(key)).await;
    policyserialtesting(client::client(),String::from(bucket1)).await;
    deleteobject(client::client(),String::from(bucket1),String::from(key)).await;
    deleteobject(client::client(),String::from(bucket2),String::from(key)).await;
    deletebucket(client::client(),String::from(bucket1)).await;
    deletebucket(client::client(),String::from(bucket2)).await;
    createbucket(client::client(),client::region(),String::from(bucket1)).await;
    versionserialtesting(client::client(),String::from(bucket1)).await;
    deletebucket(client::client(),String::from(bucket1)).await;
}