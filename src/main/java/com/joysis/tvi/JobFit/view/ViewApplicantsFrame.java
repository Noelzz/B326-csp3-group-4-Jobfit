package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.ApplicationController;
import com.joysis.tvi.JobFit.model.Applicant;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ViewApplicantsFrame extends JFrame {

    private final User user;
    private final ApplicationController controller;

    private JTable applicantsTable;
    private DefaultTableModel tableModel;

    public ViewApplicantsFrame(User user) {

        this.user = user;
        this.controller = new ApplicationController();

        setTitle("JobFit - View Applicants");
        setSize(1100, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        loadApplicants();
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
                        "VIEW APPLICANTS",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
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
                            int column) {

                        return false;
                    }
                };

        applicantsTable =
                new JTable(tableModel);

        applicantsTable.setRowHeight(30);

        applicantsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(applicantsTable);

        JButton refreshButton =
                new JButton("REFRESH");

        JButton viewButton =
                new JButton("VIEW APPLICANT");

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

        if (applicants.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No applicants found.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private int getEmployerId() {

        String sql =
                "SELECT id FROM Employer WHERE user_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

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
                tableModel.getValueAt(
                        selectedRow,
                        2
                ).toString();

        String email =
                tableModel.getValueAt(
                        selectedRow,
                        3
                ).toString();

        String phone =
                tableModel.getValueAt(
                        selectedRow,
                        4
                ).toString();

        String jobTitle =
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString();

        String date =
                tableModel.getValueAt(
                        selectedRow,
                        5
                ).toString();

        String status =
                tableModel.getValueAt(
                        selectedRow,
                        6
                ).toString();

        String message =
                "Applicant Information\n\n"
                        + "Name: " + name
                        + "\n"
                        + "Email: " + email
                        + "\n"
                        + "Phone: " + phone
                        + "\n\n"
                        + "Applied Job: " + jobTitle
                        + "\n"
                        + "Application Date: " + date
                        + "\n"
                        + "Status: " + status;

        JOptionPane.showMessageDialog(
                this,
                message,
                "Applicant Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}