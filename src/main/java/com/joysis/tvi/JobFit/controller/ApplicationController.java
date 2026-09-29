package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.Application;
import com.joysis.tvi.JobFit.model.Applicant;
import com.joysis.tvi.JobFit.service.ApplicationService;

import java.util.List;

public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController() {
        service = new ApplicationService();
    }

    public boolean applyForJob(
            int jobId,
            int jobSeekerId) {

        return service.applyForJob(
                jobId,
                jobSeekerId
        );
    }

    public boolean hasApplied(
            int jobId,
            int jobSeekerId) {

        return service.hasApplied(
                jobId,
                jobSeekerId
        );
    }

    public List<Application> getApplicationsByJobSeeker(
            int jobSeekerId) {

        return service.getApplicationsByJobSeeker(
                jobSeekerId
        );
    }

    public List<Applicant> getApplicantsByEmployer(
            int employerId) {

        return service.getApplicantsByEmployer(
                employerId
        );
    }

    public boolean updateApplicationStatus(
            int applicationId,
            String status) {

        return service.updateApplicationStatus(
                applicationId,
                status
        );
    }
}