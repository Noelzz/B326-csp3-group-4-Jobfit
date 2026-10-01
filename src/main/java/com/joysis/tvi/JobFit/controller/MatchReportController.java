package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.MatchReport;
import com.joysis.tvi.JobFit.service.MatchReportService;

import java.util.List;

public class MatchReportController {

    private final MatchReportService service;

    public MatchReportController() {
        service = new MatchReportService();
    }

    public List<MatchReport> generateMatches(int jobSeekerId) {
        return service.generateMatches(jobSeekerId);
    }

    public List<MatchReport> getMatchesByJobSeeker(int jobSeekerId) {
        return service.getMatchesByJobSeeker(jobSeekerId);
    }

    public List<MatchReport> getAllMatchReports() {
        return service.getAllMatchReports();
    }

    public Job getJobById(int jobId) {
        return service.getJobById(jobId);
    }
}