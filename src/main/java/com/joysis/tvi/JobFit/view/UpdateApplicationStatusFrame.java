package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.ApplicationController;
import com.joysis.tvi.JobFit.model.Applicant;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UpdateApplicationStatusFrame extends BaseTableFrame {

    private final User user;
    private final ApplicationController controller;

    private JTable applicantsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> statusComboBox;

    public UpdateApplicationStatusFrame(User user) {

        super(
                "JobFit - Update Application Status",
                "Update Application Status",
                "Review applicants and update their application status.",
                1100,
                680
        );

        this.user = user;
        this.controller = new ApplicationController();

        createContent();
        loadApplicants();
    }

    private void createContent() {

        toolbarPanel.setLayout(
                new BorderLayout()
        );

        // ==========================================
        // STATUS CONTROL CARD
        // ==========================================

        RoundedPanel controlCard =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        controlCard.setBackground(
                UITheme.SURFACE_BLUE
        );

        controlCard.setLayout(
                new BorderLayout(
                        UITheme.GAP_MD,
                        0
                )
        );

        controlCard.setBorder(
                new EmptyBorder(
                        16,
                        20,
                        16,
                        20
                )
        );

        JPanel infoPanel =
                new JPanel();

        infoPanel.setOpaque(false);

        infoPanel.setLayout(
                new BoxLayout(
                        infoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel sectionTitle =
                new JLabel(
                        "Application Review"
                );

        sectionTitle.setFont(
                UITheme.SECTION_TITLE
        );

        sectionTitle.setForeground(
                UITheme.TEXT
        );

        JLabel sectionInfo =
                new JLabel(
                        "Select an applicant below, choose a new status, then update."
                );

        sectionInfo.setFont(
                UITheme.SMALL
        );

        sectionInfo.setForeground(
                UITheme.TEXT_SECONDARY
        );

        sectionTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        sectionInfo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        infoPanel.add(
                sectionTitle
        );

        infoPanel.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        infoPanel.add(
                sectionInfo
        );

        // ==========================================
        // STATUS CONTROLS
        // ==========================================

        JPanel statusPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        statusPanel.setOpaque(false);

        JLabel statusLabel =
                new JLabel(
                        "New Status"
                );

        statusLabel.setFont(
                UITheme.LABEL
        );

        statusLabel.setForeground(
                UITheme.TEXT
        );

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "Pending",
                                "Reviewed",
                                "Accepted",
                                "Rejected"
                        }
                );

        styleComboBox(
                statusComboBox
        );

        JButton updateButton =
                new RoundedButton(
                        "Update Status",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        updateButton.setPreferredSize(
                new Dimension(
                        140,
                        40
                )
        );

        statusPanel.add(
                statusLabel
        );

        statusPanel.add(
                statusComboBox
        );

        statusPanel.add(
                updateButton
        );

        controlCard.add(
                infoPanel,
                BorderLayout.CENTER
        );

        controlCard.add(
                statusPanel,
                BorderLayout.EAST
        );

        toolbarPanel.add(
                controlCard,
                BorderLayout.CENTER
        );

        // ==========================================
        // TABLE MODEL
        // ==========================================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Application ID",
                                "Job Title",
                                "Applicant Name",
                                "Email",
                                "Date",
                                "Current Status"
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

        applicantsTable =
                new JTable(
                        tableModel
                );

        applicantsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        styleTable(
                applicantsTable
        );

        applicantsTable.setRowHeight(
                42
        );

        applicantsTable.setFillsViewportHeight(
                true
        );

        applicantsTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        100
                );

        applicantsTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        200
                );

        applicantsTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        190
                );

        applicantsTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        220
                );

        applicantsTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        140
                );

        applicantsTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(
                        130
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        applicantsTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getViewport()
                .setBackground(
                        Color.WHITE
                );

        // ==========================================
        // BOTTOM BUTTONS
        // ==========================================

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

        updateButton.addActionListener(
                e -> updateStatus()
        );

        refreshButton.addActionListener(
                e -> loadApplicants()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    private void styleComboBox(
            JComboBox<String> comboBox
    ) {

        comboBox.setFont(
                UITheme.BODY
        );

        comboBox.setForeground(
                UITheme.TEXT
        );

        comboBox.setBackground(
                Color.WHITE
        );

        comboBox.setPreferredSize(
                new Dimension(
                        140,
                        UITheme.FIELD_HEIGHT
                )
        );

        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER_BLUE
                )
        );
    }

    private void loadApplicants() {

        tableModel.setRowCount(0);

        int employerId =
                getEmployerId();

        if (employerId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Employer profile was not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        List<Applicant> applicants =
                controller.getApplicantsByEmployer(
                        employerId
                );

        for (Applicant applicant : applicants) {

            tableModel.addRow(
                    new Object[]{
                            applicant.getApplicationId(),
                            applicant.getJobTitle(),
                            applicant.getFullName(),
                            applicant.getEmail(),
                            applicant.getApplicationDate(),
                            applicant.getStatus()
                    }
            );
        }
    }

    private void updateStatus() {

        int selectedRow =
                applicantsTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an applicant first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int applicationId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        String currentStatus =
                tableModel.getValueAt(
                        selectedRow,
                        5
                ).toString();

        String newStatus =
                statusComboBox
                        .getSelectedItem()
                        .toString();

        if (currentStatus.equals(newStatus)) {

            JOptionPane.showMessageDialog(
                    this,
                    "The application already has this status.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Change application status to "
                                + newStatus
                                + "?",
                        "Confirm Status Update",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success =
                controller.updateApplicationStatus(
                        applicationId,
                        newStatus
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Application status updated successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadApplicants();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update application status.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private int getEmployerId() {

        String sql =
                "SELECT id FROM Employer WHERE user_id = ?";

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