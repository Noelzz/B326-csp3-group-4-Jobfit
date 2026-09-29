package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.joysis.tvi.JobFit.model.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobRepository {

    public List<Job> getAllJobs() {

        List<Job> jobs = new ArrayList<>();

        String sql = """
            SELECT id,
                   employer_id,
                   category_id,
                   title,
                   description,
                   location,
                   salary
            FROM Jobs
            ORDER BY id DESC
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Job job =
                        new Job(
                                resultSet.getInt("id"),
                                resultSet.getInt("employer_id"),
                                resultSet.getInt("category_id"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                resultSet.getString("location"),
                                resultSet.getDouble("salary")
                        );

                jobs.add(job);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }

    public List<Job> getJobsByEmployer(int employerId) {

        List<Job> jobs = new ArrayList<>();

        String sql = """
            SELECT id,
                   employer_id,
                   category_id,
                   title,
                   description,
                   location,
                   salary
            FROM Jobs
            WHERE employer_id = ?
            ORDER BY id DESC
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, employerId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Job job =
                        new Job(
                                resultSet.getInt("id"),
                                resultSet.getInt("employer_id"),
                                resultSet.getInt("category_id"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                resultSet.getString("location"),
                                resultSet.getDouble("salary")
                        );

                jobs.add(job);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }

    public boolean addJob(Job job) {

        String sql = """
                INSERT INTO Jobs
                (employer_id, category_id, title, description, location, salary)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, job.getEmployerId());
            statement.setInt(2, job.getCategoryId());
            statement.setString(3, job.getTitle());
            statement.setString(4, job.getDescription());
            statement.setString(5, job.getLocation());
            statement.setDouble(6, job.getSalary());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}