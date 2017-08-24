exports.handler = (event, context, callback) => {

  var aws = require('aws-sdk');
  var nodemailer = require('nodemailer');
  var sesTransport = require('nodemailer-ses-transport');
  var ses = new aws.SES({apiVersion: '2010-12-01', region: process.env.AWS_REGION}); 
  var s3 = new aws.S3({apiVersion: '2006-03-01', region: process.env.AWS_REGION});

  // Set up ses as tranport for email
  var transport = nodemailer.createTransport(sesTransport({
      ses: ses
  }));

  // Pickup parameters from calling event
  var errorMessage = JSON.stringify(event);

  // Set up email parameters
  var mailOptions = mailOptions = {
      from: process.env.EMAIL_FROM,
      to: process.env.EMAIL_RECIPIENT,
      subject: '⚠️ Error processing image ⚠️',
      text: errorMessage,
      html: '<pre>'+errorMessage+'</pre>'

  }

  // Send mail with defined transport object
  transport.sendMail(mailOptions, function(error, info){
      if(error){
        var errorMessage =  'Error in [nodemailer-error-handler].\r' +
                                '   Function input ['+JSON.stringify(event, null, 2)+'].\r' +
                                '   Error ['+error+'].';
        console.log(errorMessage);
        callback(errorMessage, null);
      }else{
        console.log('Message sent: ' + info.response);
        callback(null, "Success");
      }
  });
};
