package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.JobRequiredSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobRequiredSkillRepository {

    // Get required skills by job
    public List<JobRequiredSkill> getRequiredSkillsByJob(int jobId) {

        List<JobRequiredSkill> skills = new ArrayList<>();

        String sql = """
                SELECT jrs.id, jrs.job_id, jrs.skill_id, s.name AS skill_name
                FROM Job_Required_Skills jrs INNER JOIN Skills s ON jrs.skill_id = s.id
                WHERE jrs.job_id = ? ORDER BY s.name
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                JobRequiredSkill skill =
                        new JobRequiredSkill(
                                resultSet.getInt("id"),
                                resultSet.getInt("job_id"),
                                resultSet.getInt("skill_id"),
                                resultSet.getString("skill_name")
                        );
                skills.add(skill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return skills;
    }

    // Add required skill
    public boolean addRequiredSkill(int jobId, int skillId) {

        String sql = "INSERT INTO Job_Required_Skills (job_id, skill_id) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);
            statement.setInt(2, skillId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Remove required skills
    public boolean removeRequiredSkill(int jobId, int skillId) {

        String sql = "DELETE FROM Job_Required_Skills WHERE job_id = ? AND skill_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);
            statement.setInt(2, skillId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Check if a job requires a specific skill
    public boolean hasRequiredSkill(int jobId, int skillId) {

        String sql = "SELECT id FROM Job_Required_Skills WHERE job_id = ? AND skill_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);
            statement.setInt(2, skillId);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}