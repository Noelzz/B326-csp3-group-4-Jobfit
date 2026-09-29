package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.RegisterController;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;

    private JLabel nameLabel;

    private JButton registerButton;
    private JButton backButton;

    private RegisterController registerController;

    public RegisterFrame() {

        registerController = new RegisterController();

        setTitle("JobFit - Register");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
    }

    private void createGUI() {

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        JLabel titleLabel = new JLabel(
                "JOBFIT REGISTRATION",
                SwingConstants.CENTER
        );

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");

        nameLabel = new JLabel("Full Name:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        roleComboBox = new JComboBox<>(
                new String[]{
                        "Job Seeker",
                        "Employer"
                }
        );

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JLabel phoneLabel = new JLabel("Phone:");

        registerButton = new JButton("REGISTER");
        backButton = new JButton("BACK TO LOGIN");

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(roleLabel);
        panel.add(roleComboBox);

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(phoneLabel);
        panel.add(phoneField);

        panel.add(registerButton);
        panel.add(backButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        roleComboBox.addActionListener(e -> updateNameLabel());

        registerButton.addActionListener(e -> register());

        backButton.addActionListener(e -> {
            dispose();
        });
    }

    private void updateNameLabel() {

        if (roleComboBox.getSelectedItem()
                .equals("Employer")) {

            nameLabel.setText("Company Name:");

        } else {

            nameLabel.setText("Full Name:");
        }
    }

    private void register() {

        String username = usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        String role =
                (String) roleComboBox.getSelectedItem();

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        if (username.isEmpty()
                || password.isEmpty()
                || name.isEmpty()
                || email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean success;

        if (role.equals("Job Seeker")) {

            success = registerController.registerJobSeeker(
                    username,
                    password,
                    name,
                    email,
                    phone
            );

        } else {

            success = registerController.registerEmployer(
                    username,
                    password,
                    name,
                    email,
                    phone
            );
        }

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful!"
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed.\n"
                            + "Username may already exist.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}