package com.joysis.tvi.JobFit.model;

import java.time.LocalDate;

public class Applicant {

    private int applicationId;
    private int jobId;
    private int jobSeekerId;
    private String jobTitle;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate applicationDate;
    private String status;

    public Applicant() {
    }

    public Applicant(
            int applicationId,
            int jobId,
            int jobSeekerId,
            String jobTitle,
            String fullName,
            String email,
            String phone,
            LocalDate applicationDate,
            String status) {

        this.applicationId = applicationId;
        this.jobId = jobId;
        this.jobSeekerId = jobSeekerId;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public int getJobId() {
        return jobId;
    }

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setJobSeekerId(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}