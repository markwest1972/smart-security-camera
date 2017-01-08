#!/bin/bash

echo starting s3-upload.sh

echo uploading file $1

node s3-upload-file.js smart-security-camera-upload  $1

echo deleting file $1

sudo rm $1

echo completed s3-upload.sh
