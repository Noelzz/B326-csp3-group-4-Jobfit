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

    // JobFit colors
    private static final Color BACKGROUND_COLOR =
            new Color(245, 247, 250);

    private static final Color TITLE_COLOR =
            new Color(45, 95, 170);

    private static final Color TEXT_COLOR =
            new Color(35, 40, 48);

    private static final Color BUTTON_COLOR =
            new Color(45, 95, 170);

    private static final Color BUTTON_HOVER =
            new Color(35, 78, 145);

    public RegisterFrame() {

        registerController = new RegisterController();

        setTitle("JobFit - Register");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
    }

    private void createGUI() {

        // Background
        getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel panel = new JPanel(
                new GridLayout(8, 2, 10, 10)
        );

        panel.setBackground(BACKGROUND_COLOR);

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        // Title
        JLabel titleLabel = new JLabel(
                "JOBFIT REGISTRATION",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );

        titleLabel.setForeground(TITLE_COLOR);

        JLabel usernameLabel = createLabel("Username:");
        JLabel passwordLabel = createLabel("Password:");
        JLabel roleLabel = createLabel("Role:");

        nameLabel = createLabel("Full Name:");

        usernameField = createTextField();

        passwordField = new JPasswordField();
        styleTextField(passwordField);

        roleComboBox = new JComboBox<>(
                new String[]{
                        "Job Seeker",
                        "Employer"
                }
        );

        roleComboBox.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        roleComboBox.setBackground(Color.WHITE);

        nameField = createTextField();
        emailField = createTextField();
        phoneField = createTextField();

        JLabel emailLabel = createLabel("Email:");
        JLabel phoneLabel = createLabel("Phone:");

        registerButton = new JButton("REGISTER");
        backButton = new JButton("BACK TO LOGIN");

        styleButton(registerButton);
        styleButton(backButton);

        // Original layout — unchanged
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

        roleComboBox.addActionListener(
                e -> updateNameLabel()
        );

        registerButton.addActionListener(
                e -> register()
        );

        backButton.addActionListener(
                e -> openLogin()
        );
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        label.setForeground(TEXT_COLOR);

        return label;
    }

    private JTextField createTextField() {

        JTextField field = new JTextField();

        styleTextField(field);

        return field;
    }

    private void styleTextField(JTextField field) {

        field.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_COLOR);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(210, 215, 223)
                        ),
                        BorderFactory.createEmptyBorder(
                                4, 6, 4, 6
                        )
                )
        );
    }

    private void styleButton(JButton button) {

        button.setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(BUTTON_HOVER);
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(BUTTON_COLOR);
                    }
                }
        );
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

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

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

        try {

            if (role.equals("Job Seeker")) {

                success =
                        registerController.registerJobSeeker(
                                username,
                                password,
                                name,
                                email,
                                phone
                        );

            } else {

                success =
                        registerController.registerEmployer(
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
                        "Registration successful!\n"
                                + "You can now login to JobFit.",
                        "Registration Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                openLogin();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration failed.\n"
                                + "Username may already exist.",
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "An error occurred during registration.\n\n"
                            + ex.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openLogin() {

        dispose();

        LoginFrame loginFrame =
                new LoginFrame();

        loginFrame.setVisible(true);
    }
}