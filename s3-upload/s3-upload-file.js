//
// Uploads pictures to s3, where they will trigger a Lambda process.
//
// This module requires two command-line arguments:
// 1. Destination bucket
// 2. File to upload
//
// Example : "node s3-upload-image-file.js <s3-bucket> <path-to-image>"
//
// Requires config.json file with AWS authorisation and locale parameters.
//

// Load the SDK for JavaScript
var AWS = require('aws-sdk');
var path = require('path');
var fs = require('fs');

// Load configration file
AWS.config.loadFromPath('/usr/local/bin/scripts/s3-upload/config.json');

// Create S3 service object
s3 = new AWS.S3({apiVersion: '2006-03-01'});

// Create stream for upload
var file = process.argv[3];
var fileStream = fs.createReadStream(file);
fileStream.on('error', function(err) {
  
    console.log('File Error', err);
});

// Populate upload parameters
var uploadParams = {Bucket: process.argv[2], Key: '', Body: ''};
uploadParams.Key = 'upload/'+path.basename(file);
uploadParams.Body = fileStream;
uploadParams.ACL = 'public-read';

// call S3 to retrieve upload file to specified bucket
s3.upload (uploadParams, function (err, data) {
  if (err) {
    console.log('Upload Error', err);
  } if (data) {
    console.log('Upload Success', '[' + data.Location + ']');
  }
});
