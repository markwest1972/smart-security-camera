#!/bin/bash

echo starting s3-upload.sh

echo uploading file $2

node /usr/local/bin/scripts/s3-upload/s3-upload-file.js $1  $2

echo deleting file $2

sudo rm $2

echo completed s3-upload.sh

