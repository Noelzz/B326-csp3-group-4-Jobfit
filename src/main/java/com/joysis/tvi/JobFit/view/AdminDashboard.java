package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private final User user;

    private JButton manageUsersButton;
    private JButton manageSkillsButton;
    private JButton manageCategoriesButton;
    private JButton manageJobsButton;
    private JButton matchReportsButton;
    private JButton logoutButton;

    // Modern colors
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(35, 40, 48);
    private final Color SECONDARY_TEXT = new Color(110, 118, 130);
    private final Color BUTTON_COLOR = new Color(45, 95, 170);
    private final Color BUTTON_HOVER = new Color(35, 78, 145);
    private final Color LOGOUT_COLOR = new Color(220, 70, 70);
    private final Color LOGOUT_HOVER = new Color(195, 55, 55);

    public AdminDashboard(User user) {

        this.user = user;

        setTitle("JobFit - Admin Dashboard");
        setSize(760, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {

        // =========================================
        // MAIN PANEL
        // =========================================

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                new EmptyBorder(25, 35, 25, 35)
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

        headerPanel.setBackground(BACKGROUND);

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
                new JLabel("ADMIN DASHBOARD");

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

        headerPanel.add(titleLabel);
        headerPanel.add(
                Box.createVerticalStrut(3)
        );
        headerPanel.add(dashboardLabel);
        headerPanel.add(
                Box.createVerticalStrut(6)
        );
        headerPanel.add(welcomeLabel);

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
                        "Administration"
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

        manageUsersButton =
                createModernButton(
                        "Manage Users"
                );

        manageSkillsButton =
                createModernButton(
                        "Manage Skills"
                );

        manageCategoriesButton =
                createModernButton(
                        "Manage Job Categories"
                );

        manageJobsButton =
                createModernButton(
                        "Manage Jobs"
                );

        matchReportsButton =
                createModernButton(
                        "View Match Reports"
                );

        // =========================================
        // ROW 1
        // =========================================

        gbc.gridx = 0;
        gbc.gridy = 0;

        buttonGrid.add(
                manageUsersButton,
                gbc
        );

        gbc.gridx = 1;

        buttonGrid.add(
                manageSkillsButton,
                gbc
        );

        // =========================================
        // ROW 2
        // =========================================

        gbc.gridx = 0;
        gbc.gridy = 1;

        buttonGrid.add(
                manageCategoriesButton,
                gbc
        );

        gbc.gridx = 1;

        buttonGrid.add(
                manageJobsButton,
                gbc
        );

// =========================================
// ROW 3 - CENTERED
// =========================================

        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.gridwidth = 2;

        gbc.anchor = GridBagConstraints.CENTER;

        buttonGrid.add(
                matchReportsButton,
                gbc
        );

        gbc.gridwidth = 1;
        // =========================================
        // CARD TOP
        // =========================================

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
        // ADD TO CARD
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
        // LOGOUT AREA
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
                        "JobFit • Skills and Job Matching System"
                );

        footerLabel.setHorizontalAlignment(
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
        // MAIN LAYOUT
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

        // =========================================
        // ADD MAIN PANEL
        // =========================================

        add(mainPanel);

        // =========================================
        // BUTTON ACTIONS
        // =========================================

        manageUsersButton.addActionListener(
                e -> {

                    ManageUsersFrame frame =
                            new ManageUsersFrame(user);

                    frame.setVisible(true);
                }
        );

        manageSkillsButton.addActionListener(
                e -> {

                    ManageSkillsFrame frame =
                            new ManageSkillsFrame();

                    frame.setVisible(true);
                }
        );

        manageCategoriesButton.addActionListener(
                e -> {

                    ManageJobCategoriesFrame frame =
                            new ManageJobCategoriesFrame();

                    frame.setVisible(true);
                }
        );

        manageJobsButton.addActionListener(
                e -> {

                    AdminManageJobsFrame frame =
                            new AdminManageJobsFrame();

                    frame.setVisible(true);
                }
        );

        matchReportsButton.addActionListener(
                e -> {

                    MatchReportsFrame frame =
                            new MatchReportsFrame();

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

        // Hover effect
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
    // SMALL LOGOUT BUTTON
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

        // Hover effect
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