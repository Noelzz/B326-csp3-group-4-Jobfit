package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.LoginController;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    private LoginController loginController;

    public LoginFrame() {

        loginController = new LoginController();

        setTitle("JobFit - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 40, 25, 40
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel = new JLabel(
                "JOBFIT",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        JLabel subtitleLabel = new JLabel(
                "A Skills and Job Matching System",
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JPanel titlePanel = new JPanel(
                new GridLayout(2, 1)
        );

        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        // =========================
        // LOGIN FORM
        // =========================

        JPanel formPanel = new JPanel(
                new GridLayout(2, 2, 10, 15)
        );

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // =========================
        // BUTTONS
        // =========================

        loginButton = new JButton("LOGIN");
        registerButton = new JButton("REGISTER");

        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 2, 10, 10)
        );

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // =========================
        // ADD TO FRAME
        // =========================

        mainPanel.add(
                titlePanel,
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
        // BUTTON EVENTS
        // =========================

        loginButton.addActionListener(
                e -> login()
        );

        registerButton.addActionListener(
                e -> openRegister()
        );

        // Press ENTER to login
        passwordField.addActionListener(
                e -> login()
        );
    }

    // =========================
    // LOGIN
    // =========================

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        // Check empty fields
        if (username.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Send login request
        // LoginFrame
        //      ↓
        // LoginController
        //      ↓
        // LoginService
        //      ↓
        // UserRepository
        //      ↓
        // MySQL

        User user =
                loginController.login(
                        username,
                        password
                );

        // =========================
        // LOGIN SUCCESS
        // =========================

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!\nWelcome, "
                            + user.getUsername(),
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            System.out.println(
                    "User ID: " + user.getId()
            );

            System.out.println(
                    "Username: " + user.getUsername()
            );

            System.out.println(
                    "Role: " + user.getRole()
            );

            openDashboard(user);

        } else {

            // =========================
            // LOGIN FAILED
            // =========================

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
            passwordField.requestFocus();
        }
    }

    // =========================
    // OPEN REGISTER
    // =========================

    private void openRegister() {

        RegisterFrame registerFrame =
                new RegisterFrame();

        registerFrame.setVisible(true);
    }

    // =========================
    // OPEN DASHBOARD
    // =========================

    private void openDashboard(User user) {

        String role = user.getRole();

        // =========================
        // ADMIN
        // =========================

        if (role.equals("admin")) {

            AdminDashboard dashboard =
                    new AdminDashboard(user);

            dashboard.setVisible(true);

            dispose();

        }

        // =========================
        // JOB SEEKER
        // =========================

        else if (role.equals("job_seeker")) {

            JobSeekerDashboard dashboard =
                    new JobSeekerDashboard(user);

            dashboard.setVisible(true);

            dispose();

        }

        // =========================
        // EMPLOYER
        // =========================

        else if (role.equals("employer")) {

            EmployerDashboard dashboard =
                    new EmployerDashboard(user);

            dashboard.setVisible(true);

            dispose();

        }

        // =========================
        // UNKNOWN ROLE
        // =========================

        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unknown user role: " + role,
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}