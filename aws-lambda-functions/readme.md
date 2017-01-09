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

### IAM Roles

### Upload to AWS Lambda

#### Zip Files


#### Plain text (edit in browser)
