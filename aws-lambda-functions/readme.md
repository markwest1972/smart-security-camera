# aws-lambda-functions

This directory contains AWS Lambda Function definitions used by the smart-security-camera project.  

These functions trigger, or are triggered by the the step function defined in the [aws-step-functions module](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions).

For simplicity, each Lambda Function is located in it's own subdirectory, along with it's own set up instructions.

## Contents

1. **[s3-trigger-image-processing.js](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/s3-trigger-image-processing)** - Triggered when a new image is uploaded to an s3 upload folder.  Calls the step function defined in the [aws-step-functions module](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions).
2. **[rekognition-image-assessment.js](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/rekognition-image-assessment)** - Returns a list of labels describing each uploaded picture.
3. **[evaluate-rekognition-labels.js](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/evaluate-rekognition-labels)** - Evaluates labels to find out if an alarm email should be sent.
4. **[nodemailer-send-notification](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodemailer-send-notification)** - Sends an alarm email when the smart security camera detects a person.
5. **[s3-archive-image.js](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/s3-archive-image)** - Moves the processed image to the s3 archive folder.
6. **[nodemailer-error-handler](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodemailer-error-handler)** - Sends emails when run time errors occur.

## IAM Roles and Lambda Functions

Depending on what they do, Lambda functions require access to various AWS resources. This access is granted through Roles. Each Lambda Function must have one Role assigned to them. These Roles contain all the "sub-roles" that give access to specific AWS Resources.

In theory one can create a single "super role" that gives all Lambda Functions access to everything. This may be tempting, but goes against best practice and can pose a security risk.

Therefore one should build up a unique role for each specific combination of resources.  This can be done via the [AWS Console](https://aws.amazon.com/console/)).  

The relevant IAM Role requirements are located in the readme for each function.
