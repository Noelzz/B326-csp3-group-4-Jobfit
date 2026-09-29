package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.JobRequiredSkill;
import com.joysis.tvi.JobFit.service.JobRequiredSkillService;

import java.util.List;

public class JobRequiredSkillController {

    private final JobRequiredSkillService service;

    public JobRequiredSkillController() {

        service =
                new JobRequiredSkillService();
    }

    public List<JobRequiredSkill> getRequiredSkillsByJob(
            int jobId) {

        return service.getRequiredSkillsByJob(
                jobId
        );
    }

    public boolean addRequiredSkill(
            int jobId,
            int skillId) {

        return service.addRequiredSkill(
                jobId,
                skillId
        );
    }

    public boolean removeRequiredSkill(
            int jobId,
            int skillId) {

        return service.removeRequiredSkill(
                jobId,
                skillId
        );
    }
}