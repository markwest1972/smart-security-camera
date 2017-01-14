exports.handler = (event, context, callback) => {

  var aws = require('aws-sdk');
  var nodemailer = require('nodemailer');
  var sesTransport = require('nodemailer-ses-transport');
  var ses = new aws.SES({apiVersion: '2010-12-01', region: 'eu-west-1'});
  var s3 = new aws.S3();

  // Set up ses as tranport for email
  var transport = nodemailer.createTransport(sesTransport({
      ses: ses
  }));

  // Pickup parameters from calling event
  var bucket = event.bucket;
  var filename = event.key;
  var labels = event.Labels;

  // Add timestamp to file name
  var localFile = filename.replace('upload/', '');

  // Set up email parameters
  var mailOptions = mailOptions = {
      from: '"Smart Security Camera" <markwest1972@gmail.com>',
      to: 'markwest1972@gmail.com',
      subject: '⏰ Alarm Event detected! ⏰',
      text: JSON.stringify(labels),
      html: '<pre>'+JSON.stringify(labels, null, 2)+'</pre>',
      attachments: [

        {
            filename: localFile,
            path: 'https://s3-eu-west-1.amazonaws.com/'+ bucket + '/'+ filename
        }
      ]
  }

  transport.sendMail(mailOptions, function(error, info){
      if(error){
        var errorMessage =  'Error in [nodemailer-send-notification].\r' +
                              '   Function input ['+JSON.stringify(event, null, 2)+'].\r' +
                              '   Error ['+error + '].';
        console.log(errorMessage);
        callback(errorMessage, null);
      }else{
        console.log('Message sent: ' + info.response);
        callback(null, event);
      }
  });
};
