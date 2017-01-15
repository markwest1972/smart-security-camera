
# s3-upload 

This directory contains resources for uploading files to Amazon s3.  These are primarily meant for use with the smart-security-camera project, but can be used for any s3 uploading requirements.

## Contents

* **config.json** - contains AWS credentials.
* **package.json** - contains AWS node dependancies.
* **s3-upload-file.js** - node.js script that uploads file.
* **process-file.sh** - bash script for running node.js script and removing file after upload.  Called by Motion according to the *on_picture_save* parameter in Motions configuration file. 

## How to use

Assuming that you are uploading from a Raspberry Pi Zero:

1. Use Git to clone this directory and it's contents to your machine - for example to "*/usr/local/bin/scripts/*".
2. Run "*npm install*" to install node.js AWS dependancies.
3. Update **config.json** with your AWS credentials and AWS Locale.
4. Run "*sudo chmod +x*" on both the sh and js files (just to make sure that they can be run).
5. Test the script by running "*./process-file.sh your-bucket-name file-to-upload*".  Your file should now have been uploaded to s3 and the local copy deleted.
6. Update the *on_picture_save* parameter in Motions configuration file to point to *process-file.sh* using something like "*/usr/local/bin/scripts/s3-upload/process-picture.sh smart-security-camera-upload %f*".  *%f* is the path to the image file. *smart-security-camera-upload* can be switched out with the name of the s3 bucket you are uploading to.
