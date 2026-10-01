package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.MatchReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchReportRepository {

    //Save Match Report
    public boolean saveMatchReport(MatchReport report) {

        String sql = """
                INSERT INTO Match_Reports (job_id, job_seeker_id, match_score, created_at) 
                VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE match_score = VALUES(match_score), created_at = VALUES(created_at)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, report.getJobId());
            statement.setInt(2, report.getJobSeekerId());
            statement.setDouble(3, report.getMatchScore());
            statement.setObject(4, report.getCreatedAt());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //Match report by jobseeker
    public List<MatchReport> getMatchReportsByJobSeeker(int jobSeekerId) {

        List<MatchReport> reports = new ArrayList<>();
        String sql = """
                SELECT id, job_id, job_seeker_id, match_score, created_at
                FROM Match_Reports WHERE job_seeker_id = ? ORDER BY match_score DESC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobSeekerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                MatchReport report =
                        new MatchReport(
                                resultSet.getInt("id"),
                                resultSet.getInt("job_id"),
                                resultSet.getInt("job_seeker_id"),
                                resultSet.getDouble("match_score"),
                                createdAt
                        );
                reports.add(report);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }

    //Get all match reports
    public List<MatchReport> getAllMatchReports() {

        List<MatchReport> reports = new ArrayList<>();
        String sql = "SELECT mr.id, mr.job_id, mr.job_seeker_id, mr.match_score, mr.created_at FROM Match_Reports mr ORDER BY mr.match_score DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                MatchReport report =
                        new MatchReport(
                                resultSet.getInt("id"),
                                resultSet.getInt("job_id"),
                                resultSet.getInt("job_seeker_id"),
                                resultSet.getDouble("match_score"),
                                createdAt
                        );

                reports.add(report);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
}