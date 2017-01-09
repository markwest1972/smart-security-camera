# aws-lambda-functions

This directory contains AWS lambda function definitions used by the smart-security-camera project.  These functions trigger, or are triggered by the the step function defined in the [aws-step-functions module](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions).

## Contents

1. **s3-trigger-image-processing.js** - Triggered when a new image is uploaded to an s3 upload folder.  Calls the step function defined in the [aws-step-functions module](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions).
2. **rekognition-image-assessment.js** - Returns a list of labels describing each uploaded picture.
3. **evaluate-rekognition-labels.js** - Evaluates labels to find out if an alarm email should be sent.
4. **[nodemailer-send-notification](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodemailer-send-notification)** - Sends an alarm email when the smart security camera detects a person.
5. **s3-archive-image.js** - Moves the processed image to the s3 archive folder.
6. **[nodemailer-error-handler](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodemailer-error-handler)** - Sends emails when run time errors occur.

## How to use

### Prequisites

1. All the prerequistes specified on the [repository readme.md](https://github.com/markwest1972/smart-security-camera).
2. You'll need to be logged in to the [AWS Lambda Function console](https://console.aws.amazon.com/lambda/home).

### IAM Roles

Depending on what they do, Lambda functions access to various AWS resources.  This access is granted through Roles.  Each Lambda Function must have a Role assigned to them.  These Roles contain all the "sub-roles" that give access to the required AWS Resources.

In theory one can create a "super role" that gives all Lambda Functions access to everything.  This may be tempting, but goes against best practice and exposes potential security issues.

Therefore one should build up a unique role for each specific combination of resources.  This can be done via the [AWS Console](https://aws.amazon.com/console/)).  All Lambda Functions should include *AWSLambdaBasicExecutionRole* by default as this allows them to amongst other things create logs in AWS Cloudwatch. 

If we look closely at the requirements for each Lambda Function they look as follows:

1. s3-trigger-image-processing.js - requires access to s3 ().
2. rekognition-image-assessment.js - requires access to s3 and to rekognition (*AmazonS3ReadOnlyAccess* and *AmazonRekognitionReadOnlyAccess*).
3. evaluate-rekognition.js - requires no extra access privileges.
4. nodemailer-send-notification.js - requires access to SES and s3  (*AmazonSESFullAccess* and *AmazonS3ReadOnlyAccess*).
5. s3-archive-image.js - requires access to s3 (*AmazonS3FullAccess*).
6. nodemailer-error-handler - requires access to SES  (*AmazonSESFullAccess*).

### Adding Lamdba Functions to AWS

There are two ways of specifying adding Node.js code to Lambda Functions - either raw code/edit in browser or zip files.  Raw code/edit in browsers works for those functions that require additional libraries (the aws sdk is available by default).  Zip files are for those functions that use third party libraries.

#### Plain text (edit in browser)


#### Zip Files


#### Plain text (edit in browser)

