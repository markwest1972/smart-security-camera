# evaluate-rekognition-labels

This directory contains an AWS Lamdba Definition that evaluates whether or not to send an alert email for an uploaded picture.

## Contents

1. **evaluate-rekognition-labels.js** - source code.

## How to use

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AWSLambdaBasicExecutionRole" permission. 

### Upload to AWS

Using the [AWS Lambda Console](https://aws.amazon.com/lambda), create a new Lambda Function called *evaluate-rekognition-labels* and copy the code from evaluate-rekognition-labels.js directly into the inline code editor.

Ensure that the function uses your newly created IAM Role.

Tip: Use the "blank function" blueprint and skip the "configure triggers" prompt when creating the function.
