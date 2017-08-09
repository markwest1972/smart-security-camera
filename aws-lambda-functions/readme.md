# aws-lambda-functions

The original version of this camera was created using Node.js.  After some months of successful service the Node.js was replaced by a straight Java port, mainly to look into differences between tooling, ease of use and speed.

### Interoperability

At the time of writing you need to choose **either** Node.js or Java for all your Lambda Functions.

### Choose your language

1. [Node.js](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs).
2. [Java](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java).

### Function Stack in Node.js and Java

| Java Implementation | Node.js Implementation | Purpose |
| ------------- | ------------- | ------------- |
| [s3-trigger-image-processing](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/s3-trigger-image-processing) | [s3-trigger-image-processing](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs/s3-trigger-image-processing) | Triggered when a new image is uploaded to a given s3 upload folder. |
| [rekognition-image-assessment](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/rekognition-image-assessment) | [rekognition-image-assessment](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs/rekognition-image-assessment)  |  Uses AWS Rekognition to generate a list of labels describing each uploaded picture.  |
| [rekognition-evaluate-labels](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/rekognition-evaluate-labels) | [rekognition-evaluate-labels](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs/rekognition-evaluate-labels) | Evaluates labels to find out if an alarm email should be sent. |
| [ses-send-notification](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/ses-send-notification) | [nodemailer-send-notification](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs/nodemailer-send-notification) | Sends an alarm email via AWS SES and JavaMail when the smart security camera detects a person. |
| [s3-archive-image](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/s3-archive-image) | [s3-archive-image](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs/s3-archive-image) | Moves the processed image to the correct archive location in S3. |
| [ses-error-handler](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/java/ses-error-handler) | [nodemailer-error-handler](https://github.com/markwest1972/smart-security-camera/tree/master/aws-lambda-functions/nodejs/nodemailer-error-handler) | Sends emails via AWS SES when runtime errors occur. |
