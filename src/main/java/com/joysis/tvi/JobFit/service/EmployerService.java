package com.joysis.tvi.JobFit.service;

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

    public boolean updateProfile(Employer employer) {

        if (employer.getCompanyName() == null ||
                employer.getCompanyName().trim().isEmpty()) {
            return false;
        }

        if (employer.getEmail() == null ||
                employer.getEmail().trim().isEmpty()) {
            return false;
        }

        return repository.updateProfile(employer);
    }
}