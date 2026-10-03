package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.ApplicationController;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.controller.MatchReportController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.MatchReport;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowseJobsFrame extends BaseTableFrame {

    private final User user;
    private final JobController jobController;
    private final MatchReportController matchController;
    private final ApplicationController applicationController;

    private JTable jobsTable;
    private DefaultTableModel tableModel;

    public BrowseJobsFrame(User user) {

        super(
                "JobFit - Browse Jobs",
                "Browse Jobs",
                "Explore available jobs and see how well your skills match each opportunity.",
                1050,
                680
        );

        this.user = user;
        this.jobController = new JobController();
        this.matchController = new MatchReportController();
        this.applicationController = new ApplicationController();

        createContent();
        loadJobs();
    }

    // ==========================================
    // MAIN CONTENT
    // ==========================================

    private void createContent() {

        toolbarPanel.setLayout(
                new BorderLayout()
        );

        // ==========================================
        // INFO CARD
        // ==========================================

        RoundedPanel infoCard =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        infoCard.setBackground(
                UITheme.SURFACE_BLUE
        );

        infoCard.setLayout(
                new BorderLayout()
        );

        infoCard.setBorder(
                new EmptyBorder(
                        16,
                        20,
                        16,
                        20
                )
        );

        JPanel infoText =
                new JPanel();

        infoText.setOpaque(false);

        infoText.setLayout(
                new BoxLayout(
                        infoText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel infoTitle =
                new JLabel(
                        "Available Opportunities"
                );

        infoTitle.setFont(
                UITheme.SECTION_TITLE
        );

        infoTitle.setForeground(
                UITheme.TEXT
        );

        JLabel infoDescription =
                new JLabel(
                        "Generate matches to compare your skills with each job requirement."
                );

        infoDescription.setFont(
                UITheme.SMALL
        );

        infoDescription.setForeground(
                UITheme.TEXT_SECONDARY
        );

        infoTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        infoDescription.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        infoText.add(
                infoTitle
        );

        infoText.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        infoText.add(
                infoDescription
        );

        infoCard.add(
                infoText,
                BorderLayout.CENTER
        );

        toolbarPanel.add(
                infoCard,
                BorderLayout.CENTER
        );

        // ==========================================
        // TABLE MODEL
        // ==========================================

        tableModel =
                new DefaultTableModel(
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
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        jobsTable =
                new JTable(
                        tableModel
                );

        jobsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        styleTable(
                jobsTable
        );

        jobsTable.setRowHeight(
                42
        );

        jobsTable.setFillsViewportHeight(
                true
        );

        // ==========================================
        // COLUMN WIDTHS
        // ==========================================

        jobsTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        55
                );

        jobsTable
                .getColumnModel()
                .getColumn(0)
                .setMaxWidth(
                        70
                );

        jobsTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        240
                );

        jobsTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        190
                );

        jobsTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        180
                );

        jobsTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        130
                );

        jobsTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(
                        110
                );

        // ==========================================
        // SCROLL PANE
        // ==========================================

        JScrollPane scrollPane =
                new JScrollPane(
                        jobsTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getViewport()
                .setBackground(
                        Color.WHITE
                );

        // ==========================================
        // BUTTONS
        // ==========================================

        JButton generateButton =
                new RoundedButton(
                        "Generate Matches",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        JButton viewButton =
                new RoundedButton(
                        "View Job",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        JButton applyButton =
                new RoundedButton(
                        "Apply Now",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        JButton refreshButton =
                new RoundedButton(
                        "Refresh",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        JButton closeButton =
                new RoundedButton(
                        "Close",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        generateButton.setPreferredSize(
                new Dimension(
                        155,
                        40
                )
        );

        viewButton.setPreferredSize(
                new Dimension(
                        120,
                        40
                )
        );

        applyButton.setPreferredSize(
                new Dimension(
                        120,
                        40
                )
        );

        refreshButton.setPreferredSize(
                new Dimension(
                        110,
                        40
                )
        );

        closeButton.setPreferredSize(
                new Dimension(
                        100,
                        40
                )
        );

        // ==========================================
        // BUTTON PANEL
        // ==========================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                UITheme.GAP_SM,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(
                generateButton
        );

        buttonPanel.add(
                viewButton
        );

        buttonPanel.add(
                applyButton
        );

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                closeButton
        );

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        bottomPanel.setBorder(
                new EmptyBorder(
                        UITheme.GAP_MD,
                        0,
                        0,
                        0
                )
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.EAST
        );

        // ==========================================
        // TABLE SECTION
        // ==========================================

        JPanel tableSection =
                new JPanel(
                        new BorderLayout()
                );

        tableSection.setOpaque(false);

        tableSection.add(
                scrollPane,
                BorderLayout.CENTER
        );

        tableSection.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        tableContainer.add(
                tableSection,
                BorderLayout.CENTER
        );

        // ==========================================
        // ACTIONS
        // ==========================================

        generateButton.addActionListener(
                e -> generateMatches()
        );

        viewButton.addActionListener(
                e -> viewSelectedJob()
        );

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

    // ==========================================
    // LOAD JOBS
    // ==========================================

    private void loadJobs() {

        tableModel.setRowCount(0);

        List<Object[]> rows =
                jobController.getAllJobsWithCategory();

        int jobSeekerId =
                getJobSeekerId();

        Map<Integer, Double> matchMap =
                new HashMap<>();

        if (jobSeekerId > 0) {

            List<MatchReport> matches =
                    matchController
                            .getMatchesByJobSeeker(
                                    jobSeekerId
                            );

            for (MatchReport m : matches) {

                matchMap.put(
                        m.getJobId(),
                        m.getMatchScore()
                );
            }
        }

        for (Object[] row : rows) {

            int id =
                    (int) row[0];

            String categoryName =
                    (String) row[3];

            String title =
                    (String) row[4];

            String location =
                    (String) row[6];

            double salary =
                    (double) row[7];

            String matchDisplay =
                    "--";

            if (matchMap.containsKey(id)) {

                matchDisplay =
                        String.format(
                                "%.2f%%",
                                matchMap.get(id)
                        );
            }

            tableModel.addRow(
                    new Object[]{
                            id,
                            title,
                            categoryName,
                            location,
                            String.format(
                                    "₱%,.2f",
                                    salary
                            ),
                            matchDisplay
                    }
            );
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

    // ==========================================
    // GENERATE MATCHES
    // ==========================================

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
                matchController.generateMatches(
                        jobSeekerId
                );

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

    // ==========================================
    // VIEW SELECTED JOB
    // ==========================================

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
                (int) tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        );

        String categoryName =
                (String) tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        );

        Job selectedJob = null;

        for (Job job :
                jobController.getAllJobs()) {

            if (job.getId() == jobId) {

                selectedJob = job;
                break;
            }
        }

        if (selectedJob != null) {

            showJobDetails(
                    selectedJob,
                    categoryName
            );
        }
    }

    // ==========================================
    // JOB DETAILS
    // ==========================================

    private void showJobDetails(
            Job job,
            String categoryName
    ) {

        JPanel detailsPanel =
                new JPanel();

        detailsPanel.setLayout(
                new BoxLayout(
                        detailsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        detailsPanel.setBorder(
                new EmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );

        detailsPanel.setPreferredSize(
                new Dimension(
                        450,
                        260
                )
        );

        detailsPanel.add(
                createDetailRow(
                        "Job Title",
                        job.getTitle()
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Category",
                        categoryName
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Location",
                        job.getLocation()
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Salary",
                        String.format(
                                "₱%,.2f",
                                job.getSalary()
                        )
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Description",
                        job.getDescription()
                )
        );

        JOptionPane.showMessageDialog(
                this,
                detailsPanel,
                "Job Details",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // ==========================================
    // DETAIL ROW
    // ==========================================

    private JPanel createDetailRow(
            String label,
            String value
    ) {

        JPanel row =
                new JPanel(
                        new BorderLayout(
                                14,
                                0
                        )
                );

        row.setOpaque(false);

        JLabel labelComponent =
                new JLabel(label);

        labelComponent.setFont(
                UITheme.LABEL
        );

        labelComponent.setForeground(
                UITheme.TEXT
        );

        labelComponent.setPreferredSize(
                new Dimension(
                        100,
                        24
                )
        );

        JLabel valueComponent =
                new JLabel(
                        "<html>"
                                + (value == null
                                ? ""
                                : value)
                                + "</html>"
                );

        valueComponent.setFont(
                UITheme.BODY
        );

        valueComponent.setForeground(
                UITheme.TEXT_SECONDARY
        );

        row.add(
                labelComponent,
                BorderLayout.WEST
        );

        row.add(
                valueComponent,
                BorderLayout.CENTER
        );

        return row;
    }

    // ==========================================
    // APPLY FOR JOB
    // ==========================================

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
                (int) tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        );

        String jobTitle =
                (String) tableModel
                        .getValueAt(
                                selectedRow,
                                1
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

        if (
                applicationController
                        .hasApplied(
                                jobId,
                                jobSeekerId
                        )
        ) {

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
                        "Apply for \""
                                + jobTitle
                                + "\"?",
                        "Confirm Application",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        boolean success =
                applicationController
                        .applyForJob(
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

    // ==========================================
    // GET JOB SEEKER ID
    // ==========================================

    private int getJobSeekerId() {

        String sql =
                "SELECT id FROM Job_Seeker WHERE user_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            statement.setInt(
                    1,
                    user.getId()
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(
                        "id"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}