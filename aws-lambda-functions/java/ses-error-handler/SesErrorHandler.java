package no.markawest.smartsecuritycamera;

import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Creates and sends a warning email when runtime exceptions are raised in the
 * processing of snapshots.
 * 
 * EMAIL_RECIPIENT and EMAIL_FROM should be specified as an Environment variable for this Lambda Function.
 * 
 * Part of the Smart Security Camera project.
 * 
 * @author mark.west
 */
public class SesErrorHandler implements RequestHandler<Object, String> {

    private static String EMAIL_SUBJECT = "⚠️ Error processing image ⚠️";

    @Override
    public String handleRequest(Object parameters, Context context) {

        context.getLogger().log("Input Function [" + context.getFunctionName() + "], Parameters [" + parameters + "]");

        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[] { System.getenv("EMAIL_RECIPIENT") });

        // Create the subject and body of the message.
        Content subject = new Content().withData(EMAIL_SUBJECT);
        Content textBody = new Content().withData(parameters.toString());
        Body body = new Body().withText(textBody);

        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);

        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(System.getenv("EMAIL_FROM")).withDestination(destination)
                .withMessage(message);

        // Send the email using the AWS SES Service
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.defaultClient();
        client.sendEmail(request);

        context.getLogger().log("Mail sent");

        return "Message sent";
    }
}
