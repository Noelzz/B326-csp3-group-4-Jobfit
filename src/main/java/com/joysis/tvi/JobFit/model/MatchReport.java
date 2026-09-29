package com.joysis.tvi.JobFit.model;

import java.time.LocalDateTime;

public class MatchReport {

    private int id;
    private int jobId;
    private int jobSeekerId;
    private double matchScore;
    private LocalDateTime createdAt;

    public MatchReport() {
    }

    public MatchReport(
            int id,
            int jobId,
            int jobSeekerId,
            double matchScore,
            LocalDateTime createdAt) {

        this.id = id;
        this.jobId = jobId;
        this.jobSeekerId = jobSeekerId;
        this.matchScore = matchScore;
        this.createdAt = createdAt;
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

    public double getMatchScore() {
        return matchScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}