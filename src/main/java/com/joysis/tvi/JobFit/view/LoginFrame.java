package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.LoginController;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    private final LoginController controller;

    // ==============================
    // MODERN COLORS
    // ==============================

    private final Color BACKGROUND =
            new Color(245, 247, 250);

    private final Color CARD_COLOR =
            Color.WHITE;

    private final Color TEXT_COLOR =
            new Color(35, 40, 48);

    private final Color SECONDARY_TEXT =
            new Color(110, 118, 130);

    private final Color BLUE =
            new Color(45, 95, 170);

    private final Color BLUE_HOVER =
            new Color(35, 78, 145);

    private final Color BORDER_COLOR =
            new Color(220, 224, 230);

    public LoginFrame() {

        controller = new LoginController();

        setTitle("JobFit - Login");
        setSize(520, 600);
        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    // =========================================
    // CREATE GUI
    // =========================================

    private void createGUI() {

        // =========================================
        // MAIN PANEL
        // =========================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        30,
                        45,
                        30,
                        45
                )
        );

        // =========================================
        // HEADER
        // =========================================

        JPanel headerPanel =
                new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        headerPanel.setBackground(
                BACKGROUND
        );

        JLabel logoLabel =
                new JLabel("JOBFIT");

        logoLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );

        logoLabel.setForeground(
                BLUE
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Skills and Job Matching System"
                );

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subtitleLabel.setForeground(
                SECONDARY_TEXT
        );

        headerPanel.add(
                logoLabel
        );

        headerPanel.add(
                Box.createVerticalStrut(5)
        );

        headerPanel.add(
                subtitleLabel
        );

        // =========================================
        // LOGIN CARD
        // =========================================

        JPanel cardPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                20
                        )
                );

        cardPanel.setBackground(
                CARD_COLOR
        );

        cardPanel.setBorder(
                new EmptyBorder(
                        30,
                        35,
                        30,
                        35
                )
        );

        // =========================================
        // CARD TITLE
        // =========================================

        JLabel loginTitle =
                new JLabel(
                        "Welcome Back",
                        SwingConstants.CENTER
                );

        loginTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        loginTitle.setForeground(
                TEXT_COLOR
        );

        JLabel loginSubtitle =
                new JLabel(
                        "Sign in to continue to JobFit",
                        SwingConstants.CENTER
                );

        loginSubtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        loginSubtitle.setForeground(
                SECONDARY_TEXT
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(
                CARD_COLOR
        );

        loginTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginSubtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        titlePanel.add(
                loginTitle
        );

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(
                loginSubtitle
        );

        // =========================================
        // FORM PANEL
        // =========================================

        JPanel formPanel =
                new JPanel();

        formPanel.setLayout(
                new BoxLayout(
                        formPanel,
                        BoxLayout.Y_AXIS
                )
        );

        formPanel.setBackground(
                CARD_COLOR
        );

        // =========================================
        // USERNAME
        // =========================================

        JLabel usernameLabel =
                createLabel(
                        "Username"
                );

        usernameField =
                new JTextField();

        styleTextField(
                usernameField
        );

        formPanel.add(
                usernameLabel
        );

        formPanel.add(
                Box.createVerticalStrut(7)
        );

        formPanel.add(
                usernameField
        );

        // =========================================
        // PASSWORD
        // =========================================

        formPanel.add(
                Box.createVerticalStrut(18)
        );

        JLabel passwordLabel =
                createLabel(
                        "Password"
                );

        passwordField =
                new JPasswordField();

        styleTextField(
                passwordField
        );

        formPanel.add(
                passwordLabel
        );

        formPanel.add(
                Box.createVerticalStrut(7)
        );

        formPanel.add(
                passwordField
        );

        // =========================================
        // BUTTONS
        // =========================================

        formPanel.add(
                Box.createVerticalStrut(25)
        );

        loginButton =
                createPrimaryButton(
                        "Login"
                );

        formPanel.add(
                loginButton
        );

        formPanel.add(
                Box.createVerticalStrut(10)
        );

        registerButton =
                createSecondaryButton(
                        "Create an Account"
                );

        formPanel.add(
                registerButton
        );

        // =========================================
        // CARD CONTENT
        // =========================================

        JPanel cardContent =
                new JPanel(
                        new BorderLayout()
                );

        cardContent.setBackground(
                CARD_COLOR
        );

        cardContent.add(
                titlePanel,
                BorderLayout.NORTH
        );

        cardContent.add(
                formPanel,
                BorderLayout.CENTER
        );

        cardPanel.add(
                cardContent,
                BorderLayout.CENTER
        );

        // =========================================
        // FOOTER
        // =========================================

        JLabel footerLabel =
                new JLabel(
                        "JobFit • Find the right skills for the right job",
                        SwingConstants.CENTER
                );

        footerLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        footerLabel.setForeground(
                SECONDARY_TEXT
        );

        // =========================================
        // CENTER WRAPPER
        // =========================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout()
                );

        centerPanel.setBackground(
                BACKGROUND
        );

        centerPanel.setBorder(
                new EmptyBorder(
                        25,
                        0,
                        20,
                        0
                )
        );

        centerPanel.add(
                cardPanel,
                BorderLayout.CENTER
        );

        // =========================================
        // MAIN LAYOUT
        // =========================================

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                footerLabel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================================
        // ACTIONS
        // =========================================

        loginButton.addActionListener(
                e -> login()
        );

        registerButton.addActionListener(
                e -> {

                    RegisterFrame frame =
                            new RegisterFrame();

                    frame.setVisible(true);

                    dispose();
                }
        );

        // Press Enter to login
        passwordField.addActionListener(
                e -> login()
        );

        usernameField.addActionListener(
                e -> passwordField.requestFocus()
        );
    }

    // =========================================
    // LABEL
    // =========================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(
                TEXT_COLOR
        );

        return label;
    }

    // =========================================
    // TEXT FIELD
    // =========================================

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        field.setPreferredSize(
                new Dimension(
                        350,
                        42
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                12,
                                0,
                                12
                        )
                )
        );

        field.setBackground(
                Color.WHITE
        );

        field.setForeground(
                TEXT_COLOR
        );
    }

    // =========================================
    // PRIMARY BUTTON
    // =========================================

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                BLUE
        );

        button.setPreferredSize(
                new Dimension(
                        350,
                        42
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                BLUE_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                BLUE
                        );
                    }
                }
        );

        return button;
    }

    // =========================================
    // SECONDARY BUTTON
    // =========================================

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(
                BLUE
        );

        button.setBackground(
                Color.WHITE
        );

        button.setPreferredSize(
                new Dimension(
                        350,
                        38
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        BLUE,
                        1
                )
        );

        button.setOpaque(true);

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        240,
                                        245,
                                        253
                                )
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                Color.WHITE
                        );
                    }
                }
        );

        return button;
    }

    // =========================================
    // LOGIN
    // =========================================

    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

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

        User user =
                controller.login(
                        username,
                        password
                );

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
            passwordField.requestFocus();

            return;
        }

        dispose();

        // =========================================
        // OPEN DASHBOARD BASED ON ROLE
        // =========================================

        if ("admin".equals(
                user.getRole()
        )) {

            AdminDashboard dashboard =
                    new AdminDashboard(user);

            dashboard.setVisible(true);

        } else if ("job_seeker".equals(
                user.getRole()
        )) {

            JobSeekerDashboard dashboard =
                    new JobSeekerDashboard(user);

            dashboard.setVisible(true);

        } else if ("employer".equals(
                user.getRole()
        )) {

            EmployerDashboard dashboard =
                    new EmployerDashboard(user);

            dashboard.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unknown user role.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            new LoginFrame().setVisible(true);
        }
    }
}