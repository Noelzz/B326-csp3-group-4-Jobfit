package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobCategory;
import com.joysis.tvi.JobFit.repository.JobCategoryRepository;
import com.joysis.tvi.JobFit.repository.JobRepository;

import java.util.List;

public class JobService {

    private final JobRepository jobRepository;
    private final JobCategoryRepository categoryRepository;

    public JobService() {
        jobRepository = new JobRepository();
        categoryRepository = new JobCategoryRepository();
    }

    // Get all job categories
    public List<JobCategory> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    // Add a new job
    public boolean addJob(Job job) {

        if (job == null) {
            return false;
        }

        if (job.getEmployerId() <= 0) {
            return false;
        }

        if (job.getCategoryId() <= 0) {
            return false;
        }

        if (job.getTitle() == null ||
                job.getTitle().trim().isEmpty()) {
            return false;
        }

        if (job.getDescription() == null ||
                job.getDescription().trim().isEmpty()) {
            return false;
        }

        if (job.getLocation() == null ||
                job.getLocation().trim().isEmpty()) {
            return false;
        }

        if (job.getSalary() < 0) {
            return false;
        }

        return jobRepository.addJob(job);
    }

    // Get jobs posted by a specific employer
    public List<Job> getJobsByEmployer(int employerId) {

        if (employerId <= 0) {
            return List.of();
        }

        return jobRepository.getJobsByEmployer(employerId);
    }

    // Get all jobs for Job Seekers
    public List<Job> getAllJobs() {
        return jobRepository.getAllJobs();
    }
}