package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminDashboard extends BaseDashboardFrame {

    private final User user;

    private ManageUsersFrame manageUsersFrame;
    private ManageSkillsFrame manageSkillsFrame;
    private AdminJobManagementFrame adminJobManagementFrame;
    private MatchReportsFrame matchReportsFrame;
    private AdminProfileFrame adminProfileFrame;

    public AdminDashboard(User user) {

        super(
                "JobFit - Admin Dashboard",
                capitalizeName(user.getUsername()),
                "Admin Panel");
        this.user = user;

        createMenu();
        createContent();
    }


    // SIDEBAR MENU

    private void createMenu() {

        addSidebarButton("Dashboard", true);
        JButton users = addSidebarButton("Manage Users", false);
        JButton skills = addSidebarButton("Manage Skills", false);
        JButton jobs = addSidebarButton("Jobs & Categories", false);
        JButton reports = addSidebarButton("Match Reports", false);

        addSidebarGlue();

        JButton profile = addSidebarButton("My Profile", false);
        JButton logout = addSidebarButton("Logout", false);
        logout.setForeground(UITheme.DANGER);

        // MANAGE USERS
        users.addActionListener(e -> {

            if (
                    manageUsersFrame == null || !manageUsersFrame.isDisplayable()
            ) {

                manageUsersFrame = new ManageUsersFrame(user);
            }
            manageUsersFrame.setVisible(true);
            manageUsersFrame.toFront();
        });

        // MANAGE SKILLS
        skills.addActionListener(e -> {

            if (
                    manageSkillsFrame == null || !manageSkillsFrame.isDisplayable()
            ) {

                manageSkillsFrame = new ManageSkillsFrame();
            }

            manageSkillsFrame.setVisible(true);
            manageSkillsFrame.toFront();
        });

        // JOBS AND CATEGORIES
        jobs.addActionListener(e -> {

            if (
                    adminJobManagementFrame == null
                            || !adminJobManagementFrame.isDisplayable()
            ) {

                adminJobManagementFrame =
                        new AdminJobManagementFrame();
            }

            adminJobManagementFrame.setVisible(true);
            adminJobManagementFrame.toFront();
        });

        // MATCH REPORTS
        reports.addActionListener(e -> {

            if (
                    matchReportsFrame == null
                            || !matchReportsFrame.isDisplayable()
            ) {

                matchReportsFrame =
                        new MatchReportsFrame();
            }

            matchReportsFrame.setVisible(true);
            matchReportsFrame.toFront();
        });

        // PROFILE
        profile.addActionListener(e -> {

            if (
                    adminProfileFrame == null || !adminProfileFrame.isDisplayable()
            ) {

                adminProfileFrame = new AdminProfileFrame(user);
            }

            adminProfileFrame.setVisible(true);
            adminProfileFrame.toFront();
        });

        // LOGOUT
        logout.addActionListener(e -> logout());
    }


    // DASHBOARD CONTENT
    private void createContent() {
        // WELCOME AREA
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false);
        welcomePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 82));
        welcomePanel.setPreferredSize(new Dimension(0, 82));
        welcomePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel welcomeText = new JPanel();
        welcomeText.setOpaque(false);
        welcomeText.setLayout(new BoxLayout(welcomeText, BoxLayout.Y_AXIS));
        JLabel welcomeLabel = new JLabel("Welcome back, " + capitalizeName(user.getUsername()));

        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(UITheme.TEXT);
        JLabel subtitle = new JLabel("Manage and monitor the JobFit system.");
        subtitle.setFont(UITheme.BODY);
        subtitle.setForeground(UITheme.TEXT_SECONDARY);
        welcomeText.add(welcomeLabel);
        welcomeText.add(Box.createVerticalStrut(4));
        welcomeText.add(subtitle);

        // DATE CARD
        RoundedPanel dateCard = new RoundedPanel(14);
        dateCard.setBackground(UITheme.SURFACE);
        dateCard.setLayout(new BorderLayout());
        dateCard.setPreferredSize(new Dimension(155, 64));
        dateCard.setMaximumSize(new Dimension(155, 64));
        dateCard.setBorder(new EmptyBorder(10, 14, 10, 14));
        JLabel dateTitle = new JLabel("Today");
        dateTitle.setFont(UITheme.SMALL);
        dateTitle.setForeground(UITheme.TEXT_SECONDARY);
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("MMM d, yyyy");
        JLabel dateValue = new JLabel(today.format(formatter));
        dateValue.setFont(UITheme.LABEL);
        dateValue.setForeground(UITheme.TEXT);
        JPanel dateText = new JPanel();
        dateText.setOpaque(false);
        dateText.setLayout(new BoxLayout(dateText, BoxLayout.Y_AXIS));
        dateText.add(dateTitle);
        dateText.add(Box.createVerticalStrut(3));

        dateText.add(dateValue);
        dateCard.add(dateText, BorderLayout.CENTER);

        welcomePanel.add(welcomeText, BorderLayout.WEST);
        welcomePanel.add(dateCard, BorderLayout.EAST);
        contentPanel.add(welcomePanel);
        contentPanel.add(Box.createVerticalStrut(16));


            // ADMINISTRATION CONTAINER

        RoundedPanel container = new RoundedPanel(22);
        container.setBackground(Color.WHITE);
        container.setLayout(new BorderLayout());
        container.setBorder(new EmptyBorder(20, 22, 22, 22));
        container.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 385));
        container.setPreferredSize(new Dimension(0, 385));
        JLabel title = UIComponents.createSectionTitle("Administration");


            // CARD GRID
        JPanel cards = new JPanel(new GridLayout(2, 2, 14, 14));
        cards.setOpaque(false);
        cards.setBorder(new EmptyBorder(14, 0, 0, 0));


            // MANAGE USERS

        cards.add(UIComponents.createClickableActionCard(
                "Manage Users",
                "View, add, update and manage system users.",
                "👥", () -> {
                    if (manageUsersFrame == null || !manageUsersFrame.isDisplayable()) {
                        manageUsersFrame = new ManageUsersFrame(user);}

                    manageUsersFrame.setVisible(true);
                    manageUsersFrame.toFront();
                }
                )
        );


            // MANAGE SKILLS
        cards.add(UIComponents.createClickableActionCard(
                "Manage Skills",
                "Maintain JobFit skills and skill categories.",
                "⚙", () -> {
                    if (manageSkillsFrame == null || !manageSkillsFrame.isDisplayable()
                    ) {
                        manageSkillsFrame = new ManageSkillsFrame();
                    }

                    manageSkillsFrame.setVisible(true);
                    manageSkillsFrame.toFront();
                }

                )
            );


            // JOBS AND CATEGORIES
        cards.add(UIComponents.createClickableActionCard("Manage Jobs & Categories", "Manage job listings and job categories.", "▣", () -> {

            if (
                    adminJobManagementFrame == null || !adminJobManagementFrame.isDisplayable())
            {
                adminJobManagementFrame = new AdminJobManagementFrame();
            }

            adminJobManagementFrame.setVisible(true);
            adminJobManagementFrame.toFront();
        }
        )

        );


        // MATCH REPORTS


        cards.add(UIComponents.createClickableActionCard(
                "View Match Reports",
                "Generate and review JobFit matching reports.",
                "▥", () -> {

                    if (
                            matchReportsFrame == null || !matchReportsFrame.isDisplayable())
                    {

                        matchReportsFrame = new MatchReportsFrame();
                    }

                    matchReportsFrame.setVisible(true);
                    matchReportsFrame.toFront();
                }
                )
            );

            container.add(title, BorderLayout.NORTH);
            container.add(cards, BorderLayout.CENTER);
            contentPanel.add(container);
        }

    // ==========================================
    // CAPITALIZE USERNAME
    // admin -> Admin
    // john doe -> John Doe
    // ==========================================

    private static String capitalizeName(
            String name
    ) {

        if (
                name == null || name.trim().isEmpty()
        ) {
            return "";
        }

        String[] words = name.trim()
                        .toLowerCase()
                        .split("\\s+");

        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1));
                }

                result.append(" ");
            }
        }

        return result.toString().trim();
    }

    // ==========================================
    // LOGOUT
    // ==========================================

    private void logout() {

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (
                result == JOptionPane.YES_OPTION
        ) {

            dispose();
            new LoginFrame()
                    .setVisible(true);
        }
    }
}