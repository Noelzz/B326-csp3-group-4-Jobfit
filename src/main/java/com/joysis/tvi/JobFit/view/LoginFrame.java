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

    private final Color NAVY =
            new Color(3, 24, 55);

    private final Color BLUE =
            new Color(0, 105, 255);

    private final Color CYAN =
            new Color(15, 205, 255);

    private final Color TEXT_WHITE =
            new Color(245, 250, 255);

    private final Color TEXT_MUTED =
            new Color(170, 195, 220);

    public LoginFrame() {

        controller = new LoginController();

        setTitle("JobFit - Login");

        setSize(1100, 680);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {

        JPanel background =
                new FuturisticBackgroundPanel();

        background.setLayout(
                new GridLayout(1, 2)
        );

        background.add(
                createBrandPanel()
        );

        background.add(
                createLoginArea()
        );

        setContentPane(background);
    }

    // =====================================================
    // LEFT SIDE
    // =====================================================

    private JPanel createBrandPanel() {

        JPanel panel = new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                new EmptyBorder(
                        165,
                        65,
                        55,
                        55
                )
        );

        JPanel jobFitPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        jobFitPanel.setOpaque(false);

        jobFitPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        65
                )
        );

        jobFitPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel job =
                new JLabel("JOB");

        job.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        48
                )
        );

        job.setForeground(
                Color.WHITE
        );

        JLabel fit =
                new JLabel("FIT");

        fit.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        48
                )
        );

        fit.setForeground(
                CYAN
        );

        jobFitPanel.add(job);
        jobFitPanel.add(fit);

        JLabel subtitle =
                new JLabel(
                        "A Skills and Job Matching System"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        17
                )
        );

        subtitle.setForeground(
                new Color(
                        215,
                        230,
                        245
                )
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel cyanLine =
                new JPanel();

        cyanLine.setBackground(
                CYAN
        );

        cyanLine.setMaximumSize(
                new Dimension(
                        100,
                        4
                )
        );

        cyanLine.setPreferredSize(
                new Dimension(
                        100,
                        4
                )
        );

        cyanLine.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel tagline1 =
                new JLabel(
                        "Find the right skills."
                );

        tagline1.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        18
                )
        );

        tagline1.setForeground(
                TEXT_WHITE
        );

        tagline1.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel tagline2 =
                new JLabel(
                        "Find the right opportunity."
                );

        tagline2.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        18
                )
        );

        tagline2.setForeground(
                CYAN
        );

        tagline2.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel bottom =
                new JLabel(
                        "SKILLS  •  MATCH  •  OPPORTUNITIES"
                );

        bottom.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        bottom.setForeground(
                new Color(
                        125,
                        185,
                        225
                )
        );

        bottom.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(jobFitPanel);

        panel.add(
                Box.createVerticalStrut(12)
        );

        panel.add(subtitle);

        panel.add(
                Box.createVerticalStrut(28)
        );

        panel.add(cyanLine);

        panel.add(
                Box.createVerticalStrut(65)
        );

        panel.add(tagline1);

        panel.add(
                Box.createVerticalStrut(7)
        );

        panel.add(tagline2);

        panel.add(
                Box.createVerticalGlue()
        );

        panel.add(bottom);

        return panel;
    }

    // =====================================================
    // RIGHT SIDE
    // =====================================================

    private JPanel createLoginArea() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setOpaque(false);

        JPanel card =
                new LoginCardPanel();

        // Mas compact
        card.setPreferredSize(
                new Dimension(
                        440,
                        500
                )
        );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        30,
                        44,
                        30,
                        44
                )
        );

        card.add(
                createLoginForm(),
                BorderLayout.CENTER
        );

        panel.add(card);

        return panel;
    }

    // =====================================================
    // LOGIN FORM
    // =====================================================

    private JPanel createLoginForm() {

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // =================================================
        // TITLE
        // =================================================

        JLabel title =
                new JLabel(
                        "Welcome Back",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        29
                )
        );

        title.setForeground(
                Color.WHITE
        );

        gbc.gridy = 0;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        5,
                        0
                );

        form.add(
                title,
                gbc
        );

        // =================================================
        // SUBTITLE
        // =================================================

        JLabel subtitle =
                new JLabel(
                        "Sign in to continue to JobFit",
                        SwingConstants.CENTER
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                TEXT_MUTED
        );

        gbc.gridy = 1;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        26,
                        0
                );

        form.add(
                subtitle,
                gbc
        );

        // =================================================
        // USERNAME LABEL
        // =================================================

        JLabel usernameLabel =
                createLabel(
                        "Username"
                );

        gbc.gridy = 2;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        6,
                        0
                );

        form.add(
                usernameLabel,
                gbc
        );

        // =================================================
        // USERNAME FIELD
        // =================================================

        usernameField =
                new JTextField();

        styleTextField(
                usernameField
        );

        gbc.gridy = 3;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        17,
                        0
                );

        form.add(
                usernameField,
                gbc
        );

        // =================================================
        // PASSWORD LABEL
        // =================================================

        JLabel passwordLabel =
                createLabel(
                        "Password"
                );

        gbc.gridy = 4;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        6,
                        0
                );

        form.add(
                passwordLabel,
                gbc
        );

        // =================================================
        // PASSWORD FIELD
        // =================================================

        passwordField =
                new JPasswordField();

        passwordField.setEchoChar(
                '•'
        );

        styleTextField(
                passwordField
        );

        gbc.gridy = 5;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        9,
                        0
                );

        form.add(
                passwordField,
                gbc
        );

        // =================================================
        // SHOW PASSWORD
        // =================================================

        JCheckBox showPassword =
                new JCheckBox(
                        "Show password"
                );

        showPassword.setOpaque(
                false
        );

        showPassword.setFocusPainted(
                false
        );

        showPassword.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        showPassword.setForeground(
                TEXT_MUTED
        );

        showPassword.addActionListener(
                e -> {

                    if (
                            showPassword
                                    .isSelected()
                    ) {

                        passwordField
                                .setEchoChar(
                                        (char) 0
                                );

                    } else {

                        passwordField
                                .setEchoChar(
                                        '•'
                                );
                    }
                }
        );

        gbc.gridy = 6;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.fill =
                GridBagConstraints.NONE;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        18,
                        0
                );

        form.add(
                showPassword,
                gbc
        );

        // =================================================
        // LOGIN BUTTON
        // =================================================

        loginButton =
                new RoundedButton(
                        "Login",
                        new Color(
                                0,
                                100,
                                255
                        ),
                        new Color(
                                0,
                                205,
                                255
                        ),
                        new Color(
                                20,
                                125,
                                255
                        ),
                        new Color(
                                35,
                                225,
                                255
                        ),
                        true
                );

        loginButton.setPreferredSize(
                new Dimension(
                        350,
                        48
                )
        );

        gbc.gridy = 7;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        13,
                        0
                );

        form.add(
                loginButton,
                gbc
        );

        // =================================================
        // DIVIDER
        // =================================================

        DividerPanel divider =
                new DividerPanel();

        divider.setPreferredSize(
                new Dimension(
                        350,
                        22
                )
        );

        gbc.gridy = 8;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        12,
                        0
                );

        form.add(
                divider,
                gbc
        );

        // =================================================
        // CREATE ACCOUNT
        // =================================================

        registerButton =
                new RoundedButton(
                        "Create an Account",
                        new Color(
                                7,
                                46,
                                90
                        ),
                        new Color(
                                9,
                                70,
                                125
                        ),
                        new Color(
                                10,
                                65,
                                115
                        ),
                        new Color(
                                15,
                                100,
                                155
                        ),
                        false
                );

        registerButton.setPreferredSize(
                new Dimension(
                        350,
                        45
                )
        );

        gbc.gridy = 9;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        0,
                        0
                );

        form.add(
                registerButton,
                gbc
        );

        // =================================================
        // ACTIONS
        // =================================================

        loginButton.addActionListener(
                e -> login()
        );

        registerButton.addActionListener(
                e -> {

                    new RegisterFrame()
                            .setVisible(true);

                    dispose();
                }
        );

        usernameField.addActionListener(
                e ->
                        passwordField
                                .requestFocus()
        );

        passwordField.addActionListener(
                e -> login()
        );

        return form;
    }

    // =====================================================
    // LABEL
    // =====================================================

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
                new Color(
                        225,
                        238,
                        250
                )
        );

        return label;
    }

    // =====================================================
    // TEXT FIELD
    // =====================================================

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

        field.setForeground(
                Color.WHITE
        );

        field.setCaretColor(
                CYAN
        );

        field.setBackground(
                new Color(
                        7,
                        40,
                        80
                )
        );

        field.setPreferredSize(
                new Dimension(
                        350,
                        45
                )
        );

        field.setBorder(
                BorderFactory
                        .createCompoundBorder(

                                BorderFactory
                                        .createLineBorder(
                                                new Color(
                                                        25,
                                                        135,
                                                        220
                                                ),
                                                1
                                        ),

                                BorderFactory
                                        .createEmptyBorder(
                                                0,
                                                14,
                                                0,
                                                14
                                        )
                        )
        );
    }

    // =====================================================
    // LOGIN LOGIC
    // =====================================================

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

        if (
                username.isEmpty()
                        || password.isEmpty()
        ) {

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

        if (
                "admin".equals(
                        user.getRole()
                )
        ) {

            new AdminDashboard(user)
                    .setVisible(true);

        } else if (
                "job_seeker".equals(
                        user.getRole()
                )
        ) {

            new JobSeekerDashboard(user)
                    .setVisible(true);

        } else if (
                "employer".equals(
                        user.getRole()
                )
        ) {

            new EmployerDashboard(user)
                    .setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unknown user role.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            new LoginFrame()
                    .setVisible(true);
        }
    }

    // =====================================================
    // FUTURISTIC BACKGROUND
    // =====================================================

    private class FuturisticBackgroundPanel
            extends JPanel {

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w = getWidth();
            int h = getHeight();

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    3,
                                    22,
                                    52
                            ),
                            w,
                            h,
                            new Color(
                                    5,
                                    44,
                                    95
                            )
                    );

            g2.setPaint(
                    gradient
            );

            g2.fillRect(
                    0,
                    0,
                    w,
                    h
            );

            // GRID
            g2.setColor(
                    new Color(
                            20,
                            140,
                            255,
                            18
                    )
            );

            for (
                    int x = 0;
                    x < w;
                    x += 40
            ) {

                g2.drawLine(
                        x,
                        0,
                        x,
                        h
                );
            }

            for (
                    int y = 0;
                    y < h;
                    y += 40
            ) {

                g2.drawLine(
                        0,
                        y,
                        w,
                        y
                );
            }

            // TOP CIRCUIT
            g2.setColor(
                    new Color(
                            10,
                            205,
                            255,
                            170
                    )
            );

            g2.setStroke(
                    new BasicStroke(2f)
            );

            g2.drawLine(
                    0,
                    145,
                    95,
                    145
            );

            g2.drawLine(
                    95,
                    145,
                    145,
                    195
            );

            g2.drawLine(
                    145,
                    195,
                    330,
                    195
            );

            g2.fillOval(
                    325,
                    190,
                    10,
                    10
            );

            // BOTTOM CIRCUIT
            g2.drawLine(
                    0,
                    h - 150,
                    105,
                    h - 150
            );

            g2.drawLine(
                    105,
                    h - 150,
                    160,
                    h - 205
            );

            g2.drawLine(
                    160,
                    h - 205,
                    350,
                    h - 205
            );

            g2.fillOval(
                    345,
                    h - 210,
                    10,
                    10
            );

            g2.dispose();
        }
    }

    // =====================================================
    // LOGIN CARD
    // =====================================================

    private class LoginCardPanel
            extends JPanel {

        public LoginCardPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // SHADOW
            g2.setColor(
                    new Color(
                            0,
                            160,
                            255,
                            45
                    )
            );

            g2.fillRoundRect(
                    5,
                    7,
                    getWidth() - 10,
                    getHeight() - 10,
                    28,
                    28
            );

            // CARD
            g2.setColor(
                    new Color(
                            4,
                            31,
                            68,
                            245
                    )
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 8,
                    getHeight() - 8,
                    28,
                    28
            );

            // BORDER
            g2.setColor(
                    new Color(
                            20,
                            160,
                            255,
                            150
                    )
            );

            g2.setStroke(
                    new BasicStroke(
                            1.3f
                    )
            );

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 9,
                    getHeight() - 9,
                    28,
                    28
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =====================================================
    // DIVIDER
    // =====================================================

    private class DividerPanel
            extends JPanel {

        public DividerPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int centerY =
                    getHeight() / 2;

            int centerX =
                    getWidth() / 2;

            g2.setColor(
                    new Color(
                            95,
                            145,
                            190
                    )
            );

            g2.drawLine(
                    0,
                    centerY,
                    centerX - 25,
                    centerY
            );

            g2.drawLine(
                    centerX + 25,
                    centerY,
                    getWidth(),
                    centerY
            );

            String text = "or";

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            12
                    )
            );

            g2.setColor(
                    TEXT_MUTED
            );

            FontMetrics fm =
                    g2.getFontMetrics();

            g2.drawString(
                    text,
                    centerX
                            - fm.stringWidth(text)
                            / 2,
                    centerY
                            + fm.getAscent()
                            / 2
                            - 1
            );

            g2.dispose();
        }
    }
}