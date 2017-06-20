# rekognition-image-assessment

Sends image from s3 to Amazon Rekognition for Image Processing.  

## Contents

1. **RekognitionImageAssessmentHandler.Java** - source code.
2. **pom.xml** - Maven pom file.

## How to use

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AmazonRekognitionReadOnlyAccess", "AmazonS3ReadOnlyAccess" and "AWSLambdaBasicExecutionRole" permissions. 

### Upload to AWS

Using the [Eclipse AWS Toolkit](http://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/welcome.html), create a new Lambda Function called *rekognition-image-asessment-java* using the Java source and pom file.

Ensure that the function uses your newly created IAM Role.
