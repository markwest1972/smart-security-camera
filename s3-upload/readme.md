
# s3-upload 

This directory contains resources for uploading files to Amazon s3.  These are primarily meant for use with the smart-security-camera project, but can be used for any s3 uploading requirements.

## Pre requisites

1. AWS 
2. s3 Bucket with upload/ directory at root.
3. Recent node and npm verson.

## Contents

* config.json - aws credentials
* package.json - aws node dependancies
* s3-upload-file.js - node.js script
* process-file.sh - bash script for running node.js script and removing file after upload

## How to use

1. Clone this directory and it's to your machine - for example to "/usr/local/bin/scripts/".
2. Run "npm install" to install node.js AWS dependancies.
3. Update config.json with your AWS credentials.
4. Run "sudo chmod +x" on both the sh and js files.
5. Test the script by running "process-file.sh 
