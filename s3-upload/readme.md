
# s3-upload 

This directory contains resources for uploading files to Amazon s3.  These are primarily meant for use with the smart-security-camera project, but can be used for any s3 uploading requirements.

## Pre requisites

1. [AWS Credentials](http://docs.aws.amazon.com/general/latest/gr/aws-sec-cred-types.html#access-keys-and-secret-access-keys).
2. AWS s3 Bucket with an upload/ directory at root.
3. [A recent node and npm verson](https://github.com/sdesalas/node-pi-zero).

## Contents

* **config.json** - contains aws credentials
* **package.json** - contains aws node dependancies
* **s3-upload-file.js** - node.js script that uploads file
* **process-file.sh** - bash script for running node.js script and removing file after upload

## How to use

1. Clone this directory and it's contents to your machine - for example to "*/usr/local/bin/scripts/*".
2. Run "*npm install*" to install node.js AWS dependancies.
3. Update config.json with your AWS credentials.
4. Run "*sudo chmod +x*" on both the sh and js files (just to make sure that they can be run).
5. Test the script by running "*./process-file.sh your-bucket-name file-to-upload*".  Your file should have been uploaded to s3 and the local copy deleted.
