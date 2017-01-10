# s3-trigger-image-processing

Monitors the s3 buckets "/upload" directory for new image files and triggers their processing by calling the [step function](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions).

## Contents

1. **s3-archive-image.js** - source code.

## How to use

### IAM Role

1. Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AWSLambdaBasicExecutionRole" permission. 
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

### Upload to AWS

1. Using the [AWS Lambda Console](https://aws.amazon.com/lambda), create a new Lambda Function, using the "blank function" blueprint.
2. Create a trigger for your s3 bucket with the event type "Object Create (All)" and the prefix "/upload".  This will ensure that this function is run for each new item uploaded to the "/upload" directory of your s3 bucket.
3. Once the trigger has been specified you can copy the code from s3-trigger-image-processing.js directly into the inline code editor.
4. You need to update the step machine arn reference (*stateMachineArn* variable) in the code.  At the time of writing it would seem that you actually need to run the step machine in the Step Machine Console in order to find the arn (look for the State Machine Arn and **not** the Execution ID). 
5. Ensure that the function uses your newly created IAM Role.
