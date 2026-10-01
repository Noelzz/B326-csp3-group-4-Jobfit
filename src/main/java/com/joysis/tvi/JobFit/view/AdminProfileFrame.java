package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.RoundedButton;
import com.joysis.tvi.JobFit.controller.AdminController;
import com.joysis.tvi.JobFit.controller.UserController;
import com.joysis.tvi.JobFit.model.Admin;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;

public class AdminProfileFrame extends JFrame {

    private final User user;
    private final AdminController adminController;
    private final UserController userController;

    private JTextField emailField;
    private JTextField phoneField;

    private String originalEmail;
    private String originalPhone;

    public AdminProfileFrame(User user) {

        this.user = user;
        this.adminController = new AdminController();
        this.userController = new UserController();

        setTitle("JobFit - Admin Profile");
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadProfile();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JLabel titleLabel = new JLabel("ADMIN PROFILE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel phoneLabel = new JLabel("Phone:");

        JTextField usernameField = new JTextField(user.getUsername());
        usernameField.setEditable(false);

        emailField = new JTextField();
        phoneField = new JTextField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        JButton updateButton = new RoundedButton("UPDATE");
        JButton changePasswordButton = new RoundedButton("PASSWORD");
        JButton backButton = new RoundedButton("BACK");

        Dimension btnSize = new Dimension(120, 36);
        updateButton.setPreferredSize(btnSize);
        changePasswordButton.setPreferredSize(btnSize);
        backButton.setPreferredSize(btnSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(updateButton);
        buttonPanel.add(changePasswordButton);
        buttonPanel.add(backButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        updateButton.addActionListener(e -> updateProfile());
        changePasswordButton.addActionListener(e -> openChangePasswordDialog());
        backButton.addActionListener(e -> dispose());
    }

    private void loadProfile() {

        Admin admin = adminController.getProfile(user.getId());

        if (admin != null) {
            originalEmail = admin.getEmail();
            originalPhone = admin.getPhone();

            emailField.setText(originalEmail);
            phoneField.setText(originalPhone);
        } else {
            originalEmail = "";
            originalPhone = "";

            emailField.setText("");
            phoneField.setText("");
        }
    }

    private void updateProfile() {

        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (email.equals(originalEmail == null ? "" : originalEmail)
                && phone.equals(originalPhone == null ? "" : originalPhone)) {

            JOptionPane.showMessageDialog(
                    this,
                    "No changes to save.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Email is required.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String error = adminController.saveProfile(user.getId(), email, phone);

        if (error == null) {
            originalEmail = email;
            originalPhone = phone;

            JOptionPane.showMessageDialog(
                    this,
                    "Admin profile saved successfully!",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    error,
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openChangePasswordDialog() {

        JPasswordField currentField = new JPasswordField();
        JPasswordField newField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();

        Object[] message = {
                "Current Password:", currentField,
                "New Password:", newField,
                "Confirm New Password:", confirmField
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                message,
                "Change Password",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;

        String current = new String(currentField.getPassword());
        String newPass = new String(newField.getPassword());
        String confirm = new String(confirmField.getPassword());

        String error = userController.changePassword(
                user.getId(), current, newPass, confirm);

        if (error == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Password changed successfully!",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    error,
                    "Change Password Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}