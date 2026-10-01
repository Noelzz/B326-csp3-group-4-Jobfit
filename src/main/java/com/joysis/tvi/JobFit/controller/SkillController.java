package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.Skill;
import com.joysis.tvi.JobFit.service.SkillService;

import java.util.List;

public class SkillController {

    private final SkillService service;

    public SkillController() {
        service = new SkillService();
    }

    public List<Skill> getAllSkills() {
        return service.getAllSkills();
    }

    public boolean addSkill(String name) {
        return service.addSkill(name);
    }

    public boolean updateSkill(int id, String name) {
        return service.updateSkill(id, name);
    }

    public boolean deleteSkill(int id) {
        return service.deleteSkill(id);
    }

    public List<Skill> getJobSeekerSkills(int jobSeekerId) {
        return service.getJobSeekerSkills(jobSeekerId);
    }

    public boolean addSkill(int jobSeekerId, int skillId) {
        return service.addSkill(jobSeekerId, skillId);
    }

    public boolean removeSkill(int jobSeekerId, int skillId) {
        return service.removeSkill(jobSeekerId, skillId);
    }

    public Skill findOrCreateSkill(String name) {
        return service.findOrCreateSkill(name);
    }
}