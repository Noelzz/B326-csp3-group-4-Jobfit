package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobSeekerSkillRepository {

    public List<Skill> getJobSeekerSkills(int jobSeekerId) {

        List<Skill> skills = new ArrayList<>();

        String sql = """
                SELECT s.id, s.name
                FROM Skills s
                INNER JOIN Job_Seeker_Skills jss
                    ON s.id = jss.skill_id
                WHERE jss.job_seeker_id = ?
                ORDER BY s.name
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, jobSeekerId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                skills.add(
                        new Skill(
                                resultSet.getInt("id"),
                                resultSet.getString("name")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return skills;
    }

    public boolean addSkill(
            int jobSeekerId,
            int skillId) {

        String sql = """
                INSERT INTO Job_Seeker_Skills
                (job_seeker_id, skill_id)
                VALUES (?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, jobSeekerId);
            statement.setInt(2, skillId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeSkill(
            int jobSeekerId,
            int skillId) {

        String sql = """
                DELETE FROM Job_Seeker_Skills
                WHERE job_seeker_id = ?
                AND skill_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, jobSeekerId);
            statement.setInt(2, skillId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}