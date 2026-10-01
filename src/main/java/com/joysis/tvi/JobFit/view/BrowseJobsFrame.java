package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.config.RoundedButton;
import com.joysis.tvi.JobFit.controller.ApplicationController;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.controller.MatchReportController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.MatchReport;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowseJobsFrame extends JFrame {

    private final User user;
    private final JobController jobController;
    private final MatchReportController matchController;
    private final ApplicationController applicationController;

    private JTable jobsTable;
    private DefaultTableModel tableModel;

    public BrowseJobsFrame(User user) {

        this.user = user;
        this.jobController = new JobController();
        this.matchController = new MatchReportController();
        this.applicationController = new ApplicationController();

        setTitle("JobFit - Browse Jobs");
        setSize(1050, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        loadJobs();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("BROWSE JOBS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel infoLabel = new JLabel(
                "Generate matches to see how well you fit each job.",
                SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.add(titleLabel);
        headerPanel.add(infoLabel);

        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Job Title",
                        "Category",
                        "Location",
                        "Salary",
                        "Match"
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

        JButton generateButton = new RoundedButton("GENERATE MATCHES");
        JButton viewButton = new RoundedButton("VIEW JOB");
        JButton applyButton = new RoundedButton("APPLY");
        JButton refreshButton = new RoundedButton("REFRESH");
        JButton closeButton = new RoundedButton("CLOSE");

        Dimension btnSize = new Dimension(130, 36);
        Dimension smallSize = new Dimension(110, 36);
        generateButton.setPreferredSize(btnSize);
        viewButton.setPreferredSize(smallSize);
        applyButton.setPreferredSize(smallSize);
        refreshButton.setPreferredSize(smallSize);
        closeButton.setPreferredSize(smallSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(generateButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        generateButton.addActionListener(e -> generateMatches());
        viewButton.addActionListener(e -> viewSelectedJob());
        applyButton.addActionListener(e -> applyForSelectedJob());
        refreshButton.addActionListener(e -> loadJobs());
        closeButton.addActionListener(e -> dispose());
    }

    private void loadJobs() {

        tableModel.setRowCount(0);

        List<Object[]> rows = jobController.getAllJobsWithCategory();

        int jobSeekerId = getJobSeekerId();

        Map<Integer, Double> matchMap = new HashMap<>();
        if (jobSeekerId > 0) {
            List<MatchReport> matches = matchController.getMatchesByJobSeeker(jobSeekerId);
            for (MatchReport m : matches) {
                matchMap.put(m.getJobId(), m.getMatchScore());
            }
        }

        for (Object[] row : rows) {

            int id = (int) row[0];
            String categoryName = (String) row[3];
            String title = (String) row[4];
            String location = (String) row[6];
            double salary = (double) row[7];

            String matchDisplay = "--";
            if (matchMap.containsKey(id)) {
                matchDisplay = String.format("%.2f%%", matchMap.get(id));
            }

            tableModel.addRow(new Object[]{
                    id,
                    title,
                    categoryName,
                    location,
                    String.format("₱%.2f", salary),
                    matchDisplay
            });
        }

        if (rows.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No jobs are currently available.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void generateMatches() {

        int jobSeekerId = getJobSeekerId();
        if (jobSeekerId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Job seeker profile was not found.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        List<MatchReport> reports = matchController.generateMatches(jobSeekerId);
        loadJobs();

        if (reports.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No jobs with required skills were found.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Match report generated successfully.",
                "JobFit",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void viewSelectedJob() {

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
        String categoryName = (String) tableModel.getValueAt(selectedRow, 2);

        Job selectedJob = null;
        for (Job job : jobController.getAllJobs()) {
            if (job.getId() == jobId) {
                selectedJob = job;
                break;
            }
        }

        if (selectedJob != null) {
            showJobDetails(selectedJob, categoryName);
        }
    }

    private void showJobDetails(Job job, String categoryName) {

        String message =
                "Job Title: " + job.getTitle()
                        + "\n\n"
                        + "Category: " + categoryName
                        + "\n\n"
                        + "Location: " + job.getLocation()
                        + "\n\n"
                        + "Salary: ₱"
                        + String.format("%.2f", job.getSalary())
                        + "\n\n"
                        + "Description:\n"
                        + job.getDescription();

        JOptionPane.showMessageDialog(
                this,
                message,
                "Job Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void applyForSelectedJob() {

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
        String jobTitle = (String) tableModel.getValueAt(selectedRow, 1);

        int jobSeekerId = getJobSeekerId();
        if (jobSeekerId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Job Seeker profile was not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (applicationController.hasApplied(jobId, jobSeekerId)) {
            JOptionPane.showMessageDialog(
                    this,
                    "You have already applied for this job.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Apply for \"" + jobTitle + "\"?",
                "Confirm Application",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean success = applicationController.applyForJob(jobId, jobSeekerId);

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Application submitted successfully!\n"
                            + "Status: Pending",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to submit application.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private int getJobSeekerId() {

        String sql = "SELECT id FROM Job_Seeker WHERE user_id = ?";

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
}