package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.config.PasswordValidator;
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

    public String updateProfile(JobSeeker jobSeeker) {

        if (jobSeeker == null) {
            return "Invalid profile data.";
        }
        if (!PasswordValidator.isValidName(jobSeeker.getFullName())) {
            return "Full name must be 2-100 characters.";
        }
        if (!PasswordValidator.isValidEmail(jobSeeker.getEmail())) {
            return "Invalid email format.";
        }
        if (!PasswordValidator.isValidPhone(jobSeeker.getPhone())) {
            return "Invalid phone number.";
        }

        if (repository.emailExistsExceptUser(jobSeeker.getEmail(), jobSeeker.getUserId())) {
            return "Email is already in use by another account.";
        }

        boolean updated = repository.updateProfile(jobSeeker);
        return updated ? null : "No changes were made, or update failed.";
    }
}