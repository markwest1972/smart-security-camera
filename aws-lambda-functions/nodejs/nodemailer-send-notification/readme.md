
# nodemailer-send-notification

This directory contains an AWS Lamdba Definition for sending Alert Emails (with image attachment) for the smart-security-camera project.

## Contents

1. **index.js** - source code.
2. **package.json** - npm dependencies.

## How to use

### Creating a Zip File

This function uses third party libraries.  This means that we have to create a zip file containing the code and all dependancies.

1. Get a local copy of this directory by using git clone.
2. Use "npm install" to download dependencies.
3. Create a Zip file of index.js, package.json and the node_modules directory.  Note that these should be at the root of the Zip File.

### Memory Configuration

This function often times out with the default memory allocation (128).  Increasing the memory allocation from 128 to 256 normally resolves this issue.

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AmazonS3ReadOnlyAccess", "AmazonSESFullAccess" and "AWSLambdaBasicExecutionRole" permissions.

### Environment Variables

The variables EMAIL_RECIPIENT and EMAIL_FROM need to be declared as Environment Variables and defined in the Lambda Function console.  These should point to the _recipient_ of your emails and the _from_ addresses respectively. Remember that the EMAIL_FROM address [needs to be verified in SES](http://docs.aws.amazon.com/ses/latest/DeveloperGuide/verify-email-addresses.html).

You'll also need to define the process.env.S3_URL_PREFIX environment variable. This points to the S3 region where your bucket is located.  For example mine is set to 'https://s3-eu-west-1.amazonaws.com/'.

### Upload to AWS

Using the [AWS Lambda Console](https://aws.amazon.com/lambda), create a new Lambda Function called *nodemailer-send-notification* which uses the Zip file and IAM Role that you have just created.

Tip: Use the "blank function" blueprint and skip the "configure triggers" prompt.

### Acknowledgement

Thanks to Matt Dorn, who contributed a version of this code that provides a more user friendly printout of the Rekogniton labels.
