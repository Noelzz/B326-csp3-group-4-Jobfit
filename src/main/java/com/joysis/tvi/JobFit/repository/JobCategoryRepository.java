package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.JobCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobCategoryRepository {

    // =========================
    // GET ALL CATEGORIES
    // =========================

    public List<JobCategory> getAllCategories() {

        List<JobCategory> categories =
                new ArrayList<>();

        String sql = """
                SELECT id, name, description
                FROM Job_Categories
                ORDER BY name
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

                JobCategory category =
                        new JobCategory(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description")
                        );

                categories.add(category);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return categories;
    }

    // =========================
    // ADD CATEGORY
    // =========================

    public boolean addCategory(
            String name,
            String description) {

        String sql = """
                INSERT INTO Job_Categories
                (name, description)
                VALUES (?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, description);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // UPDATE CATEGORY
    // =========================

    public boolean updateCategory(
            int id,
            String name,
            String description) {

        String sql = """
                UPDATE Job_Categories
                SET name = ?,
                    description = ?
                WHERE id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // DELETE CATEGORY
    // =========================

    public boolean deleteCategory(int id) {

        String sql = """
                DELETE FROM Job_Categories
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
}