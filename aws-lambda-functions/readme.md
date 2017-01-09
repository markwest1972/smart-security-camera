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

Lambda functions require an IAM role to access the relevant AWS resources.  This roles can be added via the [AWS Console](https://aws.amazon.com/console/)).  The best practice is to create a role for each specific combination of resources.  Note that all Lambda Functions should have *AWSLambdaBasicExecutionRole* by default as this allows them to amongst other things create logs in AWS Cloudwatch.

For example:

1. s3-trigger-image-processing.js needs access to s3 ().
2. rekognition-image-assessment.js needs access to s3 and to rekognition (*AmazonS3ReadOnlyAccess* and *AmazonRekognitionReadOnlyAccess*).
3. evaluate-rekognition.js needs access to ().
4. nodemailer-send-notification.js needs access to SES and s3  ().
5. s3-archive-image.js needs  access to s3 ().
6. nodemailer-error-handler needs access to SES  ().

If you in a hurry you can just create one "super role" that includes all the resources your project will be using and then assign this role to all your functions.

### Adding Lamdba Functions to AWS

There are two ways of specifying adding Node.js code to Lambda Functions - either raw code/edit in browser or zip files.  Raw code/edit in browsers works for those functions that require additional libraries (the aws sdk is available by default).  Zip files are for those functions that use third party libraries.

#### Plain text (edit in browser)


#### Zip Files


#### Plain text (edit in browser)

