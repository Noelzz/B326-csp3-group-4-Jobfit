package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.config.RoundedButton;
import com.joysis.tvi.JobFit.controller.ApplicationController;
import com.joysis.tvi.JobFit.model.Application;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TrackApplicationsFrame extends JFrame {

    private final User user;
    private final ApplicationController controller;

    private JTable applicationsTable;
    private DefaultTableModel tableModel;

    public TrackApplicationsFrame(User user) {

        this.user = user;
        this.controller = new ApplicationController();

        setTitle("JobFit - Track Applications");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        loadApplications();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("TRACK APPLICATIONS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        tableModel = new DefaultTableModel(
                new Object[]{
                        "Application ID",
                        "Job Title",
                        "Application Date",
                        "Status"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        applicationsTable = new JTable(tableModel);
        applicationsTable.setRowHeight(30);
        applicationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(applicationsTable);

        JButton refreshButton = new RoundedButton("REFRESH");
        JButton closeButton = new RoundedButton("CLOSE");

        Dimension btnSize = new Dimension(110, 36);
        refreshButton.setPreferredSize(btnSize);
        closeButton.setPreferredSize(btnSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        refreshButton.addActionListener(e -> loadApplications());
        closeButton.addActionListener(e -> dispose());
    }

    private void loadApplications() {

        tableModel.setRowCount(0);

        int jobSeekerId = getJobSeekerId();

        if (jobSeekerId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Job Seeker profile was not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        List<Application> applications = controller.getApplicationsByJobSeeker(jobSeekerId);

        for (Application application : applications) {
            String jobTitle = getJobTitle(application.getJobId());

            tableModel.addRow(new Object[]{
                    application.getId(),
                    jobTitle,
                    application.getApplicationDate(),
                    application.getStatus()
            });
        }
    }

    private int getJobSeekerId() {

        String sql = "SELECT id FROM Job_Seeker WHERE user_id = ?";

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

    private String getJobTitle(int jobId) {

        String sql = "SELECT title FROM Jobs WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("title");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown Job";
    }
}