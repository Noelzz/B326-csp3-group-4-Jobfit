package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.RegisterController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    private final RegisterController registerController;

    public RegisterFrame() {

        this.registerController = new RegisterController();

        setTitle("JobFit - Create Account");
        setSize(760, 760);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {

        // ==========================================
        // MAIN PANEL
        // ==========================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        28,
                        34,
                        28,
                        34
                )
        );

        // ==========================================
        // HEADER
        // ==========================================

        JPanel headerPanel =
                new JPanel();

        headerPanel.setOpaque(false);

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel logoLabel =
                new JLabel(
                        "JOBFIT"
                );

        logoLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        logoLabel.setForeground(
                UITheme.BLUE
        );

        logoLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel titleLabel =
                new JLabel(
                        "Create an Account"
                );

        titleLabel.setFont(
                UITheme.PAGE_TITLE
        );

        titleLabel.setForeground(
                UITheme.TEXT
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Join JobFit and start building better opportunities."
                );

        subtitleLabel.setFont(
                UITheme.BODY
        );

        subtitleLabel.setForeground(
                UITheme.TEXT_SECONDARY
        );

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        headerPanel.add(
                logoLabel
        );

        headerPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        headerPanel.add(
                titleLabel
        );

        headerPanel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        headerPanel.add(
                subtitleLabel
        );

        // ==========================================
        // REGISTER CARD
        // ==========================================

        RoundedPanel card =
                new RoundedPanel(
                        20
                );

        card.setBackground(
                UITheme.SURFACE
        );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        26,
                        34,
                        26,
                        34
                )
        );

        // ==========================================
        // FORM
        // ==========================================

        JPanel formPanel =
                new JPanel();

        formPanel.setOpaque(false);

        formPanel.setLayout(
                new BoxLayout(
                        formPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // Username
        formPanel.add(
                createLabel(
                        "Username"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        usernameField =
                new JTextField();

        styleTextField(
                usernameField
        );

        formPanel.add(
                usernameField
        );

        formPanel.add(
                Box.createVerticalStrut(
                        14
                )
        );

        // Password
        formPanel.add(
                createLabel(
                        "Password"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        passwordField =
                new JPasswordField();

        styleTextField(
                passwordField
        );

        formPanel.add(
                passwordField
        );

        formPanel.add(
                Box.createVerticalStrut(
                        14
                )
        );

        // Role
        formPanel.add(
                createLabel(
                        "Role"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "Job Seeker",
                                "Employer"
                        }
                );

        styleComboBox(
                roleComboBox
        );

        formPanel.add(
                roleComboBox
        );

        formPanel.add(
                Box.createVerticalStrut(
                        14
                )
        );

        // Name
        nameLabel =
                createLabel(
                        "Full Name"
                );

        formPanel.add(
                nameLabel
        );

        formPanel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        nameField =
                new JTextField();

        styleTextField(
                nameField
        );

        formPanel.add(
                nameField
        );

        formPanel.add(
                Box.createVerticalStrut(
                        14
                )
        );

        // Email
        formPanel.add(
                createLabel(
                        "Email Address"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        emailField =
                new JTextField();

        styleTextField(
                emailField
        );

        formPanel.add(
                emailField
        );

        formPanel.add(
                Box.createVerticalStrut(
                        14
                )
        );

        // Phone
        formPanel.add(
                createLabel(
                        "Phone Number"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        phoneField =
                new JTextField();

        styleTextField(
                phoneField
        );

        formPanel.add(
                phoneField
        );

        formPanel.add(
                Box.createVerticalStrut(
                        22
                )
        );

        // ==========================================
        // BUTTONS
        // ==========================================

        registerButton =
                new RoundedButton(
                        "Create Account",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        registerButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        registerButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        registerButton.setPreferredSize(
                new Dimension(
                        0,
                        44
                )
        );

        formPanel.add(
                registerButton
        );

        formPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        backButton =
                new JButton(
                        "Back to Login"
                );

        styleBackButton(
                backButton
        );

        backButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        backButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        backButton.setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        formPanel.add(
                backButton
        );

        card.add(
                formPanel,
                BorderLayout.CENTER
        );

        // ==========================================
        // CENTER WRAPPER
        // ==========================================

        JPanel centerWrapper =
                new JPanel(
                        new BorderLayout()
                );

        centerWrapper.setOpaque(false);

        centerWrapper.setBorder(
                new EmptyBorder(
                        22,
                        90,
                        0,
                        90
                )
        );

        centerWrapper.add(
                card,
                BorderLayout.CENTER
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                centerWrapper,
                BorderLayout.CENTER
        );

        setContentPane(
                mainPanel
        );

        // ==========================================
        // ACTIONS
        // ==========================================

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

    // ==========================================
    // LABEL STYLE
    // ==========================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                UITheme.LABEL
        );

        label.setForeground(
                UITheme.TEXT
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    // ==========================================
    // TEXT FIELD STYLE
    // ==========================================

    private void styleTextField(
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

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        UITheme.FIELD_HEIGHT
                )
        );

        field.setPreferredSize(
                new Dimension(
                        0,
                        UITheme.FIELD_HEIGHT
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

    // ==========================================
    // COMBO BOX STYLE
    // ==========================================

    private void styleComboBox(
            JComboBox<String> comboBox
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

        comboBox.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        comboBox.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        UITheme.FIELD_HEIGHT
                )
        );

        comboBox.setPreferredSize(
                new Dimension(
                        0,
                        UITheme.FIELD_HEIGHT
                )
        );

        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER_BLUE
                )
        );
    }

    // ==========================================
    // BACK BUTTON STYLE
    // ==========================================

    private void styleBackButton(
            JButton button
    ) {

        button.setFont(
                UITheme.BUTTON
        );

        button.setForeground(
                UITheme.NAVY
        );

        button.setBackground(
                Color.WHITE
        );

        button.setFocusPainted(
                false
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER_BLUE
                )
        );
    }

    // ==========================================
    // ROLE LABEL CHANGE
    // ==========================================

    private void updateNameLabel() {

        if (
                roleComboBox
                        .getSelectedItem()
                        .equals(
                                "Employer"
                        )
        ) {

            nameLabel.setText(
                    "Company Name"
            );

        } else {

            nameLabel.setText(
                    "Full Name"
            );
        }
    }

    // ==========================================
    // REGISTER
    // ==========================================

    private void register() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        String role =
                (String) roleComboBox
                        .getSelectedItem();

        String name =
                nameField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();

        if (
                username.isEmpty()
                        || password.isEmpty()
                        || name.isEmpty()
                        || email.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String error;

        try {

            if (
                    role.equals(
                            "Job Seeker"
                    )
            ) {

                error =
                        registerController
                                .registerJobSeeker(
                                        username,
                                        password,
                                        name,
                                        email,
                                        phone
                                );

            } else {

                error =
                        registerController
                                .registerEmployer(
                                        username,
                                        password,
                                        name,
                                        email,
                                        phone
                                );
            }

            if (error == null) {

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
                        error,
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

    // ==========================================
    // BACK TO LOGIN
    // ==========================================

    private void openLogin() {

        dispose();

        new LoginFrame()
                .setVisible(
                        true
                );
    }
}