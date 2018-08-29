package no.markawest.smartsecuritycamera;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

/**
 * Archives snapshots, depending on their alert status.
 * 
 * Part of the Smart Security Camera project.
 * 
 * @author mark.west
 */
public class S3ArchiveImageHandler implements RequestHandler<Parameters, Parameters> {

    @Override
    public Parameters handleRequest(Parameters parameters, Context context) {

        context.getLogger().log("Input Function [" + context.getFunctionName() + "], Parameters [" + parameters + "]");

        // The archive location of the snapshot will be decided by the alert
        // flag
        String newFilename;
        if (parameters.getSendAlert()) {
            newFilename =  parameters.getS3Key().replace("upload/", "archive/alerts/");
        } else {
            newFilename = parameters.getS3Key().replace("upload/", "archive/falsepositives/");
        }

        // Ensure that the first two hyphens are used to create sub-directories
        // in the file path
        newFilename = newFilename.replaceFirst("-", "/");
        newFilename = newFilename.replaceFirst("-", "/");

        // Using the S3 client, first copy the file to the archive, and then
        // delete the original
        AmazonS3 client = AmazonS3ClientBuilder.defaultClient();
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(parameters.getS3Bucket(), parameters.getS3Key(), parameters.getS3Bucket(), newFilename);
        client.copyObject(copyObjectRequest);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(parameters.getS3Bucket(), parameters.getS3Key());
        client.deleteObject(deleteObjectRequest);

        // Place the new location in the parameters
        parameters.setS3ArchivedKey(newFilename);

        context.getLogger().log("Output Function [" + context.getFunctionName() + "], Parameters [" + parameters + "]");

        return parameters;
    }
}
