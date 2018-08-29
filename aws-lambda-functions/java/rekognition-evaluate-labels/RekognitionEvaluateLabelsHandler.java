package no.markawest.smartsecuritycamera;

import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Evaluates the labels returned from AWS Rekognition and sets an alert flag
 * accordingly.
 *
 * Part of the Smart Security Camera project.
 *
 * @author mark.west
 */
public class RekognitionEvaluateLabelsHandler implements RequestHandler<Parameters, Parameters> {

    private final List<String> alertLabels = Arrays.asList("Human", "People", "Person", "Male", "Female", "Portrait", "Pedestrian", "Child", "Kid", "Beard", "Hair", "Face");

    @Override
    public Parameters handleRequest(Parameters parameters, Context context) {

        context.getLogger().log("Input Function [" + context.getFunctionName() + "], Parameters [" + parameters + "]");

        parameters.setSendAlert( parameters.getRekognitionLabels().keySet().stream().anyMatch(k -> alertLabels.contains(k.toString())) );

        context.getLogger().log("Output Function [" + context.getFunctionName() + "], Parameters [" + parameters + "]");

        return parameters;
    }

}
