package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.config.PasswordValidator;
import com.joysis.tvi.JobFit.model.Employer;
import com.joysis.tvi.JobFit.repository.EmployerRepository;

public class EmployerService {

    private final EmployerRepository repository;

    public EmployerService() {
        repository = new EmployerRepository();
    }

    public Employer getProfile(int userId) {
        return repository.getProfile(userId);
    }

    public String updateProfile(Employer employer) {

        if (employer == null) {
            return "Invalid profile data.";
        }
        if (!PasswordValidator.isValidName(employer.getCompanyName())) {
            return "Company name must be 2-100 characters.";
        }
        if (!PasswordValidator.isValidEmail(employer.getEmail())) {
            return "Invalid email format.";
        }
        if (!PasswordValidator.isValidPhone(employer.getPhone())) {
            return "Invalid phone number.";
        }

        if (repository.emailExistsExceptUser(employer.getEmail(), employer.getUserId())) {
            return "Email is already in use by another account.";
        }

        boolean updated = repository.updateProfile(employer);
        return updated ? null : "No changes were made, or update failed.";
    }
}