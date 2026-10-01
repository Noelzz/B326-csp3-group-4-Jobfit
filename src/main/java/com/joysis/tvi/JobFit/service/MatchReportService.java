package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.MatchReport;
import com.joysis.tvi.JobFit.model.Skill;
import com.joysis.tvi.JobFit.repository.JobRepository;
import com.joysis.tvi.JobFit.repository.JobRequiredSkillRepository;
import com.joysis.tvi.JobFit.repository.JobSeekerSkillRepository;
import com.joysis.tvi.JobFit.repository.MatchReportRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchReportService {

    private final MatchReportRepository matchReportRepository;
    private final JobRepository jobRepository;
    private final JobRequiredSkillRepository requiredSkillRepository;
    private final JobSeekerSkillRepository jobSeekerSkillRepository;

    public MatchReportService() {

        matchReportRepository = new MatchReportRepository();
        jobRepository = new JobRepository();
        requiredSkillRepository = new JobRequiredSkillRepository();
        jobSeekerSkillRepository = new JobSeekerSkillRepository();
    }

    public List<MatchReport> generateMatches(int jobSeekerId) {

        if (jobSeekerId <= 0) {
            return List.of();
        }

        List<MatchReport> reports = new ArrayList<>();
        List<Job> jobs = jobRepository.getAllJobs();
        List<Skill> seekerSkills = jobSeekerSkillRepository.getJobSeekerSkills(jobSeekerId);

        for (Job job : jobs) {
            var requiredSkills = requiredSkillRepository.getRequiredSkillsByJob(job.getId());

            // Skip jobs with no required skills
            if (requiredSkills.isEmpty()) {
                continue;
            }

            int matchedSkills = 0;

            for (var requiredSkill : requiredSkills) {
                for (Skill seekerSkill : seekerSkills) {
                    if (seekerSkill.getId() == requiredSkill.getSkillId()) {
                        matchedSkills++;
                        break;
                    }
                }
            }

            double matchScore = ((double) matchedSkills / requiredSkills.size()) * 100.0;
            MatchReport report = new MatchReport(0, job.getId(), jobSeekerId, matchScore, LocalDateTime.now());
            matchReportRepository.saveMatchReport(report);
            reports.add(report);
        }

        return reports;
    }

    public List<MatchReport> getMatchesByJobSeeker(int jobSeekerId) {

        if (jobSeekerId <= 0) {
            return List.of();
        }
        return matchReportRepository.getMatchReportsByJobSeeker(jobSeekerId);
    }

    public List<MatchReport> getAllMatchReports() {
        return matchReportRepository.getAllMatchReports();
    }

    public Job getJobById(int jobId) {

        if (jobId <= 0) {
            return null;
        }

        List<Job> jobs = jobRepository.getAllJobs();

        for (Job job : jobs) {
            if (job.getId() == jobId) {
                return job;
            }
        }
        return null;
    }
}