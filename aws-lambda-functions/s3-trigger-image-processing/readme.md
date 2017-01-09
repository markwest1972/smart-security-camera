# /s3-trigger-image-processing

Monitors the s3 /upload for new image files and triggers their processing by calling the [step function](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions).

## Contents

1. s3-archive-image.js - source code.

## How to use

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AWSLambdaBasicExecutionRole" permission. 

### Upload to AWS

Using the [AWS Lambda Console](https://aws.amazon.com/lambda), create a new Lambda Function called *s3-trigger-image-processing*.


Once the trigger has been specified you can copy the code from s3-trigger-image-processing.js directly into the inline code editor.

Warning: You'll need to update the arn....

Tip: Use the "blank function" blueprint,.

