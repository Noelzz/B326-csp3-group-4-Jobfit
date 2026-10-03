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

public class ViewApplicantsFrame extends BaseTableFrame {

    private final User user;
    private final ApplicationController controller;

    private JTable applicantsTable;
    private DefaultTableModel tableModel;

    public ViewApplicantsFrame(User user) {

        super(
                "JobFit - View Applicants",
                "View Applicants",
                "Review applicants who applied to your job postings.",
                1100,
                650
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
                        "Applicant List"
                );

        infoTitle.setFont(
                UITheme.SECTION_TITLE
        );

        infoTitle.setForeground(
                UITheme.TEXT
        );

        JLabel infoDescription =
                new JLabel(
                        "Select an applicant below to view their profile and skills."
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
                                "Applicant Name",
                                "Email",
                                "Phone",
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
                        180
                );

        applicantsTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        180
                );

        applicantsTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        210
                );

        applicantsTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        130
                );

        applicantsTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(
                        160
                );

        applicantsTable
                .getColumnModel()
                .getColumn(6)
                .setPreferredWidth(
                        120
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

        JButton refreshButton =
                new RoundedButton(
                        "Refresh",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        JButton viewButton =
                new RoundedButton(
                        "View Applicant",
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
                        110,
                        40
                )
        );

        viewButton.setPreferredSize(
                new Dimension(
                        145,
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
                viewButton
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
                e -> loadApplicants()
        );

        viewButton.addActionListener(
                e -> viewSelectedApplicant()
        );

        closeButton.addActionListener(
                e -> dispose()
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
                            applicant.getPhone(),
                            applicant.getApplicationDate(),
                            applicant.getStatus()
                    }
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

    private void viewSelectedApplicant() {

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

        String name =
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString();

        String email =
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString();

        String phone =
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString();

        String jobTitle =
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        String date =
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString();

        String status =
                tableModel
                        .getValueAt(
                                selectedRow,
                                6
                        )
                        .toString();

        int applicationId =
                (int) tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        );

        List<Applicant> applicants =
                controller.getApplicantsByEmployer(
                        getEmployerId()
                );

        Applicant selectedApplicant =
                null;

        for (Applicant applicant : applicants) {

            if (
                    applicant.getApplicationId()
                            == applicationId
            ) {

                selectedApplicant =
                        applicant;

                break;
            }
        }

        String skillsText =
                "None";

        if (
                selectedApplicant != null
                        && selectedApplicant.getSkills() != null
                        && !selectedApplicant
                        .getSkills()
                        .isEmpty()
        ) {

            StringBuilder builder =
                    new StringBuilder();

            for (
                    int i = 0;
                    i < selectedApplicant
                            .getSkills()
                            .size();
                    i++
            ) {

                if (i > 0) {

                    builder.append(
                            ", "
                    );
                }

                builder.append(
                        selectedApplicant
                                .getSkills()
                                .get(i)
                                .getName()
                );
            }

            skillsText =
                    builder.toString();
        }

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
                        470,
                        300
                )
        );

        detailsPanel.add(
                createDetailRow(
                        "Applicant Name",
                        name
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Email",
                        email
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Phone",
                        phone
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Applied Job",
                        jobTitle
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Application Date",
                        date
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Status",
                        status
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Skills",
                        skillsText
                )
        );

        JOptionPane.showMessageDialog(
                this,
                detailsPanel,
                "Applicant Details",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private JPanel createDetailRow(
            String label,
            String value
    ) {

        JPanel row =
                new JPanel(
                        new BorderLayout(
                                15,
                                0
                        )
                );

        row.setOpaque(false);

        JLabel labelComponent =
                new JLabel(
                        label
                );

        labelComponent.setFont(
                UITheme.LABEL
        );

        labelComponent.setForeground(
                UITheme.TEXT
        );

        labelComponent.setPreferredSize(
                new Dimension(
                        130,
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
}