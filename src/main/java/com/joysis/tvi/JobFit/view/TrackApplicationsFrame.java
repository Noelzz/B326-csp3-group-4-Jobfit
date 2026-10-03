package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.ApplicationController;
import com.joysis.tvi.JobFit.model.Application;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TrackApplicationsFrame extends BaseTableFrame {

    private final User user;
    private final ApplicationController controller;

    private JTable applicationsTable;
    private DefaultTableModel tableModel;

    public TrackApplicationsFrame(User user) {

        super(
                "JobFit - Track Applications",
                "Track Applications",
                "Monitor the status of your submitted job applications.",
                900,
                620
        );

        this.user = user;
        this.controller = new ApplicationController();

        createContent();
        loadApplications();
    }

    private void createContent() {

        toolbarPanel.setLayout(
                new BorderLayout()
        );

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
                        "Application Status"
                );

        infoTitle.setFont(
                UITheme.SECTION_TITLE
        );

        infoTitle.setForeground(
                UITheme.TEXT
        );

        JLabel infoDescription =
                new JLabel(
                        "View your submitted applications and their current status."
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

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Application ID",
                                "Job Title",
                                "Application Date",
                                "Status"
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

        applicationsTable =
                new JTable(
                        tableModel
                );

        applicationsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        styleTable(
                applicationsTable
        );

        applicationsTable.setRowHeight(
                42
        );

        applicationsTable.setFillsViewportHeight(
                true
        );

        applicationsTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        110
                );

        applicationsTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        260
                );

        applicationsTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        220
                );

        applicationsTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        150
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        applicationsTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getViewport()
                .setBackground(
                        Color.WHITE
                );

        JButton refreshButton =
                new RoundedButton(
                        "Refresh",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        JButton closeButton =
                new RoundedButton(
                        "Close",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        refreshButton.setPreferredSize(
                new Dimension(
                        120,
                        40
                )
        );

        closeButton.setPreferredSize(
                new Dimension(
                        100,
                        40
                )
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

        refreshButton.addActionListener(
                e -> loadApplications()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    private void loadApplications() {

        tableModel.setRowCount(0);

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

        List<Application> applications =
                controller.getApplicationsByJobSeeker(
                        jobSeekerId
                );

        for (Application application : applications) {

            String jobTitle =
                    getJobTitle(
                            application.getJobId()
                    );

            tableModel.addRow(
                    new Object[]{
                            application.getId(),
                            jobTitle,
                            application.getApplicationDate(),
                            application.getStatus()
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

    private String getJobTitle(
            int jobId
    ) {

        String sql =
                "SELECT title FROM Jobs WHERE id = ?";

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
                    jobId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getString(
                        "title"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "Unknown Job";
    }
}