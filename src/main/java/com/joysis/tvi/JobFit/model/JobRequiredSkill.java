package com.joysis.tvi.JobFit.model;

public class JobRequiredSkill {

    private int id;
    private int jobId;
    private int skillId;
    private String skillName;

    public JobRequiredSkill(
            int id,
            int jobId,
            int skillId,
            String skillName) {

        this.id = id;
        this.jobId = jobId;
        this.skillId = skillId;
        this.skillName = skillName;
    }

    public int getId() {
        return id;
    }

    public int getJobId() {
        return jobId;
    }

    public int getSkillId() {
        return skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return skillName;
    }
}