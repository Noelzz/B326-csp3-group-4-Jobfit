package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.JobSeeker;
import com.joysis.tvi.JobFit.service.JobSeekerService;

public class JobSeekerController {

    private final JobSeekerService service;

    public JobSeekerController() {
        service = new JobSeekerService();
    }

    public JobSeeker getProfile(int userId) {
        return service.getProfile(userId);
    }

    public String updateProfile(JobSeeker jobSeeker) {
        return service.updateProfile(jobSeeker);
    }
}