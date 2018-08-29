package no.markawest.smartsecuritycamera;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;

/**
 * Calls the the Rekognition Service with a snapshot uploaded from the web
 * camera. Snapshot has been uploaded to S3. The resulting labels are returned
 * as part of the output.
 *
 * Evaluates the labels returned from AWS Rekognition and sets an alert flag
 * accordingly.
 * 
 * Part of the Smart Security Camera project.
 * 
 * @author mark.west
 */
public class RekognitionImageAssessmentHandler implements RequestHandler<Parameters, Parameters> {

    @Override
    public Parameters handleRequest(Parameters parameters, Context context) {

        context.getLogger().log("Input Function [" + context.getFunctionName() + 
                "], Parameters [" + parameters + "]");

        // Create Rekognition client using the parameters available form the runtime context
        AmazonRekognition rekognitionClient = 
                AmazonRekognitionClientBuilder.defaultClient();

        // Create a Rekognition request
        DetectLabelsRequest request = new DetectLabelsRequest().withImage(new Image()
                .withS3Object(new S3Object().withName(parameters.getS3Key())
                        .withBucket(parameters.getS3Bucket())));

        // Call the Rekognition Service
        DetectLabelsResult result = rekognitionClient.detectLabels(request);

        // Transfer labels and confidence scores over to Parameter POJO
        for (Label label : result.getLabels()) {
            parameters.getRekognitionLabels().put(label.getName(), label.getConfidence());
        }

        context.getLogger().log("Output Function [" + context.getFunctionName() +
                "], Parameters [" + parameters + "]");

        // Return the result (will be serialised to JSON)
        return parameters;
    }
}
