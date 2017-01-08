# smart-security-camera
A PiZero and Motion based webcamera that forwards images to Amazon Web Services for Image Processing.

More information can be found here : https://utbrudd.bouvet.no/2017/01/05/building-a-motion-activated-security-camera-with-the-raspberry-pi-zero/.

This is a work in progress and will be accompanied with a new blog post in the near future. 

## Contents

1. s3-upload: Handles upload of image files from Pi Zero to Amazon s3.
2. motion-config: Configuration file for Motion.
3. aws-lambda-functions: Source code for all aws lambda functions for handling image analysis and processing. 
4. aws-step-functions: Source code for orchestration of aws lambda functions
