package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.config.SalaryParser;
import com.joysis.tvi.JobFit.controller.JobCategoryController;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobCategory;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PostJobFrame extends BaseFormFrame {

    private final User user;
    private final JobController jobController;
    private final JobCategoryController categoryController;

    private JTextField titleField;
    private JComboBox<JobCategory> categoryComboBox;
    private JTextArea descriptionArea;
    private JTextField locationField;
    private JTextField salaryField;

    public PostJobFrame(User user) {

        super(
                "JobFit - Post Job",
                "Post a Job",
                "Create a new job listing for your company.",
                850,
                820
        );

        this.user = user;
        this.jobController = new JobController();
        this.categoryController = new JobCategoryController();

        createContent();
        loadCategories();
    }

    private void createContent() {

        formPanel.setLayout(
                new BoxLayout(
                        formPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel sectionTitle =
                new JLabel("Job Information");

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
                        "Fill in the details below to create a new job posting."
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

        formPanel.add(sectionTitle);
        formPanel.add(Box.createVerticalStrut(6));
        formPanel.add(sectionInfo);
        formPanel.add(Box.createVerticalStrut(24));

        // JOB TITLE

        formPanel.add(
                createFieldLabel("Job Title")
        );

        formPanel.add(
                Box.createVerticalStrut(8)
        );

        titleField =
                new JTextField();

        styleField(titleField);

        formPanel.add(titleField);

        formPanel.add(
                Box.createVerticalStrut(18)
        );

        // CATEGORY

        formPanel.add(
                createFieldLabel("Job Category")
        );

        formPanel.add(
                Box.createVerticalStrut(8)
        );

        categoryComboBox =
                new JComboBox<>();

        styleComboBox(
                categoryComboBox
        );

        JButton addCategoryButton =
                new RoundedButton(
                        "New Category",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        addCategoryButton.setPreferredSize(
                new Dimension(
                        145,
                        44
                )
        );

        addCategoryButton.setMinimumSize(
                new Dimension(
                        145,
                        44
                )
        );

        JPanel categoryPanel =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        categoryPanel.setOpaque(false);

        categoryPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        categoryPanel.setPreferredSize(
                new Dimension(
                        0,
                        44
                )
        );

        categoryPanel.setMinimumSize(
                new Dimension(
                        0,
                        44
                )
        );

        categoryPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        categoryPanel.add(
                categoryComboBox,
                BorderLayout.CENTER
        );

        categoryPanel.add(
                addCategoryButton,
                BorderLayout.EAST
        );

        formPanel.add(categoryPanel);

        formPanel.add(
                Box.createVerticalStrut(18)
        );

        // DESCRIPTION

        formPanel.add(
                createFieldLabel(
                        "Job Description"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(8)
        );

        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(UITheme.BODY);
        descriptionArea.setForeground(UITheme.TEXT);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        10,
                        12));
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        descriptionScrollPane.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_BLUE));

        descriptionScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionScrollPane.setPreferredSize(new Dimension(0, 100));
        descriptionScrollPane.setMinimumSize(new Dimension(0, 90));

        descriptionScrollPane.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        90
                )
        );

        formPanel.add(
                descriptionScrollPane
        );

        formPanel.add(
                Box.createVerticalStrut(18)
        );

        // LOCATION

        formPanel.add(
                createFieldLabel("Location")
        );

        formPanel.add(
                Box.createVerticalStrut(8)
        );

        locationField =
                new JTextField();

        styleField(
                locationField
        );

        formPanel.add(
                locationField
        );

        formPanel.add(
                Box.createVerticalStrut(18)
        );

        // SALARY

        formPanel.add(
                createFieldLabel("Salary")
        );

        formPanel.add(
                Box.createVerticalStrut(8)
        );

        salaryField =
                new JTextField();

        styleField(
                salaryField
        );

        formPanel.add(
                salaryField
        );

        formPanel.add(
                Box.createVerticalStrut(6)
        );

        JLabel salaryHint =
                new JLabel(
                        "Examples: 18000, 18k, 18k-20k, PHP 25000"
                );

        salaryHint.setFont(
                UITheme.SMALL
        );

        salaryHint.setForeground(
                UITheme.TEXT_SECONDARY
        );

        salaryHint.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        formPanel.add(
                salaryHint
        );

        formPanel.add(
                Box.createVerticalStrut(
                        UITheme.GAP_LG
                )
        );

// ==========================================
// BUTTONS
// ==========================================

        JButton postButton = new RoundedButton(
                        "Post Job",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER);

        JButton backButton = new RoundedButton("Back", UITheme.DANGER, new Color(190, 55, 65));
        postButton.setPreferredSize(new Dimension(140, 42));
        backButton.setPreferredSize(new Dimension(110, 42));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(postButton);
        buttonPanel.add(backButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

// ==========================================
// ACTIONS
// ==========================================

        addCategoryButton.addActionListener(e -> addNewCategory());
        postButton.addActionListener(e -> postJob());
        backButton.addActionListener(e -> dispose());
    }

    private JLabel createFieldLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(UITheme.LABEL);

        label.setForeground(UITheme.TEXT);

        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    private void styleField(
            JTextField field
    ) {

        field.setFont(
                UITheme.BODY
        );

        field.setForeground(
                UITheme.TEXT
        );

        field.setBackground(
                Color.WHITE
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        field.setPreferredSize(
                new Dimension(
                        0,
                        44
                )
        );

        field.setMinimumSize(
                new Dimension(
                        0,
                        44
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER_BLUE
                        ),
                        new EmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );
    }

    private void styleComboBox(
            JComboBox<JobCategory> comboBox
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
                        0,
                        44
                )
        );

        comboBox.setMinimumSize(
                new Dimension(
                        0,
                        44
                )
        );

        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER_BLUE
                )
        );
    }

    private void loadCategories() {

        List<JobCategory> categories =
                categoryController.getAllCategories();

        categoryComboBox.removeAllItems();

        for (JobCategory category : categories) {
            categoryComboBox.addItem(category);
        }

        if (categories.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No job categories found. Please add one using New Category.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void addNewCategory() {

        JTextField nameField =
                new JTextField();

        JTextArea descArea =
                new JTextArea(
                        3,
                        20
                );

        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);

        Object[] message = {
                "Category Name:",
                nameField,
                "Description:",
                new JScrollPane(descArea)
        };

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Add New Job Category",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        String name =
                nameField.getText().trim();

        String description =
                descArea.getText().trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category name cannot be empty.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean success =
                categoryController.addCategory(
                        name,
                        description
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category \"" + name + "\" added.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadCategories();

            for (
                    int i = 0;
                    i < categoryComboBox.getItemCount();
                    i++
            ) {

                if (
                        categoryComboBox
                                .getItemAt(i)
                                .getName()
                                .equals(name)
                ) {

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

        if (
                title.isEmpty()
                        || description.isEmpty()
                        || location.isEmpty()
                        || salaryText.isEmpty()
        ) {

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

        double salary =
                SalaryParser.parse(
                        salaryText
                );

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
                jobController.addJob(job);

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

            if (
                    categoryComboBox.getItemCount() > 0
            ) {

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

        String sql =
                "SELECT id FROM Employer WHERE user_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    user.getId()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

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