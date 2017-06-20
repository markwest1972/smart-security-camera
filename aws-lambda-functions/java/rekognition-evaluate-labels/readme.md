# rekognition-evaluate-labels

This directory contains an AWS Lamdba Definition that evaluates whether or not to send an alert email for an uploaded picture.

## Contents

1. **RekognitionEvaluateLabelsHandler.java** - source code.
2. **pom.xml** - Maven pom file.

## How to use

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AWSLambdaBasicExecutionRole" permission.

### Upload to AWS

Using the [Eclipse AWS Toolkit](http://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/welcome.html), create a new Lambda Function called *rekognition-evaluate-labels-java* using the Java source and pom file.

Ensure that the function uses your newly created IAM Role.
