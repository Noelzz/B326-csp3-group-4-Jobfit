package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class BrowseJobsFrame extends JFrame {

    private final User user;
    private final JobController controller;

    private JTable jobsTable;
    private DefaultTableModel tableModel;

    public BrowseJobsFrame(User user) {

        this.user = user;
        this.controller = new JobController();

        setTitle("JobFit - Browse Jobs");
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
                        "BROWSE JOBS",
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

        JButton refreshButton =
                new JButton("REFRESH");

        JButton viewButton =
                new JButton("VIEW JOB");

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

        buttonPanel.add(refreshButton);
        buttonPanel.add(viewButton);
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

        refreshButton.addActionListener(
                e -> loadJobs()
        );

        viewButton.addActionListener(
                e -> viewSelectedJob()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    private void loadJobs() {

        tableModel.setRowCount(0);

        List<Job> jobs =
                controller.getAllJobs();

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

    private void viewSelectedJob() {

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

        Job selectedJob = null;

        List<Job> jobs =
                controller.getAllJobs();

        for (Job job : jobs) {

            if (job.getId() == jobId) {

                selectedJob = job;
                break;
            }
        }

        if (selectedJob != null) {

            showJobDetails(selectedJob);
        }
    }

    private void showJobDetails(Job job) {

        String categoryName =
                getCategoryName(
                        job.getCategoryId()
                );

        String message =
                "Job Title: " + job.getTitle()
                        + "\n\n"
                        + "Category: " + categoryName
                        + "\n\n"
                        + "Location: " + job.getLocation()
                        + "\n\n"
                        + "Salary: ₱"
                        + String.format(
                        "%.2f",
                        job.getSalary()
                )
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
}