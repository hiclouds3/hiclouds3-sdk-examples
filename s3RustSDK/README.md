# hicloud S3 Rust SDK

## Environment setup
* cargo >= 1.53.0
* rustc >= 1.53.0

## Rust SDK Serial Test Read me
* Fill in AccessKey, SecretKey in「sample/src/client.rs」
	```rust
	let credentials = Credentials::new("Enter you access key id","Enter you secret access key", None,None, "STATIC_CREDENTIALS");
	```
* Fill in canonical user ID in「sample/src/bucket_logging_serial_testing.rs」
	```rust
	let grantee=Grantee::builder()
        .id("Enter the canonical user ID of the grantee")
	```
* Set bucketName and filePath information in「sample/src/main.rs」
	```rust
	let bucket1="Enter your bucket1 name";
    let bucket2="Enter your bucket2 name";
    let key="Enter your key name";
	```
* If you run「sample/src/main.rs」,all the sample programs will be run
	```sh
	$ cd sample/
	$ cargo run
	```
