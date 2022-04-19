# hicloud S3 Sample Codes

This repository intends to demonstrate how to perform several S3-compatible operations on **[hicloud S3](http://hicloud.hinet.net/hicloud_s3_about.html)** using AWS SDKs for various languages.

## Getting Started

### hicloud S3 Account and Credentials
To access hicloud S3, you will need to sign up for a hicloud S3 account.

To sign up for a hicloud S3 account
1.	Go to https://userportal.hicloud.hinet.net/cloud/
2.	Follow the on-screen instructions.

When you sign up, hicloud S3 provides you with security credentials that are specific to your account. Two of these credentials, your access key ID and your secret key, are used by the SDK whenever it accesses to hicloud S3. The security credentials authenticate requests to the service and identify you as the sender of a request. The following examples show these credentials.

-	Access Key ID Example: U0U03U5UQXdNREl4TkRFek5UZ3hNek0yTURZd09EWJ0
-	Secret Key Example: WW1NNU9XVTNOLFF34kRNMk5DRTVaV0ZrTVRjMFpEVTFOV1kxWkRZeFlUTT0

Your secret key must remain a secret that is known only by you and hicloud S3. Keep it confidential in order to protect your account. Store it securely in a safe place, and never email it. Do not share it outside your organization, even if an inquiry appears to come from hicloud S3.No one who legitimately represents hicloud S3 will ever ask you for your secret key.

### SDK Environment Setup & Running Sample Codes
Inside each language-specific directory, we include a README file explaining:
- how to configure your environment settings for hicloud S3
- how to build & run the sample codes

Currently, we provide code samples tested on the SDKs as follows:
- Go https://github.com/aws/aws-sdk-go-v2/releases/tag/v1.7.1
- Java https://github.com/aws/aws-sdk-java/releases/1.12.61
- Javascript https://github.com/aws/aws-sdk-js/releases/tag/v2.1.39
- .NET https://github.com/aws/aws-sdk-net/tree/aws-sdk-net-v1
- PHP https://github.com/aws/aws-sdk-php/releases/tag/2.8.31
- Python3 https://github.com/boto/boto3/releases/tag/1.17.105
- Ruby https://github.com/aws/aws-sdk-ruby/tree/version-3
- Rust https://github.com/awslabs/aws-sdk-rust/tree/v0.0.19-alpha
