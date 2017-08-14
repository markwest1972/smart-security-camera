# smart-security-camera

This project elevates a [Pi Zero simple webcamera with Motion Detection](https://utbrudd.bouvet.no/2017/01/05/building-a-motion-activated-security-camera-with-the-raspberry-pi-zero/) into a smart security camera by adding Cloud based image analysis via [AWS Rekognition](https://aws.amazon.com/rekognition/).

You can read more about this solution in the following blog posts:
* [Smarten up Your Pi Zero Web Camera with Image Analysis and Amazon Web Services Part 1](https://utbrudd.bouvet.no/2017/01/10/smarten-up-your-pi-zero-web-camera-with-image-analysis-and-amazon-web-services-part-1).
* [Smarten up Your Pi Zero Web Camera with Image Analysis and Amazon Web Services Part 2](https://utbrudd.bouvet.no/2017/01/10/smarten-up-your-pi-zero-web-camera-with-image-analysis-and-amazon-web-services-part-2).

You can also check out [this presentation](https://www.youtube.com/watch?v=20H-7QOaPFs) from Riga Dev Days where I describe the solution.  The [slides from this talk are also available](https://www.slideshare.net/markawest/building-a-smart-security-camera-with-raspberry-pi-zero-nodejs-and-the-cloud-riga-dev-days-and-geecon).

## Java or Node.js?

Both __Java__ and __Node.js__ versions of the AWS Lambda Functions are provided.  Due to naming differences I have also provided seperate Step Function definitions for both the Java and Node.js versions.

Heres a [blogpost describing the differences between the two versions](https://www.bouvet.no/bouvet-deler/comparing-java-and-node.js-on-aws-lambda).

## Contents

1. **[s3-upload](https://github.com/markwest1972/smart-security-camera/tree/master/s3-upload)**: Handles upload of image files from Pi Zero to Amazon s3.
2. **[motion-config](https://github.com/markwest1972/smart-security-camera/tree/master/motion-config)**: Configuration files for Motion (running on a Pi Zero).
3. **[aws-lambda-functions](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions)**: Choose between Node.js or Java source code for all AWS lambda functions.
4. **[aws-step-functions](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions)**: JSON definitions for orchestration of AWS Lambda Functions.

## How to use

**If you think you've found a typo, or need help getting things to work, get in contact and I'll try to help!**

Each subdirectory in this repository has simple instructions.  Note that there are naming dependancies in this project, so make sure that any naming changes are applied across the repository.

All the code is provided as is, and it is left to the user to work out the fine details for themselves. The AWS documentation is very useful here. Remember that [GIYF](http://www.giyf.com) :)

### Prerequisites

The following prerequisites are required for working with this repository.

##### AWS Credentials

1. [AWS Credentials](http://docs.aws.amazon.com/gettingstarted/latest/awsgsg-intro/gsg-aws-intro.html).
2. You'll also need to be using a AWS Region that supports Rekognition, Step Functions, Lambda, s3 and SES (for example 'eu-west-1').

##### S3 Bucket

1. You'll need a [s3 bucket](https://aws.amazon.com/documentation/s3/) where your images can be uploaded for processing.
2. The bucket will need two root directories : "/upload" and "/archive".
3. Directly under the "/archive" directory, create the "/alerts" and "/falsepositives" subdirectories.
4. In the "Permissions->Bucket Policy" tab for your S3 Bucket, set up the following Bucket Policy:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": {
                "AWS": "*"
            },
            "Action": [
                "s3:Get*",
                "s3:List*"
            ],
            "Resource": [
                "arn:aws:s3:::your-bucket-name",
                "arn:aws:s3:::your-bucket-name/*"
            ]
        }
    ]
}
```
To make your S3 even more secure you can swop "*" with the full **ARN** for the **IAM role** associated with your [nodemailer-send-notification](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs/nodemailer-send-notification) or [ses-send-notification](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/ses-send-notification) Lambda Function.

## Suggested Implementation Plan 

1. [Set up your PiZero webcamera with Motion](https://utbrudd.bouvet.no/2017/01/05/building-a-motion-activated-security-camera-with-the-raspberry-pi-zero/).
2. Create an AWS account.
3. Create an S3 Bucket.
4. Implement [s3-upload](https://github.com/markwest1972/smart-security-camera/tree/master/s3-upload) from PiZero to S3 Bucket.
5. Implement all your [aws-lambda-functions](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions) and test them individually.
6. Implement your [aws-step-function](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions). Test it.
7. Set up the S3 Trigger ([Java](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/s3-trigger-image-processing) or [Node.js](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs/s3-trigger-image-processing)) that triggers the Step Function.
