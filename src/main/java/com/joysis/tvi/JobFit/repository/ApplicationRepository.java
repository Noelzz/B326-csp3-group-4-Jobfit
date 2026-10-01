package com.joysis.tvi.JobFit.repository;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.model.Applicant;
import com.joysis.tvi.JobFit.model.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRepository {

    // Add application
    public boolean addApplication(Application application) {

        String sql = "INSERT INTO Applications (job_id, job_seeker_id, application_date, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, application.getJobId());
            statement.setInt(2, application.getJobSeekerId());
            statement.setDate(3, java.sql.Date.valueOf(application.getApplicationDate()));
            statement.setString(4, application.getStatus());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Applied
    public boolean hasApplied(int jobId, int jobSeekerId) {

        String sql = "SELECT id FROM Applications WHERE job_id = ? AND job_seeker_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);
            statement.setInt(2, jobSeekerId);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get the applications for jobseeker
    public List<Application> getApplicationsByJobSeeker(int jobSeekerId) {
        List<Application> applications = new ArrayList<>();

        String sql = "SELECT id, job_id, job_seeker_id, application_date, status FROM Applications WHERE job_seeker_id = ? ORDER BY id DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobSeekerId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                LocalDate applicationDate = resultSet.getDate("application_date").toLocalDate();

                Application application =
                        new Application(
                                resultSet.getInt("id"),
                                resultSet.getInt("job_id"),
                                resultSet.getInt("job_seeker_id"),
                                applicationDate,
                                resultSet.getString("status")
                        );
                applications.add(application);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }

    // Get the applications by employer
    public List<Applicant> getApplicantsByEmployer(int employerId) {

        List<Applicant> applicants = new ArrayList<>();

        String sql = """
                SELECT a.id AS application_id, j.id AS job_id, js.id AS job_seeker_id, j.title 
                AS job_title, js.full_name, js.email, js.phone, a.application_date,a.status
                FROM Applications a INNER JOIN Jobs j ON a.job_id = j.id INNER JOIN Job_Seeker js 
                ON a.job_seeker_id = js.id WHERE j.employer_id = ? ORDER BY a.id DESC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Applicant applicant = new Applicant(
                            resultSet.getInt("application_id"),
                            resultSet.getInt("job_id"),
                            resultSet.getInt("job_seeker_id"),
                            resultSet.getString("job_title"),
                            resultSet.getString("full_name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getDate("application_date").toLocalDate(),
                            resultSet.getString("status")
                    );
                    applicant.setSkills(getSkillsForJobSeeker(applicant.getJobSeekerId()));
                    applicants.add(applicant);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applicants;
    }

    // Gets the skills for jobseekers
    private List<com.joysis.tvi.JobFit.model.Skill> getSkillsForJobSeeker(int jobSeekerId) {

        List<com.joysis.tvi.JobFit.model.Skill> skills = new ArrayList<>();

        String sql = "SELECT s.id, s.name FROM Skills s INNER JOIN Job_Seeker_Skills jss ON s.id = jss.skill_id WHERE jss.job_seeker_id = ? ORDER BY s.name";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobSeekerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    skills.add(new com.joysis.tvi.JobFit.model.Skill(resultSet.getInt("id"), resultSet.getString("name")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return skills;
    }

    // Updates the application status
    public boolean updateApplicationStatus(
            int applicationId,
            String status) {

        String sql = "UPDATE Applications SET status = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, applicationId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}