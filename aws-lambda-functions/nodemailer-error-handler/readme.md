# nodemailer-error-handler

This directory contains an AWS Lamdba Definition for sending Error Emails for the smart-security-camera project.

## Contents

1. **index.js** - Node.js code triggered by exceptions in the processing of incoming images.
2. **package.json** - npm dependancies.

## Prerequisites 

1. All the prerequisites specified on the [repository readme.md](https://github.com/markwest1972/smart-security-camera).
2. [A recent node and npm verson](https://github.com/sdesalas/node-pi-zero) on your build machine.

## How to use

### Creating a Zip File

This function uses third party libraries.  This means that we have to create a zip file containing the code and all dependancies.

1. Get a local copy of this directory by using git clone.
2. Use "npm install" to download dependancies.
3. Create a Zip file of index.js, package.json and the node_modules directory.  Note that these should be at the root of the Zip File.

### IAM Role

Using the [AWS IAM Console](https://aws.amazon.com/console/) create an IAM Role containing the "AmazonSESFullAccess" and "AWSLambdaBasicExecutionRole" permissions. 

### Upload to AWS

Using the [AWS Lambda Console](https://aws.amazon.com/lambda), create a new Lambda Function called *nodemailer-error-handler* which uses the Zip file and IAM Role that you have just created.
