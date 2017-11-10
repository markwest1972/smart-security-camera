package no.markawest.smartsecuritycamera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;

/**
 * Creates an alert email a snapshot from the Web Camera and the corresponding
 * labels from the AWS Rekognition Service. Utilises JavaMail and the AWS SES
 * Service.
 * 
 * EMAIL_RECIPIENT, S3_URL_PREFIX and EMAIL_FROM should be specified as Environment variables for this Lambda Function.
 * 
 * Part of the Smart Security Camera project.
 * 
 * @author mark.west
 */
public class SesSendNotificationHandler implements RequestHandler<Parameters, Parameters> {

    private static String EMAIL_SUBJECT = "⏰ Alarm Event detected! ⏰";

    @Override
    public Parameters handleRequest(Parameters parameters, Context context) {

        context.getLogger().log("Input Function [" + context.getFunctionName() + "], Parameters [" + parameters + "]");

        try {

            // Create an empty Mime message and start populating it
            Session session = Session.getDefaultInstance(new Properties());
            MimeMessage message = new MimeMessage(session);
            message.setSubject(EMAIL_SUBJECT, "UTF-8");
            message.setFrom(new InternetAddress(System.getenv("EMAIL_FROM")));
            message.setReplyTo(new Address[] { new InternetAddress(System.getenv("EMAIL_FROM")) });
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(System.getenv("EMAIL_RECIPIENT")));

            MimeBodyPart wrap = new MimeBodyPart();
            MimeMultipart cover = new MimeMultipart("alternative");
            MimeBodyPart html = new MimeBodyPart();
            cover.addBodyPart(html);
            wrap.setContent(cover);
            MimeMultipart content = new MimeMultipart("related");
            message.setContent(content);
            content.addBodyPart(wrap);

            // Create an S3 URL reference to the snapshot that will be attached to this email
            URL attachmentURL = createSignedURL(parameters.getS3Bucket(), parameters.getS3Key());

            StringBuilder sb = new StringBuilder();
            String id = UUID.randomUUID().toString();
            sb.append("<img src=\"cid:");
            sb.append(id);
            sb.append("\" alt=\"ATTACHMENT\"/>\n");
            
            // Add the attachment as a part of the message body
            MimeBodyPart attachment = new MimeBodyPart();
            DataSource fds = new URLDataSource(attachmentURL);
            attachment.setDataHandler(new DataHandler(fds));
            
            attachment.setContentID("<" + id + ">");
            attachment.setDisposition(BodyPart.ATTACHMENT);
             
            attachment.setFileName(fds.getName());
            content.addBodyPart(attachment);

            // Pretty print the Rekognition Labels as part of the Emails HTML content
            String prettyPrintLabels = parameters.getRekognitionLabels().toString();
            prettyPrintLabels = prettyPrintLabels.replace("{", "").replace("}", "");
            prettyPrintLabels = prettyPrintLabels.replace(",", "<br>");            
            html.setContent("<html><body><h2>Uploaded Filename : " + parameters.getS3Key().replace("upload/", "") + 
                            "</h2><p><b>Detected Labels/Confidence</b><br><br>" + prettyPrintLabels + "</p>"+sb+"</body></html>", "text/html");

            // Convert the JavaMail message into a raw email request for sending via SES
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
            SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);

            // Send the email using the AWS SES Service
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.defaultClient();
            client.sendRawEmail(rawEmailRequest);

        } catch (MessagingException | IOException e) {
            // Convert Checked Exceptions to RuntimeExceptions to ensure that
            // they get picked up by the Step Function infrastructure
            throw new AmazonServiceException("Error in ["+context.getFunctionName()+"]", e);
        }

        context.getLogger().log("Output Function [" + context.getFunctionName() + "], Parameters [" + parameters + "]");

        return parameters;
    }
    
    /**
     * Create pre-signed key for ease of access to S3 object
     */
    private URL createSignedURL(String s3Bucket, String s3Key){
    
        AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
        
        // Set expiration to 1 hour
        java.util.Date expiration = new java.util.Date();
        long msec = expiration.getTime();
        msec += 1000 * 60 * 60; // 1 hour.
        expiration.setTime(msec);
                     
        // Generate signed key
        GeneratePresignedUrlRequest generatePresignedUrlRequest = 
                      new GeneratePresignedUrlRequest(s3Bucket, s3Key);
        
        generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
        generatePresignedUrlRequest.setExpiration(expiration);
                     
        // Return key
        return s3client.generatePresignedUrl(generatePresignedUrlRequest); 
    }
}
