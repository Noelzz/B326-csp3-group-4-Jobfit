package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.EmployerController;
import com.joysis.tvi.JobFit.model.Employer;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;

public class EmployerProfileFrame extends JFrame {

    private final User user;
    private final EmployerController controller;

    private JTextField companyNameField;
    private JTextField emailField;
    private JTextField phoneField;

    public EmployerProfileFrame(User user) {

        this.user = user;
        this.controller = new EmployerController();

        setTitle("JobFit - Company Profile");
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

        JLabel titleLabel =
                new JLabel(
                        "COMPANY PROFILE",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JPanel formPanel =
                new JPanel(new GridLayout(4, 2, 10, 15));

        JLabel usernameLabel =
                new JLabel("Username:");

        JLabel companyLabel =
                new JLabel("Company Name:");

        JLabel emailLabel =
                new JLabel("Email:");

        JLabel phoneLabel =
                new JLabel("Phone:");

        JTextField usernameField =
                new JTextField(user.getUsername());

        usernameField.setEditable(false);

        companyNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(companyLabel);
        formPanel.add(companyNameField);

        formPanel.add(emailLabel);
        formPanel.add(emailField);

        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        JButton updateButton =
                new JButton("UPDATE PROFILE");

        JButton backButton =
                new JButton("BACK");

        JPanel buttonPanel =
                new JPanel(new GridLayout(1, 2, 10, 10));

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

        updateButton.addActionListener(
                e -> updateProfile()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    private void loadProfile() {

        Employer employer =
                controller.getProfile(user.getId());

        if (employer != null) {

            companyNameField.setText(
                    employer.getCompanyName()
            );

            emailField.setText(
                    employer.getEmail()
            );

            phoneField.setText(
                    employer.getPhone()
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load company profile.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateProfile() {

        String companyName =
                companyNameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        if (companyName.isEmpty() ||
                email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Company Name and Email are required.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Employer employer =
                new Employer(
                        0,
                        user.getId(),
                        companyName,
                        email,
                        phone
                );

        boolean success =
                controller.updateProfile(employer);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Company profile updated successfully!",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update company profile.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}