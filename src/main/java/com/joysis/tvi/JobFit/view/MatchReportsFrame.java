package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.MatchReportController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.MatchReport;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadReports();
    }

    private void createGUI() {

        // ==========================================
        // MAIN BACKGROUND
        // ==========================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        UITheme.PAGE_PADDING,
                        UITheme.PAGE_PADDING,
                        UITheme.PAGE_PADDING,
                        UITheme.PAGE_PADDING
                )
        );

        // ==========================================
        // PAGE HEADER
        // ==========================================

        JPanel headerPanel =
                new JPanel();

        headerPanel.setOpaque(false);

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "Match Reports"
                );

        titleLabel.setFont(
                UITheme.PAGE_TITLE
        );

        titleLabel.setForeground(
                UITheme.TEXT
        );

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel infoLabel =
                new JLabel(
                        "Review job matching results based on required skills and job seeker skills."
                );

        infoLabel.setFont(
                UITheme.BODY
        );

        infoLabel.setForeground(
                UITheme.TEXT_SECONDARY
        );

        infoLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        headerPanel.add(
                titleLabel
        );

        headerPanel.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        headerPanel.add(
                infoLabel
        );

        // ==========================================
        // REPORT CARD
        // ==========================================

        RoundedPanel reportCard =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        reportCard.setBackground(
                UITheme.SURFACE
        );

        reportCard.setLayout(
                new BorderLayout()
        );

        reportCard.setBorder(
                new EmptyBorder(
                        UITheme.CARD_PADDING,
                        UITheme.CARD_PADDING,
                        UITheme.CARD_PADDING,
                        UITheme.CARD_PADDING
                )
        );

        // ==========================================
        // CARD HEADER
        // ==========================================

        JPanel cardHeader =
                new JPanel();

        cardHeader.setOpaque(false);

        cardHeader.setLayout(
                new BoxLayout(
                        cardHeader,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel sectionTitle =
                new JLabel(
                        "Matching Results"
                );

        sectionTitle.setFont(
                UITheme.SECTION_TITLE
        );

        sectionTitle.setForeground(
                UITheme.TEXT
        );

        sectionTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel sectionInfo =
                new JLabel(
                        "View generated match scores for job seekers and available jobs."
                );

        sectionInfo.setFont(
                UITheme.SMALL
        );

        sectionInfo.setForeground(
                UITheme.TEXT_SECONDARY
        );

        sectionInfo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardHeader.add(
                sectionTitle
        );

        cardHeader.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        cardHeader.add(
                sectionInfo
        );

        cardHeader.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        UITheme.GAP_MD,
                        0
                )
        );

        // ==========================================
        // TABLE MODEL
        // ==========================================

        tableModel =
                new DefaultTableModel(
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
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        reportsTable =
                new JTable(
                        tableModel
                );

        // ==========================================
        // TABLE DESIGN
        // ==========================================

        reportsTable.setFont(
                UITheme.BODY
        );

        reportsTable.setForeground(
                UITheme.TEXT
        );

        reportsTable.setBackground(
                Color.WHITE
        );

        reportsTable.setSelectionBackground(
                UITheme.LIGHT_BLUE
        );

        reportsTable.setSelectionForeground(
                UITheme.TEXT
        );

        reportsTable.setRowHeight(
                40
        );

        reportsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        reportsTable.setShowVerticalLines(
                false
        );

        reportsTable.setGridColor(
                UITheme.BORDER
        );

        reportsTable.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );

        reportsTable.setFillsViewportHeight(
                true
        );

        // TABLE HEADER
        reportsTable
                .getTableHeader()
                .setFont(
                        UITheme.LABEL
                );

        reportsTable
                .getTableHeader()
                .setForeground(
                        UITheme.TEXT
                );

        reportsTable
                .getTableHeader()
                .setBackground(
                        new Color(
                                236,
                                244,
                                253
                        )
                );

        reportsTable
                .getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                42
                        )
                );

        // ==========================================
        // COLUMN WIDTHS
        // ==========================================

        reportsTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        80
                );

        reportsTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        220
                );

        reportsTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        200
                );

        reportsTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        120
                );

        reportsTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        210
                );

        // ==========================================
        // SCROLL PANE
        // ==========================================

        JScrollPane scrollPane =
                new JScrollPane(
                        reportsTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER
                )
        );

        scrollPane.getViewport()
                .setBackground(
                        Color.WHITE
                );

        // ==========================================
        // BUTTONS
        // ==========================================

        JButton refreshButton =
                new RoundedButton(
                        "Refresh Reports",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        JButton closeButton =
                new RoundedButton(
                        "Close",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        Dimension buttonSize =
                new Dimension(
                        145,
                        40
                );

        refreshButton.setPreferredSize(
                buttonSize
        );

        closeButton.setPreferredSize(
                buttonSize
        );

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
        // BUILD REPORT CARD
        // ==========================================

        reportCard.add(
                cardHeader,
                BorderLayout.NORTH
        );

        reportCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        reportCard.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // CONTENT WRAPPER
        // ==========================================

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setOpaque(false);

        contentPanel.setBorder(
                new EmptyBorder(
                        UITheme.GAP_LG,
                        0,
                        0,
                        0
                )
        );

        contentPanel.add(
                reportCard,
                BorderLayout.CENTER
        );

        // ==========================================
        // FINAL LAYOUT
        // ==========================================

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        setContentPane(
                mainPanel
        );

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        refreshButton.addActionListener(
                e -> loadReports()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    // ==========================================
    // LOAD MATCH REPORTS
    // ==========================================

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

            String jobSeekerName =
                    getJobSeekerName(
                            report.getJobSeekerId()
                    );

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
                            jobTitle,
                            jobSeekerName,
                            matchScore,
                            createdAt
                    }
            );
        }
    }

    // ==========================================
    // GET JOB SEEKER NAME
    // ==========================================

    private String getJobSeekerName(
            int jobSeekerId
    ) {

        String sql =
                "SELECT full_name FROM Job_Seeker WHERE id = ?";

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
                    jobSeekerId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getString(
                        "full_name"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "Unknown Job Seeker";
    }
}