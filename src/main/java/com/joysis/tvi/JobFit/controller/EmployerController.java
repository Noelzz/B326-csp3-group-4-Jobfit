package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.Employer;
import com.joysis.tvi.JobFit.service.EmployerService;

public class EmployerController {

    private final EmployerService service;

    public EmployerController() {
        service = new EmployerService();
    }

    public Employer getProfile(int userId) {
        return service.getProfile(userId);
    }

    public String updateProfile(Employer employer) {
        return service.updateProfile(employer);
    }
}