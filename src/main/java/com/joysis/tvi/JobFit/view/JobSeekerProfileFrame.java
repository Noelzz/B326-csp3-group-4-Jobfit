package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.JobSeekerController;
import com.joysis.tvi.JobFit.model.JobSeeker;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;

public class JobSeekerProfileFrame extends JFrame {

    private User user;
    private JobSeekerController controller;

    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;

    private JButton updateButton;
    private JButton backButton;

    public JobSeekerProfileFrame(User user) {

        this.user = user;
        this.controller = new JobSeekerController();

        setTitle("JobFit - My Profile");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadProfile();
    }

    private void createGUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 40, 25, 40
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "MY PROFILE",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        // =========================
        // FORM
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(4, 2, 10, 15)
                );

        JLabel fullNameLabel =
                new JLabel("Full Name:");

        JLabel emailLabel =
                new JLabel("Email:");

        JLabel phoneLabel =
                new JLabel("Phone:");

        JLabel usernameLabel =
                new JLabel("Username:");

        fullNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        JTextField usernameDisplay =
                new JTextField(user.getUsername());

        usernameDisplay.setEditable(false);

        formPanel.add(usernameLabel);
        formPanel.add(usernameDisplay);

        formPanel.add(fullNameLabel);
        formPanel.add(fullNameField);

        formPanel.add(emailLabel);
        formPanel.add(emailField);

        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        // =========================
        // BUTTONS
        // =========================

        updateButton =
                new JButton("UPDATE PROFILE");

        backButton =
                new JButton("BACK");

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(1, 2, 10, 10)
                );

        buttonPanel.add(updateButton);
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

        // =========================
        // EVENTS
        // =========================

        updateButton.addActionListener(
                e -> updateProfile()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // LOAD PROFILE
    // =========================

    private void loadProfile() {

        JobSeeker jobSeeker =
                controller.getProfile(user.getId());

        if (jobSeeker != null) {

            fullNameField.setText(
                    jobSeeker.getFullName()
            );

            emailField.setText(
                    jobSeeker.getEmail()
            );

            phoneField.setText(
                    jobSeeker.getPhone()
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load your profile.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // UPDATE PROFILE
    // =========================

    private void updateProfile() {

        String fullName =
                fullNameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        if (fullName.isEmpty() ||
                email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Full Name and Email are required.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JobSeeker jobSeeker =
                new JobSeeker(
                        0,
                        user.getId(),
                        fullName,
                        email,
                        phone
                );

        boolean success =
                controller.updateProfile(
                        jobSeeker
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Profile updated successfully!",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update profile.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}