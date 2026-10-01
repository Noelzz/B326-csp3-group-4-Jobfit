package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.config.RoundedButton;
import com.joysis.tvi.JobFit.config.SalaryParser;
import com.joysis.tvi.JobFit.controller.JobCategoryController;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobCategory;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PostJobFrame extends JFrame {

    private final User user;
    private final JobController jobController;
    private final JobCategoryController categoryController;

    private JTextField titleField;
    private JComboBox<JobCategory> categoryComboBox;
    private JTextArea descriptionArea;
    private JTextField locationField;
    private JTextField salaryField;

    public PostJobFrame(User user) {

        this.user = user;
        this.jobController = new JobController();
        this.categoryController = new JobCategoryController();

        setTitle("JobFit - Post Job");
        setSize(600, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadCategories();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JLabel titleLabel = new JLabel("POST A JOB", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));

        JLabel jobTitleLabel = new JLabel("Job Title:");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel descriptionLabel = new JLabel("Description:");
        JLabel locationLabel = new JLabel("Location:");
        JLabel salaryLabel = new JLabel("Salary:");

        titleField = new JTextField();

        // Category dropdown with "add new" button
        categoryComboBox = new JComboBox<>();
        JButton addCategoryButton = new RoundedButton("+ NEW");
        addCategoryButton.setPreferredSize(new Dimension(90, 30));
        addCategoryButton.setFont(new Font("Segoe UI", Font.BOLD, 11));

        JPanel categoryPanel = new JPanel(new BorderLayout(5, 0));
        categoryPanel.add(categoryComboBox, BorderLayout.CENTER);
        categoryPanel.add(addCategoryButton, BorderLayout.EAST);

        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        locationField = new JTextField();

        // Salary field with hint
        salaryField = new JTextField();
        JLabel salaryHint = new JLabel("e.g. 18000, 18k, 18k-20k");
        salaryHint.setFont(new Font("Arial", Font.ITALIC, 11));
        salaryHint.setForeground(new Color(120, 120, 120));

        JPanel salaryPanel = new JPanel(new BorderLayout(0, 2));
        salaryPanel.add(salaryField, BorderLayout.CENTER);
        salaryPanel.add(salaryHint, BorderLayout.SOUTH);

        formPanel.add(jobTitleLabel);
        formPanel.add(titleField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryPanel);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionScrollPane);
        formPanel.add(locationLabel);
        formPanel.add(locationField);
        formPanel.add(salaryLabel);
        formPanel.add(salaryPanel);

        JButton postButton = new RoundedButton("POST JOB");
        JButton backButton = new RoundedButton("BACK");

        Dimension btnSize = new Dimension(140, 40);
        postButton.setPreferredSize(btnSize);
        backButton.setPreferredSize(btnSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(postButton);
        buttonPanel.add(backButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        addCategoryButton.addActionListener(e -> addNewCategory());
        postButton.addActionListener(e -> postJob());
        backButton.addActionListener(e -> dispose());
    }

    private void loadCategories() {

        List<JobCategory> categories = categoryController.getAllCategories();

        categoryComboBox.removeAllItems();

        for (JobCategory category : categories) {
            categoryComboBox.addItem(category);
        }

        if (categories.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No job categories found. Please add one using '+ NEW'.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void addNewCategory() {

        JTextField nameField = new JTextField();
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);

        Object[] message = {
                "Category Name:", nameField,
                "Description:", new JScrollPane(descArea)
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                message,
                "Add New Job Category",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;

        String name = nameField.getText().trim();
        String description = descArea.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Category name cannot be empty.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean success = categoryController.addCategory(name, description);

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Category \"" + name + "\" added.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            loadCategories();

            for (int i = 0; i < categoryComboBox.getItemCount(); i++) {
                if (categoryComboBox.getItemAt(i).getName().equals(name)) {
                    categoryComboBox.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add category.\nIt may already exist.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void postJob() {

        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        String location = locationField.getText().trim();
        String salaryText = salaryField.getText().trim();

        JobCategory selectedCategory = (JobCategory) categoryComboBox.getSelectedItem();

        if (title.isEmpty() || description.isEmpty()
                || location.isEmpty() || salaryText.isEmpty()) {

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

        double salary = SalaryParser.parse(salaryText);

        if (salary < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid salary format.\n"
                            + "Examples: 18000, 18k, 18k-20k, PHP 25000",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

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

        Job job = new Job(
                0,
                employerId,
                selectedCategory.getId(),
                title,
                description,
                location,
                salary
        );

        boolean success = jobController.addJob(job);

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

        String sql = "SELECT id FROM Employer WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, user.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}