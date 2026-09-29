package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
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
import java.util.List;

public class MatchedJobsFrame extends JFrame {

    private final User user;
    private final MatchReportController controller;

    private JTable matchedJobsTable;
    private DefaultTableModel tableModel;

    private JButton generateButton;
    private JButton refreshButton;
    private JButton closeButton;

    public MatchedJobsFrame(User user) {

        this.user = user;
        controller = new MatchReportController();

        setTitle("JobFit - Matched Jobs");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadMatches();
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
                        "MATCHED JOBS",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JLabel infoLabel =
                new JLabel(
                        "Jobs are matched based on your skills.",
                        SwingConstants.CENTER
                );

        infoLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JPanel headerPanel =
                new JPanel(new GridLayout(2, 1));

        headerPanel.add(titleLabel);
        headerPanel.add(infoLabel);

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Job ID",
                                "Job Title",
                                "Location",
                                "Salary",
                                "Match Score"
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

        matchedJobsTable =
                new JTable(tableModel);

        matchedJobsTable.setRowHeight(30);

        matchedJobsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(matchedJobsTable);

        generateButton =
                new JButton("GENERATE MATCHES");

        refreshButton =
                new JButton("REFRESH");

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

        buttonPanel.add(generateButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        mainPanel.add(
                headerPanel,
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

        generateButton.addActionListener(
                e -> generateMatches()
        );

        refreshButton.addActionListener(
                e -> loadMatches()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    private void generateMatches() {

        int jobSeekerId =
                getJobSeekerId();

        if (jobSeekerId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job seeker profile was not found.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        List<MatchReport> reports =
                controller.generateMatches(
                        jobSeekerId
                );

        if (reports.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No jobs with required skills were found.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            tableModel.setRowCount(0);

            return;
        }

        loadMatches();

        JOptionPane.showMessageDialog(
                this,
                "Job matches generated successfully.",
                "JobFit",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void loadMatches() {

        tableModel.setRowCount(0);

        int jobSeekerId =
                getJobSeekerId();

        if (jobSeekerId <= 0) {
            return;
        }

        List<MatchReport> reports =
                controller.getMatchesByJobSeeker(
                        jobSeekerId
                );

        for (MatchReport report : reports) {

            Job job =
                    controller.getJobById(
                            report.getJobId()
                    );

            if (job == null) {
                continue;
            }

            String matchScore =
                    String.format(
                            "%.2f%%",
                            report.getMatchScore()
                    );

            tableModel.addRow(
                    new Object[]{
                            job.getId(),
                            job.getTitle(),
                            job.getLocation(),
                            String.format(
                                    "%.2f",
                                    job.getSalary()
                            ),
                            matchScore
                    }
            );
        }
    }

    private int getJobSeekerId() {

        String sql =
                "SELECT id FROM Job_Seeker WHERE user_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

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
}