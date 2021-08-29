use s3::Credentials;
use s3::{Client, Config, Region};

pub fn client() -> Client {
    let credentials = Credentials::new("","", None,None, "STATIC_CREDENTIALS");
    let region = region();
    let config = Config::builder()
        .credentials_provider(credentials)
        .region(region)
        .build();
    Client::from_conf(config)
}
pub fn region() -> Region{
    Region::new("ap-northeast-1")
}