package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.config.PasswordHashGenerator;
import com.joysis.tvi.JobFit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    // Login
    public User login(String username, String password) {

        String sql = "SELECT id, username, password, role FROM Users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");

                // Handles non-BCrypt hashes gracefully
                if (PasswordHashGenerator.verify(password, storedPassword)) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            storedPassword,
                            resultSet.getString("role")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Check email for job seeker
    public boolean emailExistsJobSeeker(String email) {

        String sql = "SELECT id FROM Job_Seeker WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            return statement.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check email for employer
    public boolean emailExistsEmployer(String email) {

        String sql = "SELECT id FROM Employer WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            return statement.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Verify password
    public boolean verifyPassword(int userId, String plainPassword) {

        String sql = "SELECT password FROM Users WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("password");
                return PasswordHashGenerator.verify(plainPassword, storedHash);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update password
    public boolean updatePassword(int userId, String newPlainPassword) {

        String sql = "UPDATE Users SET password = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, PasswordHashGenerator.hash(newPlainPassword));
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all users
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, role FROM Users ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    // Check if username exists
    public boolean usernameExists(String username) {

        String sql = "SELECT id FROM Users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            return statement.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if username exists (excluding a specific ID)
    public boolean usernameExistsExceptId(String username, int id) {

        String sql = "SELECT id FROM Users WHERE username = ? AND id <> ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setInt(2, id);
            return statement.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add user
    public boolean addUser(String username, String password, String role) {

        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, username);
            statement.setString(2, PasswordHashGenerator.hash(password));
            statement.setString(3, role);

            if (statement.executeUpdate() == 0) {
                return false;
            }

            ResultSet keys = statement.getGeneratedKeys();
            if (!keys.next()) {
                return false;
            }

            int userId = keys.getInt(1);
            return createProfile(connection, userId, role, username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update user
    public boolean updateUser(int id, String username, String password, String role) {

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            // Get the user's previous role (to detect role changes)
            String oldRole = null;
            String getRoleSql = "SELECT role FROM Users WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(getRoleSql)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    oldRole = resultSet.getString("role");
                } else {
                    connection.rollback();
                    return false;
                }
            }

            // Keep existing hashes, hash plaintext input
            String passwordToSave = PasswordHashGenerator.isBcryptHash(password)
                    ? password
                    : PasswordHashGenerator.hash(password);

            // If role changed, delete the old profile first
            if (!role.equals(oldRole)) {
                if (!deleteOldProfile(connection, id, oldRole)) {
                    connection.rollback();
                    return false;
                }
            }

            // Update the Users table
            String sql = "UPDATE Users SET username = ?, password = ?, role = ? WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, passwordToSave);
                statement.setString(3, role);
                statement.setInt(4, id);

                if (statement.executeUpdate() == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // If role changed, create the new profile
            if (!role.equals(oldRole)) {
                if (!createProfile(connection, id, role, username)) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete old profile (when role changes)
    private boolean deleteOldProfile(Connection connection, int userId, String oldRole) {

        String sql;

        if ("job_seeker".equals(oldRole)) {
            sql = "DELETE FROM Job_Seeker WHERE user_id = ?";
        } else if ("employer".equals(oldRole)) {
            sql = "DELETE FROM Employer WHERE user_id = ?";
        } else if ("admin".equals(oldRole)) {
            sql = "DELETE FROM Admin WHERE user_id = ?";
        } else {
            return true; // unknown role, nothing to delete
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Create profile for a new user based on role
    private boolean createProfile(Connection connection, int userId, String role, String username) {

        try {
            if (role.equals("job_seeker")) {
                String checkSql = "SELECT id FROM Job_Seeker WHERE user_id = ?";

                try (PreparedStatement check = connection.prepareStatement(checkSql)) {
                    check.setInt(1, userId);
                    if (check.executeQuery().next()) return true;
                }

                String sql = "INSERT INTO Job_Seeker (user_id, full_name, email, phone) VALUES (?, ?, '', NULL)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, userId);
                    statement.setString(2, username);
                    statement.executeUpdate();
                    return true;
                }
            }

            if (role.equals("employer")) {
                String checkSql = "SELECT id FROM Employer WHERE user_id = ?";

                try (PreparedStatement check = connection.prepareStatement(checkSql)) {
                    check.setInt(1, userId);
                    if (check.executeQuery().next()) return true;
                }

                String sql = "INSERT INTO Employer (user_id, company_name, email, phone) VALUES (?, ?, '', NULL)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, userId);
                    statement.setString(2, username);
                    statement.executeUpdate();
                    return true;
                }
            }

            if (role.equals("admin")) {
                String checkSql = "SELECT id FROM Admin WHERE user_id = ?";

                try (PreparedStatement check = connection.prepareStatement(checkSql)) {
                    check.setInt(1, userId);
                    if (check.executeQuery().next()) return true;
                }

                String sql = "INSERT INTO Admin (user_id, email, phone) VALUES (?, '', NULL)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, userId);
                    statement.executeUpdate();
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete user
    public boolean deleteUser(int id) {

        String sql = "DELETE FROM Users WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Register job seeker
    public boolean registerJobSeeker(String username, String password, String fullName,
                                     String email, String phone) {

        String userSql = "INSERT INTO Users (username, password, role) VALUES (?, ?, 'job_seeker')";
        String profileSql = "INSERT INTO Job_Seeker (user_id, full_name, email, phone) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            int userId;
            try (PreparedStatement userStatement = connection.prepareStatement(
                    userSql, Statement.RETURN_GENERATED_KEYS)) {

                userStatement.setString(1, username);
                userStatement.setString(2, PasswordHashGenerator.hash(password));
                userStatement.executeUpdate();

                ResultSet keys = userStatement.getGeneratedKeys();
                if (!keys.next()) {
                    connection.rollback();
                    return false;
                }

                userId = keys.getInt(1);
            }

            try (PreparedStatement profileStatement = connection.prepareStatement(profileSql)) {
                profileStatement.setInt(1, userId);
                profileStatement.setString(2, fullName);
                profileStatement.setString(3, email);
                profileStatement.setString(4, phone);
                profileStatement.executeUpdate();
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Register employer
    public boolean registerEmployer(String username, String password, String companyName,
                                    String email, String phone) {

        String userSql = "INSERT INTO Users (username, password, role) VALUES (?, ?, 'employer')";
        String profileSql = "INSERT INTO Employer (user_id, company_name, email, phone) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            int userId;
            try (PreparedStatement userStatement = connection.prepareStatement(
                    userSql, Statement.RETURN_GENERATED_KEYS)) {

                userStatement.setString(1, username);
                userStatement.setString(2, PasswordHashGenerator.hash(password));
                userStatement.executeUpdate();

                ResultSet keys = userStatement.getGeneratedKeys();
                if (!keys.next()) {
                    connection.rollback();
                    return false;
                }

                userId = keys.getInt(1);
            }

            try (PreparedStatement profileStatement = connection.prepareStatement(profileSql)) {
                profileStatement.setInt(1, userId);
                profileStatement.setString(2, companyName);
                profileStatement.setString(3, email);
                profileStatement.setString(4, phone);
                profileStatement.executeUpdate();
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}