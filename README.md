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

It is left to the user to work some details out for themselves.  If you have problems, use the AWS Help Pages for support.

### Prerequisites

The following prerequisites are required for working with this repository.

##### AWS Credentials

1. [AWS Credentials](http://docs.aws.amazon.com/gettingstarted/latest/awsgsg-intro/gsg-aws-intro.html).
2. You'll also need to be using a AWS Region that supports Rekognition, Step Functions, Lambda, s3 and SES (for example 'eu-west-1').

##### s3 Bucket

1. An [s3 bucket](https://aws.amazon.com/documentation/s3/) where your images can be uploaded for processing.
2. The bucket will need two root directories : "/upload" and "/archive". 
3. In the preferences for your s3 Bucket, grant 'List' permission to any authenticated AWS user.  This is a temporary workaround that prevents a 403 error when using a [URL to add attachments to Alert emails](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodemailer-send-notification).

##### Recent Version of Node.js

1. [A recent node and npm verson](https://github.com/sdesalas/node-pi-zero) on your Pi Zero.
2. A recent node and npm verson on your build machine.
