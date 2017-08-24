# s3-archive-image

Archives files after successfull processing (moves them from "upload" to the relevant "archive" subdirectory in s3).

## Contents

1. **S3ArchiveImageHandler.java** - source code.
2. **pom.xml** - Maven pom file.

## How to use

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AmazonS3FullAccess" and "AWSLambdaBasicExecutionRole" permissions. 

### Upload to AWS

Using the [Eclipse AWS Toolkit](http://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/welcome.html), create a new Lambda Function called *s3-archive-image-java* using the Java source and pom file.

Ensure that the function uses your newly created IAM Role.
