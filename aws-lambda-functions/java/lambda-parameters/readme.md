# lambda-parameters

POJO for transferring data between Java based Lambda Functions. 

AWS uses Jackson to serialise Java objects to JSON and vice versa.

## Contents

* Parameters.java

#### Parameter properties

The Parameter class has the following properties:
* *s3Key* - S3 Key for uploaded snapshot.
* *s3Bucket* - S3 Bucket containing uploaded snapshot.
* *sendAlert* - Shall we send an alert email or not?
* *rekognitionLabels* - Labels and confidence scores returned from AWS Rekognition.
* *s3ArchivedKey* - Archived location of snapshot after processing.

## Usage

Ensure that all Java uploads to AWS include this POJO.
