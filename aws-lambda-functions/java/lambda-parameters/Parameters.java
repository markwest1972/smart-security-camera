package no.markawest.smartsecuritycamera;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Parameters for Lambda Functions involved in the processing of snapshots from
 * the the Smart Security Camera project.
 * 
 * @author mark.west
 */
public class Parameters {

    // S3 Key for uploaded snapshot
    private String s3Key = null;

    // S3 Bucket containing uploaded snapshot
    private String s3Bucket = null;

    // Shall we send an alert email or not?
    private Boolean sendAlert = Boolean.FALSE;

    // Labels and confidence scores returned from AWS Rekognition
    private Map<String, Float> rekognitionLabels = null;

    // Location of snapshot after processing
    private String s3ArchivedKey = null;
    
    // Step Function UUID
    private UUID stepFunctionID = null;

    public Parameters() {}

    public Parameters(String s3Bucket, String s3key, UUID stepFunctionID) {
        this.s3Bucket = s3Bucket;
        this.s3Key = s3key;
        this.stepFunctionID = stepFunctionID;
    }
    
    public String getS3ArchivedKey() {
        return s3ArchivedKey;
    }

    public void setS3ArchivedKey(String s3ArchivedKey) {
        this.s3ArchivedKey = s3ArchivedKey;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3key) {
        this.s3Key = s3key;
    }

    public String getS3Bucket() {
        return s3Bucket;
    }

    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }

    public Boolean getSendAlert() {
        return sendAlert;
    }

    public void setSendAlert(Boolean sendAlert) {
        this.sendAlert = sendAlert;
    }

    public void setRekognitionLabels(Map<String, Float> rekognitionLabels) {
        this.rekognitionLabels = rekognitionLabels;
    }

    public Map<String, Float> getRekognitionLabels() {
        if (this.rekognitionLabels == null) {
            this.rekognitionLabels = new HashMap<String, Float>();
        }
        return this.rekognitionLabels;
    }

    public UUID getStepFunctionID(){
        return this.stepFunctionID;
    }
    
    public void setStepFunctionID(UUID id){
        this.stepFunctionID = id;
    }

    @Override
    public String toString() {
        return "Parameters [s3Key=" + s3Key + ", s3Bucket=" + s3Bucket + ", sendAlert=" + sendAlert
                + ", rekognitionLabels=" + rekognitionLabels + ", s3ArchivedKey=" + s3ArchivedKey + ", stepFunctionID="
                + stepFunctionID + "]";
    }
}
