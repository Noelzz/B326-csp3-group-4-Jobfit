package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.Employer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployerRepository {

    public Employer getProfile(int userId) {

        String sql = """
                SELECT id, user_id, company_name, email, phone
                FROM Employer
                WHERE user_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Employer(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("company_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateProfile(Employer employer) {

        String sql = """
                UPDATE Employer
                SET company_name = ?,
                    email = ?,
                    phone = ?
                WHERE user_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, employer.getCompanyName());
            statement.setString(2, employer.getEmail());
            statement.setString(3, employer.getPhone());
            statement.setInt(4, employer.getUserId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}