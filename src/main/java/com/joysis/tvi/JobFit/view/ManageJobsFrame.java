package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.config.RoundedButton;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.controller.JobRequiredSkillController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobRequiredSkill;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ManageJobsFrame extends JFrame {

    private final User user;
    private final JobController jobController;
    private final JobRequiredSkillController requiredSkillController;

    private JTable jobsTable;
    private DefaultTableModel tableModel;

    public ManageJobsFrame(User user) {

        this.user = user;
        this.jobController = new JobController();
        this.requiredSkillController = new JobRequiredSkillController();

        setTitle("JobFit - Manage Job Posts");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        loadJobs();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("MANAGE JOB POSTS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Job Title",
                        "Category",
                        "Location",
                        "Salary"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jobsTable = new JTable(tableModel);
        jobsTable.setRowHeight(30);
        jobsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(jobsTable);

        JButton refreshButton = new RoundedButton("REFRESH");
        JButton viewDetailsButton = new RoundedButton("VIEW DETAILS");
        JButton deleteButton = new RoundedButton("DELETE", new Color(220, 70, 70), new Color(195, 55, 55));
        JButton closeButton = new RoundedButton("CLOSE");

        Dimension btnSize = new Dimension(120, 36);
        refreshButton.setPreferredSize(btnSize);
        viewDetailsButton.setPreferredSize(btnSize);
        deleteButton.setPreferredSize(btnSize);
        closeButton.setPreferredSize(btnSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(refreshButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        refreshButton.addActionListener(e -> loadJobs());
        viewDetailsButton.addActionListener(e -> viewJobDetails());
        deleteButton.addActionListener(e -> deleteSelectedJob());
        closeButton.addActionListener(e -> dispose());
    }

    private void loadJobs() {

        tableModel.setRowCount(0);

        int employerId = getEmployerId();

        if (employerId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Employer profile was not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        List<Job> jobs = jobController.getJobsByEmployer(employerId);

        for (Job job : jobs) {
            String categoryName = getCategoryName(job.getCategoryId());

            tableModel.addRow(new Object[]{
                    job.getId(),
                    job.getTitle(),
                    categoryName,
                    job.getLocation(),
                    job.getSalary()
            });
        }
    }

    private void viewJobDetails() {

        int selectedRow = jobsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int jobId = (int) tableModel.getValueAt(selectedRow, 0);

        Job job = null;
        List<Job> jobs = jobController.getJobsByEmployer(getEmployerId());
        for (Job j : jobs) {
            if (j.getId() == jobId) {
                job = j;
                break;
            }
        }

        if (job == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load job details.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String categoryName = getCategoryName(job.getCategoryId());
        String categoryDescription = getCategoryDescription(job.getCategoryId());

        StringBuilder skillsText = new StringBuilder();
        List<JobRequiredSkill> requiredSkills =
                requiredSkillController.getRequiredSkillsByJob(jobId);

        if (requiredSkills.isEmpty()) {
            skillsText.append("—");
        } else {
            for (int i = 0; i < requiredSkills.size(); i++) {
                if (i > 0) skillsText.append(", ");
                skillsText.append(requiredSkills.get(i).getSkillName());
            }
        }

        String message =
                "Job Title: " + job.getTitle()
                        + "\n\n"
                        + "Category: " + categoryName
                        + "\n"
                        + "Description: " + categoryDescription
                        + "\n\n"
                        + "Location: " + job.getLocation()
                        + "\n"
                        + "Salary: ₱" + String.format("%.2f", job.getSalary())
                        + "\n\n"
                        + "Required Skills:\n" + skillsText
                        + "\n\n"
                        + "Job Description:\n" + job.getDescription();

        JOptionPane.showMessageDialog(
                this,
                message,
                "Job Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private int getEmployerId() {

        String sql = "SELECT id FROM Employer WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, user.getId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String getCategoryName(int categoryId) {

        String sql = "SELECT name FROM Job_Categories WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private String getCategoryDescription(int categoryId) {

        String sql = "SELECT description FROM Job_Categories WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String desc = resultSet.getString("description");
                return desc == null ? "—" : desc;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "—";
    }

    private void deleteSelectedJob() {

        int selectedRow = jobsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int jobId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this job?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        if (deleteJob(jobId)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Job deleted successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            loadJobs();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete job.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean deleteJob(int jobId) {

        String sql = "DELETE FROM Jobs WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);
            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}