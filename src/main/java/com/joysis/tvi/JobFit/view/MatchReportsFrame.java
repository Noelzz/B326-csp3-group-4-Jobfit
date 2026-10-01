package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.config.RoundedButton;
import com.joysis.tvi.JobFit.controller.MatchReportController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.MatchReport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class MatchReportsFrame extends JFrame {

    private final MatchReportController controller;

    private JTable reportsTable;
    private DefaultTableModel tableModel;

    public MatchReportsFrame() {

        this.controller = new MatchReportController();

        setTitle("JobFit - Match Reports");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadReports();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("MATCH REPORTS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel infoLabel = new JLabel(
                "Job matching results based on required and user skills.",
                SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.add(titleLabel);
        headerPanel.add(infoLabel);

        tableModel = new DefaultTableModel(
                new Object[]{
                        "Report ID",
                        "Job Title",
                        "Job Seeker",
                        "Match Score",
                        "Created At"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        reportsTable = new JTable(tableModel);
        reportsTable.setRowHeight(30);
        reportsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(reportsTable);

        JButton refreshButton = new RoundedButton("REFRESH");
        JButton closeButton = new RoundedButton("CLOSE");

        Dimension btnSize = new Dimension(110, 36);
        refreshButton.setPreferredSize(btnSize);
        closeButton.setPreferredSize(btnSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        refreshButton.addActionListener(e -> loadReports());
        closeButton.addActionListener(e -> dispose());
    }

    private void loadReports() {

        tableModel.setRowCount(0);

        List<MatchReport> reports = controller.getAllMatchReports();

        for (MatchReport report : reports) {
            Job job = controller.getJobById(report.getJobId());
            String jobTitle = job != null ? job.getTitle() : "Unknown Job";

            String jobSeekerName = getJobSeekerName(report.getJobSeekerId());
            String matchScore = String.format("%.2f%%", report.getMatchScore());
            String createdAt = report.getCreatedAt() != null
                    ? report.getCreatedAt().toString()
                    : "";

            tableModel.addRow(new Object[]{
                    report.getId(),
                    jobTitle,
                    jobSeekerName,
                    matchScore,
                    createdAt
            });
        }
    }

    private String getJobSeekerName(int jobSeekerId) {

        String sql = "SELECT full_name FROM Job_Seeker WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobSeekerId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("full_name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown Job Seeker";
    }
}