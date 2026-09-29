package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    // =========================
    // LOGIN
    // =========================

    public User login(
            String username,
            String password) {

        String sql = """
                SELECT id, username, password, role
                FROM Users
                WHERE username = ?
                  AND password = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // GET ALL USERS
    // =========================

    public List<User> getAllUsers() {

        List<User> users =
                new ArrayList<>();

        String sql = """
                SELECT id, username, password, role
                FROM Users
                ORDER BY id
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                User user =
                        new User(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("role")
                        );

                users.add(user);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return users;
    }

    // =========================
    // CHECK USERNAME
    // =========================

    public boolean usernameExists(
            String username) {

        String sql = """
                SELECT id
                FROM Users
                WHERE username = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // CHECK USERNAME
    // EXCLUDING CURRENT USER
    // =========================

    public boolean usernameExistsExceptId(
            String username,
            int id) {

        String sql = """
                SELECT id
                FROM Users
                WHERE username = ?
                  AND id <> ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setInt(2, id);

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // ADD USER
    // =========================

    public boolean addUser(
            String username,
            String password,
            String role) {

        String sql = """
                INSERT INTO Users
                (username, password, role)
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // UPDATE USER
    // =========================

    public boolean updateUser(
            int id,
            String username,
            String password,
            String role) {

        String sql = """
                UPDATE Users
                SET username = ?,
                    password = ?,
                    role = ?
                WHERE id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            statement.setInt(4, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // DELETE USER
    // =========================

    public boolean deleteUser(int id) {

        String sql = """
                DELETE FROM Users
                WHERE id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // REGISTER JOB SEEKER
    // =========================

    public boolean registerJobSeeker(
            String username,
            String password,
            String fullName,
            String email,
            String phone) {

        String userSql = """
                INSERT INTO Users
                (username, password, role)
                VALUES (?, ?, 'job_seeker')
                """;

        String profileSql = """
                INSERT INTO Job_Seeker
                (user_id, full_name, email, phone)
                VALUES (?, ?, ?, ?)
                """;

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            PreparedStatement userStatement =
                    connection.prepareStatement(
                            userSql,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                    );

            userStatement.setString(1, username);
            userStatement.setString(2, password);

            userStatement.executeUpdate();

            ResultSet generatedKeys =
                    userStatement.getGeneratedKeys();

            if (!generatedKeys.next()) {

                connection.rollback();

                return false;
            }

            int userId =
                    generatedKeys.getInt(1);

            PreparedStatement profileStatement =
                    connection.prepareStatement(
                            profileSql
                    );

            profileStatement.setInt(
                    1,
                    userId
            );

            profileStatement.setString(
                    2,
                    fullName
            );

            profileStatement.setString(
                    3,
                    email
            );

            profileStatement.setString(
                    4,
                    phone
            );

            profileStatement.executeUpdate();

            connection.commit();

            return true;

        } catch (Exception e) {

            try {

                if (connection != null) {
                    connection.rollback();
                }

            } catch (Exception rollbackError) {

                rollbackError.printStackTrace();
            }

            e.printStackTrace();

            return false;

        } finally {

            try {

                if (connection != null) {
                    connection.close();
                }

            } catch (Exception closeError) {

                closeError.printStackTrace();
            }
        }
    }

    // =========================
    // REGISTER EMPLOYER
    // =========================

    public boolean registerEmployer(
            String username,
            String password,
            String companyName,
            String email,
            String phone) {

        String userSql = """
                INSERT INTO Users
                (username, password, role)
                VALUES (?, ?, 'employer')
                """;

        String profileSql = """
                INSERT INTO Employer
                (user_id, company_name, email, phone)
                VALUES (?, ?, ?, ?)
                """;

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            PreparedStatement userStatement =
                    connection.prepareStatement(
                            userSql,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                    );

            userStatement.setString(1, username);
            userStatement.setString(2, password);

            userStatement.executeUpdate();

            ResultSet generatedKeys =
                    userStatement.getGeneratedKeys();

            if (!generatedKeys.next()) {

                connection.rollback();

                return false;
            }

            int userId =
                    generatedKeys.getInt(1);

            PreparedStatement profileStatement =
                    connection.prepareStatement(
                            profileSql
                    );

            profileStatement.setInt(
                    1,
                    userId
            );

            profileStatement.setString(
                    2,
                    companyName
            );

            profileStatement.setString(
                    3,
                    email
            );

            profileStatement.setString(
                    4,
                    phone
            );

            profileStatement.executeUpdate();

            connection.commit();

            return true;

        } catch (Exception e) {

            try {

                if (connection != null) {
                    connection.rollback();
                }

            } catch (Exception rollbackError) {

                rollbackError.printStackTrace();
            }

            e.printStackTrace();

            return false;

        } finally {

            try {

                if (connection != null) {
                    connection.close();
                }

            } catch (Exception closeError) {

                closeError.printStackTrace();
            }
        }
    }
}