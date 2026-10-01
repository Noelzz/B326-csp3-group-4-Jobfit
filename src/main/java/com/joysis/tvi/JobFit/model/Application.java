package com.joysis.tvi.JobFit.model;

import java.time.LocalDate;

public class Application {

    private int id;
    private int jobId;
    private int jobSeekerId;
    private LocalDate applicationDate;
    private String status;

    public Application(
            int id,
            int jobId,
            int jobSeekerId,
            LocalDate applicationDate,
            String status
    ) {
        this.id = id;
        this.jobId = jobId;
        this.jobSeekerId = jobSeekerId;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getJobId() {
        return jobId;
    }

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setJobSeekerId(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }
}