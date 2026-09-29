package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.JobSeeker;
import com.joysis.tvi.JobFit.repository.JobSeekerRepository;

public class JobSeekerService {

    private final JobSeekerRepository repository;

    public JobSeekerService() {
        repository = new JobSeekerRepository();
    }

    public JobSeeker getProfile(int userId) {

        return repository.getProfile(userId);
    }

    public boolean updateProfile(JobSeeker jobSeeker) {

        if (jobSeeker.getFullName() == null ||
                jobSeeker.getFullName().trim().isEmpty()) {

            return false;
        }

        if (jobSeeker.getEmail() == null ||
                jobSeeker.getEmail().trim().isEmpty()) {

            return false;
        }

        return repository.updateProfile(jobSeeker);
    }
}