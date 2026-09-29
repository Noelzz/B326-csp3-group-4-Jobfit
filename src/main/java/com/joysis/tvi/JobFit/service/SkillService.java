package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.Skill;
import com.joysis.tvi.JobFit.repository.JobSeekerSkillRepository;
import com.joysis.tvi.JobFit.repository.SkillRepository;

import java.util.List;

public class SkillService {

    private final SkillRepository skillRepository;
    private final JobSeekerSkillRepository jobSeekerSkillRepository;

    public SkillService() {

        skillRepository =
                new SkillRepository();

        jobSeekerSkillRepository =
                new JobSeekerSkillRepository();
    }

    // =========================
    // GENERAL SKILLS
    // =========================

    public List<Skill> getAllSkills() {

        return skillRepository.getAllSkills();
    }

    // =========================
    // ADMIN
    // =========================

    public boolean addSkill(String name) {

        if (name == null ||
                name.trim().isEmpty()) {

            return false;
        }

        name = name.trim();

        return skillRepository.addSkill(name);
    }

    public boolean updateSkill(
            int id,
            String name) {

        if (id <= 0) {
            return false;
        }

        if (name == null ||
                name.trim().isEmpty()) {

            return false;
        }

        name = name.trim();

        return skillRepository.updateSkill(
                id,
                name
        );
    }

    public boolean deleteSkill(int id) {

        if (id <= 0) {
            return false;
        }

        return skillRepository.deleteSkill(id);
    }

    // =========================
    // JOB SEEKER
    // =========================

    public List<Skill> getJobSeekerSkills(
            int jobSeekerId) {

        if (jobSeekerId <= 0) {
            return List.of();
        }

        return jobSeekerSkillRepository
                .getJobSeekerSkills(jobSeekerId);
    }

    public boolean addSkill(
            int jobSeekerId,
            int skillId) {

        if (jobSeekerId <= 0 ||
                skillId <= 0) {

            return false;
        }

        return jobSeekerSkillRepository.addSkill(
                jobSeekerId,
                skillId
        );
    }

    public boolean removeSkill(
            int jobSeekerId,
            int skillId) {

        if (jobSeekerId <= 0 ||
                skillId <= 0) {

            return false;
        }

        return jobSeekerSkillRepository.removeSkill(
                jobSeekerId,
                skillId
        );
    }
}