# ses-error-handler

This directory contains an AWS Lambda Definition for sending Error Emails for the smart-security-camera project.

## Contents

1. **SesErrorHandler.java** - Java code triggered by exceptions in the processing of incoming images.
2. **pom.xml** - Maven build file.

## How to use

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AmazonSESFullAccess" and "AWSLambdaBasicExecutionRole" permissions. 

### Environment Variables

The variables EMAIL_RECIPIENT and EMAIL_FROM need to be declared as Environment Variables and defined in the Lambda Function console.  These should point to the _recipient_ of your emails and the _from_ addresses respectively. Remember that the EMAIL_FROM address [needs to be verified in SES](http://docs.aws.amazon.com/ses/latest/DeveloperGuide/verify-email-addresses.html).

### Upload to AWS

Using the [Eclipse AWS Toolkit](http://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/welcome.html), create a new Lambda Function called *ses-error-handler-java* using the Java source and Pom file.

Remember to also use the IAM Role that you have just created.
