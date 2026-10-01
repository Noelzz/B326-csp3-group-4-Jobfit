package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.Applicant;
import com.joysis.tvi.JobFit.model.Application;
import com.joysis.tvi.JobFit.repository.ApplicationRepository;

import java.time.LocalDate;
import java.util.List;

public class ApplicationService {

    private final ApplicationRepository repository;

    public ApplicationService() {
        repository = new ApplicationRepository();
    }

    public boolean applyForJob(int jobId, int jobSeekerId) {

        if (jobId <= 0 || jobSeekerId <= 0) {
            return false;
        }

        if (repository.hasApplied(jobId, jobSeekerId)) {
            return false;
        }

        // id=0 because the DB auto-generates it
        Application application = new Application(0, jobId, jobSeekerId, LocalDate.now(), "Pending");
        return repository.addApplication(application);
    }

    public boolean hasApplied(int jobId, int jobSeekerId) {
        return repository.hasApplied(jobId, jobSeekerId);
    }

    public List<Application> getApplicationsByJobSeeker(int jobSeekerId) {

        if (jobSeekerId <= 0) {
            return List.of();
        }
        return repository.getApplicationsByJobSeeker(jobSeekerId);
    }

    public List<Applicant> getApplicantsByEmployer(int employerId) {

        if (employerId <= 0) {
            return List.of();
        }
        return repository.getApplicantsByEmployer(employerId);
    }

    public boolean updateApplicationStatus(int applicationId, String status) {

        if (applicationId <= 0 || status == null || status.trim().isEmpty()) {
            return false;
        }

        if (!status.equals("Pending") &&
                !status.equals("Reviewed") &&
                !status.equals("Accepted") &&
                !status.equals("Rejected")) {
            return false;
        }
        return repository.updateApplicationStatus(applicationId, status);
    }
}