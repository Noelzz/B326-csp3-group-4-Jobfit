package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.MatchReportController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.MatchReport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MatchReportsFrame extends JFrame {

    private final MatchReportController controller;

    private JTable reportsTable;
    private DefaultTableModel tableModel;

    public MatchReportsFrame() {

        controller = new MatchReportController();

        setTitle("JobFit - Match Reports");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadReports();
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
                        "MATCH REPORTS",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Report ID",
                                "Job ID",
                                "Job Title",
                                "Job Seeker ID",
                                "Match Score",
                                "Created At"
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

        reportsTable =
                new JTable(tableModel);

        reportsTable.setRowHeight(30);

        JScrollPane scrollPane =
                new JScrollPane(reportsTable);

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

        refreshButton.addActionListener(
                e -> loadReports()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    private void loadReports() {

        tableModel.setRowCount(0);

        List<MatchReport> reports =
                controller.getAllMatchReports();

        for (MatchReport report : reports) {

            Job job =
                    controller.getJobById(
                            report.getJobId()
                    );

            String jobTitle =
                    job != null
                            ? job.getTitle()
                            : "Unknown Job";

            String matchScore =
                    String.format(
                            "%.2f%%",
                            report.getMatchScore()
                    );

            String createdAt =
                    report.getCreatedAt() != null
                            ? report.getCreatedAt().toString()
                            : "";

            tableModel.addRow(
                    new Object[]{
                            report.getId(),
                            report.getJobId(),
                            jobTitle,
                            report.getJobSeekerId(),
                            matchScore,
                            createdAt
                    }
            );
        }
    }
}