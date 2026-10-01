package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.Skill;
import com.joysis.tvi.JobFit.repository.JobSeekerSkillRepository;
import com.joysis.tvi.JobFit.repository.SkillRepository;

import java.util.List;

public class SkillService {

    private final SkillRepository skillRepository;
    private final JobSeekerSkillRepository jobSeekerSkillRepository;

    public SkillService() {
        skillRepository = new SkillRepository();
        jobSeekerSkillRepository = new JobSeekerSkillRepository();
    }

    // Gets all the skills in the catalog
    public List<Skill> getAllSkills() {
        return skillRepository.getAllSkills();
    }

    // Admin: add a new skill to the catalog
    public boolean addSkill(String name) {

        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        name = name.trim();
        return skillRepository.addSkill(name);
    }

    // Admin: update a skill's name
    public boolean updateSkill(int id, String name) {

        if (id <= 0) {
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        name = name.trim();
        return skillRepository.updateSkill(id, name);
    }

    // Admin: delete a skill from the catalog
    public boolean deleteSkill(int id) {

        if (id <= 0) {
            return false;
        }
        return skillRepository.deleteSkill(id);
    }

    // Job seeker: link a skill to their profile
    public List<Skill> getJobSeekerSkills(int jobSeekerId) {

        if (jobSeekerId <= 0) {
            return List.of();
        }
        return jobSeekerSkillRepository.getJobSeekerSkills(jobSeekerId);
    }

    // Job seeker: link a skill to this seeker's profile
    public boolean addSkill(int jobSeekerId, int skillId) {

        if (jobSeekerId <= 0 || skillId <= 0) {
            return false;
        }

        return jobSeekerSkillRepository.addSkill(jobSeekerId, skillId);
    }

    // Job seeker: unlink a skill from this seeker's profile
    public boolean removeSkill(int jobSeekerId, int skillId) {

        if (jobSeekerId <= 0 || skillId <= 0) {
            return false;
        }

        return jobSeekerSkillRepository.removeSkill(jobSeekerId, skillId);
    }

    // Find a skill by name, or create it if it doesn't exist
    public Skill findOrCreateSkill(String name) {

        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        String cleanName = name.trim();

        // Try to find existing
        Skill existing = skillRepository.getSkillByName(cleanName);
        if (existing != null) {
            return existing;
        }

        // Create new
        boolean added = skillRepository.addSkill(cleanName);
        if (!added) {
            return null;
        }

        // Fetch the new one (to get its auto-generated ID)
        return skillRepository.getSkillByName(cleanName);
    }
}