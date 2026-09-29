package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private final User user;

    private JButton manageUsersButton;
    private JButton manageSkillsButton;
    private JButton manageCategoriesButton;
    private JButton manageJobsButton;
    private JButton matchReportsButton;
    private JButton logoutButton;

    public AdminDashboard(User user) {

        this.user = user;

        setTitle("JobFit - Admin Dashboard");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 40, 25, 40
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "JOBFIT - ADMIN DASHBOARD",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " + user.getUsername(),
                        SwingConstants.CENTER
                );

        welcomeLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        JPanel headerPanel =
                new JPanel(new GridLayout(2, 1));

        headerPanel.add(titleLabel);
        headerPanel.add(welcomeLabel);

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(3, 2, 15, 15)
                );

        manageUsersButton =
                new JButton("Manage Users");

        manageSkillsButton =
                new JButton("Manage Skills");

        manageCategoriesButton =
                new JButton("Manage Job Categories");

        manageJobsButton =
                new JButton("Manage Jobs");

        matchReportsButton =
                new JButton("View Match Reports");

        logoutButton =
                new JButton("Logout");

        buttonPanel.add(manageUsersButton);
        buttonPanel.add(manageSkillsButton);
        buttonPanel.add(manageCategoriesButton);
        buttonPanel.add(manageJobsButton);
        buttonPanel.add(matchReportsButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =========================
        // MANAGE USERS
        // =========================

        manageUsersButton.addActionListener(e -> {

            ManageUsersFrame frame =
                    new ManageUsersFrame(user);

            frame.setVisible(true);
        });

        // =========================
        // MANAGE SKILLS
        // =========================

        manageSkillsButton.addActionListener(e -> {

            ManageSkillsFrame frame =
                    new ManageSkillsFrame();

            frame.setVisible(true);
        });

        // =========================
        // MANAGE JOB CATEGORIES
        // =========================

        manageCategoriesButton.addActionListener(e -> {

            ManageJobCategoriesFrame frame =
                    new ManageJobCategoriesFrame();

            frame.setVisible(true);
        });

        // =========================
        // MANAGE JOBS
        // =========================

        manageJobsButton.addActionListener(e -> {

            AdminManageJobsFrame frame =
                    new AdminManageJobsFrame();

            frame.setVisible(true);
        });

        // =========================
        // MATCH REPORTS
        // =========================

        matchReportsButton.addActionListener(e -> {

            MatchReportsFrame frame =
                    new MatchReportsFrame();

            frame.setVisible(true);
        });

        // =========================
        // LOGOUT
        // =========================

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice == JOptionPane.YES_OPTION) {

            dispose();

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);
        }
    }
}