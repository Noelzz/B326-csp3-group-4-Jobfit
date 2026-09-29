package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobCategory;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PostJobFrame extends JFrame {

    private final User user;
    private final JobController controller;

    private JTextField titleField;
    private JComboBox<JobCategory> categoryComboBox;
    private JTextArea descriptionArea;
    private JTextField locationField;
    private JTextField salaryField;

    public PostJobFrame(User user) {

        this.user = user;
        this.controller = new JobController();

        setTitle("JobFit - Post Job");
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadCategories();
    }

    private void createGUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 40, 25, 40
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "POST A JOB",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(5, 2, 10, 15)
                );

        JLabel jobTitleLabel =
                new JLabel("Job Title:");

        JLabel categoryLabel =
                new JLabel("Category:");

        JLabel descriptionLabel =
                new JLabel("Description:");

        JLabel locationLabel =
                new JLabel("Location:");

        JLabel salaryLabel =
                new JLabel("Salary:");

        titleField = new JTextField();

        categoryComboBox =
                new JComboBox<>();

        descriptionArea =
                new JTextArea();

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane descriptionScrollPane =
                new JScrollPane(descriptionArea);

        locationField =
                new JTextField();

        salaryField =
                new JTextField();

        formPanel.add(jobTitleLabel);
        formPanel.add(titleField);

        formPanel.add(categoryLabel);
        formPanel.add(categoryComboBox);

        formPanel.add(descriptionLabel);
        formPanel.add(descriptionScrollPane);

        formPanel.add(locationLabel);
        formPanel.add(locationField);

        formPanel.add(salaryLabel);
        formPanel.add(salaryField);

        JButton postButton =
                new JButton("POST JOB");

        JButton backButton =
                new JButton("BACK");

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(1, 2, 10, 10)
                );

        buttonPanel.add(postButton);
        buttonPanel.add(backButton);

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        postButton.addActionListener(
                e -> postJob()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    private void loadCategories() {

        List<JobCategory> categories =
                controller.getAllCategories();

        categoryComboBox.removeAllItems();

        for (JobCategory category : categories) {

            categoryComboBox.addItem(category);
        }

        if (categories.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No job categories found in the database.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void postJob() {

        String title =
                titleField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        String location =
                locationField.getText().trim();

        String salaryText =
                salaryField.getText().trim();

        JobCategory selectedCategory =
                (JobCategory) categoryComboBox.getSelectedItem();

        if (title.isEmpty() ||
                description.isEmpty() ||
                location.isEmpty() ||
                salaryText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (selectedCategory == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job category.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double salary;

        try {

            salary =
                    Double.parseDouble(salaryText);

            if (salary < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Salary cannot be negative.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid salary.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        /*
         * IMPORTANT:
         *
         * user.getId() is the Users.id.
         * Jobs.employer_id needs Employer.id.
         *
         * We will handle this properly in the next step
         * if your Employer table ID is not the same as Users.id.
         */

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

        Job job =
                new Job(
                        0,
                        employerId,
                        selectedCategory.getId(),
                        title,
                        description,
                        location,
                        salary
                );

        boolean success =
                controller.addJob(job);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job posted successfully!",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            titleField.setText("");
            descriptionArea.setText("");
            locationField.setText("");
            salaryField.setText("");

            if (categoryComboBox.getItemCount() > 0) {
                categoryComboBox.setSelectedIndex(0);
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to post job.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private int getEmployerId() {

        /*
         * Temporary lookup:
         * Find Employer.id using Users.id.
         */

        try {

            String sql =
                    "SELECT id FROM Employer WHERE user_id = ?";

            java.sql.Connection connection =
                    com.joysis.tvi.JobFit.config.DatabaseConnection
                            .getConnection();

            java.sql.PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    user.getId()
            );

            java.sql.ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                int employerId =
                        resultSet.getInt("id");

                resultSet.close();
                statement.close();
                connection.close();

                return employerId;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}