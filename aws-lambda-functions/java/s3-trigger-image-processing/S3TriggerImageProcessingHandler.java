package no.markawest.smartsecuritycamera;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;

/**
 * When a new snapshot is uploaded to S3, this function is triggered. It
 * activates a Step Function to further process the new snapshot.
 * 
 * STEP_MACHINE_ARN should be specified as an Environment variable for this Lambda Function.
 * 
 * Part of the Smart Security Camera project.
 * 
 * @author mark.west
 */
public class S3TriggerImageProcessingHandler implements RequestHandler<S3Event, Parameters> {

    @Override
    public Parameters handleRequest(S3Event event, Context context) {

        context.getLogger()
                .log("Input Function [" + context.getFunctionName() + "], S3Event [" + event.toJson().toString() + "]");

        Parameters parameters = new Parameters(
                event.getRecords().get(0).getS3().getBucket().getName(), 
                event.getRecords().get(0).getS3().getObject().getKey(),
                UUID.randomUUID());

        AWSStepFunctions client = AWSStepFunctionsClientBuilder.defaultClient();
        ObjectMapper jsonMapper = new ObjectMapper();
        
        StartExecutionRequest request = new StartExecutionRequest();
        request.setStateMachineArn(System.getenv("STEP_MACHINE_ARN"));
        request.setName(parameters.getStepFunctionID().toString());
        try {
            request.setInput(jsonMapper.writeValueAsString(parameters));
        } catch (JsonProcessingException e) {
            throw new AmazonServiceException("Error in ["+context.getFunctionName()+"]", e);
        }

        context.getLogger()
                .log("Step Function [" + request.getStateMachineArn() + "] will be called with [" + request.getInput() + "]");

        StartExecutionResult result = client.startExecution(request);

        context.getLogger()
                .log("Output Function [" + context.getFunctionName() + "], Result [" + result.toString() + "]");

        return parameters;
    }
}
