package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SkillRepository {

    public List<Skill> getAllSkills() {

        List<Skill> skills = new ArrayList<>();

        String sql = """
                SELECT id, name
                FROM Skills
                ORDER BY name
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Skill skill = new Skill(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );

                skills.add(skill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return skills;
    }

    public boolean addSkill(String name) {

        String sql = """
                INSERT INTO Skills (name)
                VALUES (?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateSkill(int id, String name) {

        String sql = """
                UPDATE Skills
                SET name = ?
                WHERE id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setInt(2, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteSkill(int id) {

        String sql = """
                DELETE FROM Skills
                WHERE id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}