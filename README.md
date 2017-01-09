# smart-security-camera

This project elevates a simple webcemera with motion detection into a smart security camera, by adding cloud based image analysis.

More information about the motivation for this project can be found here : https://utbrudd.bouvet.no/2017/01/05/building-a-motion-activated-security-camera-with-the-raspberry-pi-zero/.

A new blog post that explains the contents of this repository is coming soon.

## Contents

1. **[s3-upload](https://github.com/markwest1972/smart-security-camera/tree/master/s3-upload)**: Handles upload of image files from Pi Zero to Amazon s3.
2. **motion-config** *(coming)*: Configuration file for Motion (running on a Pi Zero).
3. **[aws-lambda-functions](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions)**: Source code for all aws lambda functions for handling image analysis and processing. JSON definition for orchestration of AWS Lambda Functions.
4. **[aws-step-functions](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions)**: Source code for orchestration of AWS Lambda Functions.

### How to use

Each subdirectory has simple instructions for using it's contents.

## Prerequisites

1. You'll need [AWS Credentials and AWS Locale](http://docs.aws.amazon.com/gettingstarted/latest/awsgsg-intro/gsg-aws-intro.html).
2. You'll need to have set up an [s3 bucket](https://aws.amazon.com/documentation/s3/).
