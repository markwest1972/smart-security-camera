
# nodemailer-send-notification

This directory contains an AWS Lamdba Definition for sending Alert Emails (with image attachment) for the smart-security-camera project.

## Contents

1. **index.js** - Node.js code triggered by exceptions in the processing of incoming images.
2. **package.json** - npm dependancies.

## How to use

### Creating a Zip File

This function uses third party libraries.  This means that we have to create a zip file containing the code and all dependancies.

1. Get a local copy of this directory by using git clone.
2. Use "npm install" to download dependancies.
3. Create a Zip file of index.js, package.json and the node_modules directory.  Note that these should be at the root of the Zip File.

### Memory Configuration

When configuring the Lambda Function, increase the memory allocation from 128 to 256.

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AmazonS3ReadOnlyAccess", "AmazonSESFullAccess" and "AWSLambdaBasicExecutionRole" permissions. 

### Upload to AWS

Using the [AWS Lambda Console](https://aws.amazon.com/lambda), create a new Lambda Function called *nodemailer-send-notification* which uses the Zip file and IAM Role that you have just created.

Tip: Use the "blank function" blueprint and skip the "configure triggers" prompt.
