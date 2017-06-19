# rekognition-image-assessment

Sends image from s3 to Amazon Rekognition for Image Processing.  

## Contents

1. **rekognition-image-assessment.js** - source code.

## How to use

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AmazonRekognitionReadOnlyAccess", "AmazonS3ReadOnlyAccess" and "AWSLambdaBasicExecutionRole" permissions. 

### Upload to AWS

Using the [AWS Lambda Console](https://aws.amazon.com/lambda), create a new Lambda Function called *rekognition-image-assessment* and copy the code from rekognition-image-assessment.js directly into the inline code editor.

Ensure that the function uses your newly created IAM Role.

Tip: Use the "blank function" blueprint and skip the "configure triggers" prompt when creating the function.
