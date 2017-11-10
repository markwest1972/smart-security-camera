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
  var bucket = event.bucket;
  var filename = event.key;
  var labels = event.Labels;

  // Add timestamp to file name
  var localFile = filename.replace('upload/', '');

  // Set up HTML Email
  var htmlString = "<pre><u><b>Label &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Confidence</u></b><br>";
  for (var i = 0; i < labels.length; i++) {
    htmlString += "" + labels[i].Name + "" + Array(22-labels[i].Name.length).join('&nbsp;');
    htmlString += "" + labels[i].Confidence.toFixed(1) + "</b><br>";
  };
  htmlString += "</pre>";

  // Set up Text Email
  var textString = "Label          Confidence\n";
  for (var i = 0; i < labels.length; i++) {
    textString += "" + labels[i].Name + "" + Array(22-labels[i].Name.length).join(' ');
    textString += "" + labels[i].Confidence.toFixed(1) + "\n";
  };

  // Set up email parameters
  var mailOptions = mailOptions = {
      from: process.env.EMAIL_FROM,
      to: process.env.EMAIL_RECIPIENT,
      subject: '⏰ Alarm Event detected! ⏰',
      text: textString,
      html: htmlString,
      attachments: [
        {
            filename: localFile,
            //path: process.env.S3_URL_PREFIX + bucket + '/'+ filename
            path: s3.getSignedUrl('getObject', {  Bucket: bucket, Key: filename, Expires: 360})
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
