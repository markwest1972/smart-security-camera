package no.markawest.smartsecuritycamera;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Creates an alert email a snapshot from the Web Camera and the corresponding labels from the AWS Rekognition Service. Utilises JavaMail and the AWS SES Service.
 * 
 * The 'parameters' variable refers to a HashMap with the following keys: 
 *  - bucket : The S3 Bucket where the snapshot is located.  Populated by the S3TriggerImageProcessing class.
 *  - key : The S3 key for the snapshot (in practice this looks like a directory path and filename).  Populated by the S3TriggerImageProcessing class.
 *  - labels : The Rekognition generated labels for the snapshot.  Populated by the RekognitionImageAssessment class.
 *  - alert : Whether or not an alert email should be generated.  Populated by the RekognitionEvaluateLabels class.
 *  
 * Part of the Smart Security Camera project.
 * 
 * @author mark.west
 */
public class SesSendNotificationHandler implements RequestHandler<Parameters, Parameters> {
	private static String EMAIL_SUBJECT = "⏰ Alarm Event detected! ⏰";

	public SesSendNotificationHandler() {
	}

	public Parameters handleRequest(Parameters parameters, Context context) {
		context.getLogger().log("Input Function [" + context.getFunctionName() + "], Parameters [" + parameters + "]");

		try {
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

			URL attachmentURL = new URL(
					System.getenv("S3_URL_PREFIX") + parameters.getS3Bucket() + '/' + parameters.getS3Key());

			StringBuilder sb = new StringBuilder();
			String id = UUID.randomUUID().toString();
			sb.append("<img src=\"cid:");
			sb.append(id);
			sb.append("\" alt=\"ATTACHMENT\"/>\n");

			MimeBodyPart attachment = new MimeBodyPart();
			DataSource fds = new URLDataSource(attachmentURL);
			attachment.setDataHandler(new DataHandler(fds));

			attachment.setContentID("<" + id + ">");
			attachment.setDisposition("attachment");

			attachment.setFileName(fds.getName());
			content.addBodyPart(attachment);

			String prettyPrintLabels = parameters.getRekognitionLabels().toString();
			prettyPrintLabels = prettyPrintLabels.replace("{", "").replace("}", "");
			prettyPrintLabels = prettyPrintLabels.replace(",", "<br>");
			html.setContent("<html><body><h2>Uploaded Filename : " + parameters.getS3Key().replace("upload/", "")
					+ "</h2><p><i>Step Function ID : " + parameters.getStepFunctionID()
					+ "</i></p><p><b>Detected Labels/Confidence</b><br><br>" + prettyPrintLabels + "</p>" + sb
					+ "</body></html>", "text/html");

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			message.writeTo(outputStream);
			RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
			SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);

			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.defaultClient();
			client.sendRawEmail(rawEmailRequest);

		// Convert Checked Exceptions to RuntimeExceptions to ensure that
		// they get picked up by the Step Function infrastructure
		} catch (MessagingException | IOException e) {
			throw new RuntimeException("Error in [" + context.getFunctionName() + "]", e);
		}

		context.getLogger().log("Output Function [" + context.getFunctionName() + "], Parameters [" + parameters + "]");

		return parameters;
	}
}
