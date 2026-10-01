package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.config.RoundedButton;
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

public class UpdateApplicationStatusFrame extends JFrame {

    private final User user;
    private final ApplicationController controller;

    private JTable applicantsTable;
    private DefaultTableModel tableModel;

    private JComboBox<String> statusComboBox;

    public UpdateApplicationStatusFrame(User user) {

        this.user = user;
        this.controller = new ApplicationController();

        setTitle("JobFit - Update Application Status");
        setSize(1050, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        loadApplicants();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("UPDATE APPLICATION STATUS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        tableModel = new DefaultTableModel(
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        applicantsTable = new JTable(tableModel);
        applicantsTable.setRowHeight(30);
        applicantsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(applicantsTable);

        statusComboBox = new JComboBox<>(new String[]{
                "Pending", "Reviewed", "Accepted", "Rejected"
        });

        JButton updateButton = new RoundedButton("UPDATE STATUS");
        JButton refreshButton = new RoundedButton("REFRESH");
        JButton closeButton = new RoundedButton("CLOSE");

        Dimension btnSize = new Dimension(130, 36);
        updateButton.setPreferredSize(btnSize);
        refreshButton.setPreferredSize(new Dimension(110, 36));
        closeButton.setPreferredSize(new Dimension(110, 36));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.add(new JLabel("New Status:"));
        controlPanel.add(statusComboBox);
        controlPanel.add(updateButton);
        controlPanel.add(refreshButton);
        controlPanel.add(closeButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);

        updateButton.addActionListener(e -> updateStatus());
        refreshButton.addActionListener(e -> loadApplicants());
        closeButton.addActionListener(e -> dispose());
    }

    private void loadApplicants() {

        tableModel.setRowCount(0);

        int employerId = getEmployerId();

        if (employerId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Employer profile was not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        List<Applicant> applicants = controller.getApplicantsByEmployer(employerId);

        for (Applicant applicant : applicants) {
            tableModel.addRow(new Object[]{
                    applicant.getApplicationId(),
                    applicant.getJobTitle(),
                    applicant.getFullName(),
                    applicant.getEmail(),
                    applicant.getApplicationDate(),
                    applicant.getStatus()
            });
        }
    }

    private void updateStatus() {

        int selectedRow = applicantsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an applicant first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int applicationId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentStatus = tableModel.getValueAt(selectedRow, 5).toString();
        String newStatus = statusComboBox.getSelectedItem().toString();

        if (currentStatus.equals(newStatus)) {
            JOptionPane.showMessageDialog(
                    this,
                    "The application already has this status.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Change application status to " + newStatus + "?",
                "Confirm Status Update",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean success = controller.updateApplicationStatus(applicationId, newStatus);

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

        String sql = "SELECT id FROM Employer WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, user.getId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}