package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.JobSeeker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JobSeekerRepository {

    public JobSeeker getProfile(int userId) {

        String sql = """
                SELECT id, user_id, full_name, email, phone
                FROM Job_Seeker
                WHERE user_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new JobSeeker(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateProfile(JobSeeker jobSeeker) {

        String sql = """
                UPDATE Job_Seeker
                SET full_name = ?,
                    email = ?,
                    phone = ?
                WHERE user_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, jobSeeker.getFullName());
            statement.setString(2, jobSeeker.getEmail());
            statement.setString(3, jobSeeker.getPhone());
            statement.setInt(4, jobSeeker.getUserId());

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}