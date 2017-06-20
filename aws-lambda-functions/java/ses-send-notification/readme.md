
# ses-send-notification

This directory contains an AWS Lamdba Definition for sending Alert Emails (with image attachment) for the smart-security-camera project.

## Contents

1. **SesSendNotificationHandler.java** - Java code sending alerts from smart camera.
2. **pom.xml** - Maven build file.

## How to use

### Memory Configuration

This function often times out with the default memory allocation (128).  Increasing the memory allocation from 128 to 256 normally resolves this issue.

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AmazonS3ReadOnlyAccess", "AmazonSESFullAccess" and "AWSLambdaBasicExecutionRole" permissions. 

### Environment Variables

The variables EMAIL_RECIPIENT and EMAIL_FROM need to be declared as Environment Variables and defined in the Lambda Function console.  These should point to the _recipient_ of your emails and the _from_ addresses respectively. Remember that the EMAIL_FROM address [needs to be verified in SES](http://docs.aws.amazon.com/ses/latest/DeveloperGuide/verify-email-addresses.html).

You'll also need to define the process.env.S3_URL_PREFIX environment variable. This points to the S3 region where your bucket is located.  For example mine is set to 'https://s3-eu-west-1.amazonaws.com/'. 

### Upload to AWS

Using the [Eclipse AWS Toolkit](http://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/welcome.html), create a new Lambda Function called *ses-send-notification-java* using the Java source and pom file.

Ensure that the new Lambda Function uses your newly created IAM Role.
