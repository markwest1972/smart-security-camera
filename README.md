# smart-security-camera
A Pi Zero and Motion based webcamera that forwards images to Amazon Web Services for Image Processing.

More information can be found here : https://utbrudd.bouvet.no/2017/01/05/building-a-motion-activated-security-camera-with-the-raspberry-pi-zero/.

This is a work in progress - more information coming soon!

## Contents

1. **[s3-upload](https://github.com/markwest1972/smart-security-camera/tree/master/s3-upload)**: Handles upload of image files from Pi Zero to Amazon s3.
2. **motion-config** *(coming)*: Configuration file for Motion.
3. **aws-lambda-functions** *(coming)*: Source code for all aws lambda functions for handling image analysis and processing. 
4. **[aws-step-functions](https://github.com/markwest1972/smart-security-camera/tree/master/aws-step-functions)**: Source code for orchestration of aws lambda functions.
