use http::Uri;
use s3::Credentials;
use s3::{Client, Config, Endpoint, Region};

pub fn client() -> Client {
    let credentials = Credentials::new(
        "Enter you access key id",
        "Enter you secret access key",
        None,
        None,
        "STATIC_CREDENTIALS",
    );
    let region = region();
    let uri = "http://s3.hicloud.net.tw".parse::<Uri>().unwrap();
    let endpoint = Endpoint::mutable(uri);
    let config = Config::builder()
        .endpoint_resolver(endpoint)
        .credentials_provider(credentials)
        .region(region)
        .build();
    Client::from_conf(config)
}
pub fn region() -> Region {
    Region::new("us-east-1")
}
