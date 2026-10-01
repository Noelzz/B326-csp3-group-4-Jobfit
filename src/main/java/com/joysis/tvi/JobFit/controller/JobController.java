package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.service.JobService;

import java.util.List;

public class JobController {

    private final JobService service;

    public JobController() {
        service = new JobService();
    }

    public boolean addJob(Job job) {
        return service.addJob(job);
    }

    public List<Job> getJobsByEmployer(int employerId) {
        return service.getJobsByEmployer(employerId);
    }

    public List<Job> getAllJobs() {
        return service.getAllJobs();
    }

    public List<Object[]> getAllJobsWithCategory() {
        return service.getAllJobsWithCategory();
    }
}