package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.ApplicationController;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.User;
import com.joysis.tvi.JobFit.config.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ApplyJobFrame extends JFrame {

    private final User user;
    private final JobController jobController;
    private final ApplicationController applicationController;

    private JTable jobsTable;
    private DefaultTableModel tableModel;

    public ApplyJobFrame(User user) {

        this.user = user;
        this.jobController = new JobController();
        this.applicationController =
                new ApplicationController();

        setTitle("JobFit - Apply for Job");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        loadJobs();
    }

    private void createGUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "APPLY FOR JOB",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        tableModel =
                new DefaultTableModel(
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
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        jobsTable =
                new JTable(tableModel);

        jobsTable.setRowHeight(30);

        jobsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(jobsTable);

        JButton applyButton =
                new JButton("APPLY FOR SELECTED JOB");

        JButton refreshButton =
                new JButton("REFRESH");

        JButton closeButton =
                new JButton("CLOSE");

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                10
                        )
                );

        buttonPanel.add(applyButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        applyButton.addActionListener(
                e -> applyForSelectedJob()
        );

        refreshButton.addActionListener(
                e -> loadJobs()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    private void loadJobs() {

        tableModel.setRowCount(0);

        List<Job> jobs =
                jobController.getAllJobs();

        for (Job job : jobs) {

            String categoryName =
                    getCategoryName(
                            job.getCategoryId()
                    );

            tableModel.addRow(
                    new Object[]{
                            job.getId(),
                            job.getTitle(),
                            categoryName,
                            job.getLocation(),
                            String.format(
                                    "₱%.2f",
                                    job.getSalary()
                            )
                    }
            );
        }

        if (jobs.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No jobs are currently available.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void applyForSelectedJob() {

        int selectedRow =
                jobsTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int jobId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        int jobSeekerId =
                getJobSeekerId();

        if (jobSeekerId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job Seeker profile was not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        boolean alreadyApplied =
                applicationController.hasApplied(
                        jobId,
                        jobSeekerId
                );

        if (alreadyApplied) {

            JOptionPane.showMessageDialog(
                    this,
                    "You have already applied for this job.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to apply for this job?",
                        "Confirm Application",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success =
                applicationController.applyForJob(
                        jobId,
                        jobSeekerId
                );

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

        String sql =
                "SELECT id FROM Job_Seeker WHERE user_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    user.getId()
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt("id");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    private String getCategoryName(int categoryId) {

        String sql =
                "SELECT name FROM Job_Categories WHERE id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    categoryId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getString("name");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "Unknown";
    }
}