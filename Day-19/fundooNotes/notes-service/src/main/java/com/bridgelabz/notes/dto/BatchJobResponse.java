package com.bridgelabz.notes.dto;

public class BatchJobResponse {

    private String jobStatus;
    private int importedCount;
    private int skippedCount;

    public BatchJobResponse() {
    }

    public BatchJobResponse(String jobStatus, int importedCount, int skippedCount) {
        this.jobStatus = jobStatus;
        this.importedCount = importedCount;
        this.skippedCount = skippedCount;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public int getImportedCount() {
        return importedCount;
    }

    public void setImportedCount(int importedCount) {
        this.importedCount = importedCount;
    }

    public int getSkippedCount() {
        return skippedCount;
    }

    public void setSkippedCount(int skippedCount) {
        this.skippedCount = skippedCount;
    }
}
