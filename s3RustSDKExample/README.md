# hicloud S3 Rust SDK Examples

## Prerequisites
* Rust
* Cargo

See https://doc.rust-lang.org/book/ch01-01-installation.html

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

* If you run「sample/src/main.rs」,all the sample programs will be run
	```sh
	$ cd sample/
	$ cargo run
	```
