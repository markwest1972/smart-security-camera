# smart-security-camera

This project elevates a [Pi Zero simple webcamera with Motion Detection](https://utbrudd.bouvet.no/2017/01/05/building-a-motion-activated-security-camera-with-the-raspberry-pi-zero/) into a smart security camera by adding cloud based image analysis via [AWS Rekognition](https://aws.amazon.com/rekognition/).

** A new blog post that explains the contents of this repository is coming soon.**

## Contents

1. **[s3-upload](https://github.com/markwest1972/smart-security-camera/tree/master/s3-upload)**: Handles upload of image files from Pi Zero to Amazon s3.
2. **motion-config** *(coming)*: Configuration file for Motion (running on a Pi Zero).
3. **[aws-lambda-functions](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions)**: Source code for all aws lambda functions for handling image analysis and processing. JSON definition for orchestration of AWS Lambda Functions.
4. **[aws-step-functions](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions)**: Source code for orchestration of AWS Lambda Functions.

## How to use

Each subdirectory in this repository has simple instructions.  Note that there are naming dependancies in this project, so make sure that any naming changes are consistent.

### Prerequisites

1. You'll need [AWS Credentials](http://docs.aws.amazon.com/gettingstarted/latest/awsgsg-intro/gsg-aws-intro.html).
2. You'll need to be using a AWS Region that supports Rekognition, Step Functions, Lambda, s3 and SES (for example 'eu-west-1').
3. You'll need an [s3 bucket](https://aws.amazon.com/documentation/s3/) where your images can be uploaded for processing.
4. In the preferences for your s3 Bucket, grant 'List' permission to any authenticated AWS user.  This is a temporary workaround that prevents a 403 error when using a URL to add attachements to Alert emails.
