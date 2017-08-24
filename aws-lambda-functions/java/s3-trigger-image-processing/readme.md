# s3-trigger-image-processing

Monitors the S3 buckets "upload" directory for new image files and triggers their processing by calling the [step function](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions).

## Contents

1. **S3TriggerImageProcessingHandler.java** - source code.

## How to use

### IAM Role

1. Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AWSLambdaBasicExecutionRole" and "AmazonS3FullAccess" permissions.
2. Using the [AWS IAM Console](https://aws.amazon.com/console/) you also need to manually add an inline policy to your new created IAM Role, giving permission to run Step Functions:
```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "states:StartExecution"
            ],
            "Resource": [
                "*"
            ]
        }
    ]
}
```

### Environment Variables

The variable STEP_MACHINE_ARN needs to be declared and defined in the Lambda Function console.  This should point to the ARN of your Step Function.

### Upload to AWS

1. Using the [Eclipse AWS Toolkit](http://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/welcome.html), create a new Lambda Function called *s3-trigger-image-processing-java* using the Java source and pom file.
2. Ensure that the new Lambda Function uses your newly created IAM Role.
3. Create a trigger (that will call this function) for your S3 bucket with the event type "Object Create (All)" and the prefix "upload/". This will ensure that your function is run for each new item uploaded to the "upload" directory of your s3 bucket.
