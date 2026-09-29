package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.model.Job;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class AdminManageJobsFrame extends JFrame {

    private final JobController controller;

    private JTable jobsTable;
    private DefaultTableModel tableModel;

    private JButton refreshButton;
    private JButton deleteButton;
    private JButton closeButton;

    public AdminManageJobsFrame() {

        controller = new JobController();

        setTitle("JobFit - Manage Jobs");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadJobs();
    }

    private void createGUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "MANAGE JOBS",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Employer ID",
                                "Category ID",
                                "Title",
                                "Description",
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

        jobsTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        jobsTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(80);

        jobsTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(80);

        jobsTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(150);

        jobsTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(250);

        jobsTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(150);

        jobsTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(100);

        JScrollPane scrollPane =
                new JScrollPane(jobsTable);

        refreshButton =
                new JButton("REFRESH");

        deleteButton =
                new JButton("DELETE");

        closeButton =
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
        buttonPanel.add(deleteButton);
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

        deleteButton.addActionListener(
                e -> deleteJob()
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

            tableModel.addRow(
                    new Object[]{
                            job.getId(),
                            job.getEmployerId(),
                            job.getCategoryId(),
                            job.getTitle(),
                            job.getDescription(),
                            job.getLocation(),
                            String.format(
                                    "%.2f",
                                    job.getSalary()
                            )
                    }
            );
        }
    }

    private void deleteJob() {

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

        String jobTitle =
                tableModel.getValueAt(
                        selectedRow,
                        3
                ).toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete job \""
                                + jobTitle
                                + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        if (deleteJobFromDatabase(jobId)) {

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
                    "Unable to delete this job.\n"
                            + "It may have related applications, "
                            + "required skills, or match reports.",
                    "Delete Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private boolean deleteJobFromDatabase(int jobId) {

        String sql =
                "DELETE FROM Jobs WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, jobId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}