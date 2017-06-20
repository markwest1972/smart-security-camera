# aws-lambda-functions (Java)

This directory contains Java based AWS Lambda Function definitions used by the smart-security-camera project.  [Node.js versions are also available](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs).

These functions trigger, or are triggered by the the step function defined in the [aws-step-functions module](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions/java).

For simplicity, each Lambda Function is located in it's own subdirectory, along with it's own set up instructions.

## Contents

Calls the step function (should be implemented after the step function):

1. **[s3-trigger-image-processing](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/s3-trigger-image-processing)** - Triggered when a new image is uploaded to an s3 upload folder.  

Called by the step function (should be implemented before the step function):

1. **[rekognition-image-assessment](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/rekognition-image-assessment)** - Returns a list of labels describing each uploaded picture.
2. **[rekognition-evaluate-labels](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/rekognition-evaluate-labels)** - Evaluates labels to find out if an alarm email should be sent.
3. **[ses-send-notification](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/ses-send-notification)** - Sends an alarm email when the smart security camera detects a person.
4. **[s3-archive-image](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/s3-archive-image)** - Moves the processed image to the s3 archive folder.
5. **[ses-error-handler](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/ses-error-handler)** - Sends emails when run time errors occur.

POJO for communication between Jaav based Lambda Functions:

* **[lambda-parameters](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/lambda-parameters)** - Should be included in each Java Lambda Function upload.

## Timeouts

When configuring the Lambda Functions you should mke sure that they have a timeout of at least 15 seconds.  This is most relevant for those Lambda Functions that call other AWS services such as Rekognition or SES.  Increasing the timeout will prevent your Lambda Function from exiting while it awaits a response.

## IAM Roles and Lambda Functions

Depending on what they do, Lambda functions require access to various AWS resources. This access is granted through Roles. Each Lambda Function must have one Role assigned to them. These Roles contain all the "sub-roles" that give access to specific AWS Resources.

In theory one can create a single "super role" that gives all Lambda Functions access to everything. This may be tempting, but goes against best practice and can pose a security risk.

Therefore one should build up a unique role for each specific combination of resources.  This can be done via the [AWS Console](https://aws.amazon.com/console/).  

The relevant IAM Role requirements are located in the readme for each function.

## Environment variables

Certain configuration strings have been moved out of the code and into [Environment Variables](http://docs.aws.amazon.com/lambda/latest/dg/env_variables.html).  These Environment Variables should be set for each Lambda Function.  See the Readme files for each Lambda Function to find out which (if any) variables need to be set up.
