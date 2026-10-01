package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminRepository {

    // Get admin by user ID
    public Admin getAdminByUserId(int userId) {

        String sql = "SELECT id, user_id, email, phone FROM Admin WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Admin(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save admin profile
    public boolean saveAdminProfile(int userId, String email, String phone) {

        Admin existing = getAdminByUserId(userId);

        if (existing == null) {
            return insertAdminProfile(userId, email, phone);
        } else {
            return updateAdminProfile(userId, email, phone);
        }
    }

    // Insert admin profile
    private boolean insertAdminProfile(int userId, String email, String phone) {

        String sql = "INSERT INTO Admin (user_id, email, phone) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setString(2, email);
            statement.setString(3, phone);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update admin profile
    private boolean updateAdminProfile(int userId, String email, String phone) {

        String sql = "UPDATE Admin SET email = ?, phone = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, phone);
            statement.setInt(3, userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}