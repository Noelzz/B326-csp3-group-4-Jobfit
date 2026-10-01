package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.JobRequiredSkill;
import com.joysis.tvi.JobFit.repository.JobRequiredSkillRepository;

import java.util.List;

public class JobRequiredSkillService {

    private final JobRequiredSkillRepository repository;

    public JobRequiredSkillService() {
        repository = new JobRequiredSkillRepository();
    }

    public List<JobRequiredSkill> getRequiredSkillsByJob(int jobId) {

        if (jobId <= 0) {
            return List.of();
        }
        return repository.getRequiredSkillsByJob(jobId);
    }

    public boolean addRequiredSkill(int jobId, int skillId) {

        if (jobId <= 0 || skillId <= 0) {
            return false;
        }

        if (repository.hasRequiredSkill(jobId, skillId)) {
            return false;
        }
        return repository.addRequiredSkill(jobId, skillId);
    }

    public boolean removeRequiredSkill(int jobId, int skillId) {

        if (jobId <= 0 || skillId <= 0) {
            return false;
        }
        return repository.removeRequiredSkill(jobId, skillId);
    }
}