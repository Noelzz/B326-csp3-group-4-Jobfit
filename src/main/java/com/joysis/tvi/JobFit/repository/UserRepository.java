package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.User;
import org.mindrot.jbcrypt.BCrypt;

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

            if (resultSet.next()) {

                String storedPassword =
                        resultSet.getString("password");

                if (BCrypt.checkpw(
                        password,
                        storedPassword
                )) {

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
    // CHECK USERNAME EXCEPT ID
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

        String hashedPassword =
                BCrypt.hashpw(
                        password,
                        BCrypt.gensalt()
                );

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                java.sql.Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setString(3, role);

            int affectedRows =
                    statement.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            ResultSet keys =
                    statement.getGeneratedKeys();

            if (!keys.next()) {
                return false;
            }

            int userId =
                    keys.getInt(1);

            /*
             * Create the appropriate profile automatically.
             */
            return createProfile(
                    connection,
                    userId,
                    role,
                    username
            );

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

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            /*
             * Get the user's previous role.
             */
            String oldRole = null;

            String getRoleSql = """
                    SELECT role
                    FROM Users
                    WHERE id = ?
                    """;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(getRoleSql)
            ) {

                statement.setInt(1, id);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    oldRole =
                            resultSet.getString("role");

                } else {

                    connection.rollback();
                    return false;
                }
            }

            /*
             * Determine whether the supplied password is
             * already a BCrypt hash.
             */
            String passwordToSave;

            if (password != null &&
                    (password.startsWith("$2a$") ||
                            password.startsWith("$2b$") ||
                            password.startsWith("$2y$"))) {

                passwordToSave = password;

            } else {

                passwordToSave =
                        BCrypt.hashpw(
                                password,
                                BCrypt.gensalt()
                        );
            }

            String sql = """
                    UPDATE Users
                    SET username = ?,
                        password = ?,
                        role = ?
                    WHERE id = ?
                    """;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(sql)
            ) {

                statement.setString(
                        1,
                        username
                );

                statement.setString(
                        2,
                        passwordToSave
                );

                statement.setString(
                        3,
                        role
                );

                statement.setInt(
                        4,
                        id
                );

                int affectedRows =
                        statement.executeUpdate();

                if (affectedRows == 0) {

                    connection.rollback();

                    return false;
                }
            }

            /*
             * If the role changed, create the new profile.
             */
            if (!role.equals(oldRole)) {

                boolean profileCreated =
                        createProfile(
                                connection,
                                id,
                                role,
                                username
                        );

                if (!profileCreated) {

                    connection.rollback();

                    return false;
                }
            }

            connection.commit();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            try {

                if (connection != null) {
                    connection.rollback();
                }

            } catch (Exception rollbackError) {

                rollbackError.printStackTrace();
            }

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
    // CREATE PROFILE
    // =========================

    private boolean createProfile(
            Connection connection,
            int userId,
            String role,
            String username) {

        try {

            if (role.equals("job_seeker")) {

                String checkSql = """
                        SELECT id
                        FROM Job_Seeker
                        WHERE user_id = ?
                        """;

                try (
                        PreparedStatement check =
                                connection.prepareStatement(checkSql)
                ) {

                    check.setInt(1, userId);

                    ResultSet resultSet =
                            check.executeQuery();

                    if (resultSet.next()) {
                        return true;
                    }
                }

                String sql = """
                        INSERT INTO Job_Seeker
                        (user_id, full_name, email, phone)
                        VALUES (?, ?, '', '')
                        """;

                try (
                        PreparedStatement statement =
                                connection.prepareStatement(sql)
                ) {

                    statement.setInt(1, userId);
                    statement.setString(2, username);

                    statement.executeUpdate();

                    return true;
                }
            }

            if (role.equals("employer")) {

                String checkSql = """
                        SELECT id
                        FROM Employer
                        WHERE user_id = ?
                        """;

                try (
                        PreparedStatement check =
                                connection.prepareStatement(checkSql)
                ) {

                    check.setInt(1, userId);

                    ResultSet resultSet =
                            check.executeQuery();

                    if (resultSet.next()) {
                        return true;
                    }
                }

                String sql = """
                        INSERT INTO Employer
                        (user_id, company_name, email, phone)
                        VALUES (?, ?, '', '')
                        """;

                try (
                        PreparedStatement statement =
                                connection.prepareStatement(sql)
                ) {

                    statement.setInt(1, userId);
                    statement.setString(2, username);

                    statement.executeUpdate();

                    return true;
                }
            }

            /*
             * Admin accounts do not need a profile table.
             */
            if (role.equals("admin")) {
                return true;
            }

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

            String hashedPassword =
                    BCrypt.hashpw(
                            password,
                            BCrypt.gensalt()
                    );

            PreparedStatement userStatement =
                    connection.prepareStatement(
                            userSql,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                    );

            userStatement.setString(
                    1,
                    username
            );

            userStatement.setString(
                    2,
                    hashedPassword
            );

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

            String hashedPassword =
                    BCrypt.hashpw(
                            password,
                            BCrypt.gensalt()
                    );

            PreparedStatement userStatement =
                    connection.prepareStatement(
                            userSql,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                    );

            userStatement.setString(
                    1,
                    username
            );

            userStatement.setString(
                    2,
                    hashedPassword
            );

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