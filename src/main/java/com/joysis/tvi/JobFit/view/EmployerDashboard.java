package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EmployerDashboard extends JFrame {

    private final User user;

    private JButton profileButton;
    private JButton postJobButton;
    private JButton manageJobsButton;
    private JButton applicantsButton;
    private JButton updateStatusButton;
    private JButton requiredSkillsButton;
    private JButton logoutButton;

    // =========================================
    // MODERN COLORS
    // =========================================

    private final Color BACKGROUND =
            new Color(245, 247, 250);

    private final Color CARD_COLOR =
            Color.WHITE;

    private final Color TEXT_COLOR =
            new Color(35, 40, 48);

    private final Color SECONDARY_TEXT =
            new Color(110, 118, 130);

    private final Color BUTTON_COLOR =
            new Color(45, 95, 170);

    private final Color BUTTON_HOVER =
            new Color(35, 78, 145);

    private final Color LOGOUT_COLOR =
            new Color(220, 70, 70);

    private final Color LOGOUT_HOVER =
            new Color(195, 55, 55);

    public EmployerDashboard(User user) {

        this.user = user;

        setTitle("JobFit - Employer Dashboard");
        setSize(760, 680);
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
                        25,
                        35,
                        25,
                        35
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

        JLabel titleLabel =
                new JLabel("JOBFIT");

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        titleLabel.setForeground(
                BUTTON_COLOR
        );

        JLabel dashboardLabel =
                new JLabel(
                        "EMPLOYER DASHBOARD"
                );

        dashboardLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        dashboardLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        dashboardLabel.setForeground(
                TEXT_COLOR
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome back, " +
                                user.getUsername()
                );

        welcomeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        welcomeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        welcomeLabel.setForeground(
                SECONDARY_TEXT
        );

        headerPanel.add(
                titleLabel
        );

        headerPanel.add(
                Box.createVerticalStrut(3)
        );

        headerPanel.add(
                dashboardLabel
        );

        headerPanel.add(
                Box.createVerticalStrut(6)
        );

        headerPanel.add(
                welcomeLabel
        );

        // =========================================
        // DASHBOARD CARD
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
                        25,
                        30,
                        25,
                        30
                )
        );

        // =========================================
        // SECTION TITLE
        // =========================================

        JLabel sectionLabel =
                new JLabel(
                        "Employer Management"
                );

        sectionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        sectionLabel.setForeground(
                TEXT_COLOR
        );

        JPanel cardHeader =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        cardHeader.setBackground(
                CARD_COLOR
        );

        cardHeader.add(
                sectionLabel
        );

        // =========================================
        // BUTTON GRID
        // =========================================

        JPanel buttonGrid =
                new JPanel(
                        new GridBagLayout()
                );

        buttonGrid.setBackground(
                CARD_COLOR
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8
                );

        gbc.fill =
                GridBagConstraints.NONE;

        // =========================================
        // CREATE BUTTONS
        // =========================================

        profileButton =
                createModernButton(
                        "Manage Company Profile"
                );

        postJobButton =
                createModernButton(
                        "Post Job"
                );

        manageJobsButton =
                createModernButton(
                        "Manage Job Posts"
                );

        applicantsButton =
                createModernButton(
                        "View Applicants"
                );

        updateStatusButton =
                createModernButton(
                        "Update Application Status"
                );

        requiredSkillsButton =
                createModernButton(
                        "Manage Required Skills"
                );

        // =========================================
        // ROW 1
        // =========================================

        gbc.gridx = 0;
        gbc.gridy = 0;

        buttonGrid.add(
                profileButton,
                gbc
        );

        gbc.gridx = 1;

        buttonGrid.add(
                postJobButton,
                gbc
        );

        // =========================================
        // ROW 2
        // =========================================

        gbc.gridx = 0;
        gbc.gridy = 1;

        buttonGrid.add(
                manageJobsButton,
                gbc
        );

        gbc.gridx = 1;

        buttonGrid.add(
                applicantsButton,
                gbc
        );

        // =========================================
        // ROW 3
        // =========================================

        gbc.gridx = 0;
        gbc.gridy = 2;

        buttonGrid.add(
                updateStatusButton,
                gbc
        );

        gbc.gridx = 1;

        buttonGrid.add(
                requiredSkillsButton,
                gbc
        );

        // =========================================
        // ADD CARD COMPONENTS
        // =========================================

        cardPanel.add(
                cardHeader,
                BorderLayout.NORTH
        );

        cardPanel.add(
                buttonGrid,
                BorderLayout.CENTER
        );

        // =========================================
        // LOGOUT
        // =========================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                10
                        )
                );

        bottomPanel.setBackground(
                BACKGROUND
        );

        logoutButton =
                createLogoutButton(
                        "Logout"
                );

        bottomPanel.add(
                logoutButton
        );

        // =========================================
        // FOOTER
        // =========================================

        JLabel footerLabel =
                new JLabel(
                        "JobFit • Skills and Job Matching System",
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

        JPanel centerWrapper =
                new JPanel(
                        new BorderLayout()
                );

        centerWrapper.setBackground(
                BACKGROUND
        );

        centerWrapper.setBorder(
                new EmptyBorder(
                        25,
                        0,
                        10,
                        0
                )
        );

        centerWrapper.add(
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
                centerWrapper,
                BorderLayout.CENTER
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================================
        // BUTTON ACTIONS
        // =========================================

        profileButton.addActionListener(
                e -> {

                    EmployerProfileFrame frame =
                            new EmployerProfileFrame(user);

                    frame.setVisible(true);
                }
        );

        postJobButton.addActionListener(
                e -> {

                    PostJobFrame frame =
                            new PostJobFrame(user);

                    frame.setVisible(true);
                }
        );

        manageJobsButton.addActionListener(
                e -> {

                    ManageJobsFrame frame =
                            new ManageJobsFrame(user);

                    frame.setVisible(true);
                }
        );

        applicantsButton.addActionListener(
                e -> {

                    ViewApplicantsFrame frame =
                            new ViewApplicantsFrame(user);

                    frame.setVisible(true);
                }
        );

        updateStatusButton.addActionListener(
                e -> {

                    UpdateApplicationStatusFrame frame =
                            new UpdateApplicationStatusFrame(user);

                    frame.setVisible(true);
                }
        );

        requiredSkillsButton.addActionListener(
                e -> {

                    ManageRequiredSkillsFrame frame =
                            new ManageRequiredSkillsFrame(user);

                    frame.setVisible(true);
                }
        );

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    // =========================================
    // MODERN BUTTON
    // =========================================

    private JButton createModernButton(
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
                BUTTON_COLOR
        );

        button.setPreferredSize(
                new Dimension(
                        250,
                        55
                )
        );

        button.setMinimumSize(
                new Dimension(
                        250,
                        55
                )
        );

        button.setMaximumSize(
                new Dimension(
                        250,
                        55
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
                                BUTTON_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                BUTTON_COLOR
                        );
                    }
                }
        );

        return button;
    }

    // =========================================
    // LOGOUT BUTTON
    // =========================================

    private JButton createLogoutButton(
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
                Color.WHITE
        );

        button.setBackground(
                LOGOUT_COLOR
        );

        button.setPreferredSize(
                new Dimension(
                        105,
                        34
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
                                LOGOUT_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                LOGOUT_COLOR
                        );
                    }
                }
        );

        return button;
    }

    // =========================================
    // LOGOUT
    // =========================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            dispose();

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);
        }
    }
}