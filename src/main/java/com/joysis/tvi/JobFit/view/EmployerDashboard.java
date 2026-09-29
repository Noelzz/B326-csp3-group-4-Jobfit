package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;

public class EmployerDashboard extends JFrame {

    private final User user;

    private JButton profileButton;
    private JButton postJobButton;
    private JButton manageJobsButton;
    private JButton applicantsButton;
    private JButton statusButton;
    private JButton requiredSkillsButton;
    private JButton logoutButton;

    public EmployerDashboard(User user) {

        this.user = user;

        setTitle("JobFit - Employer Dashboard");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        // =========================
        // HEADER
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "JOBFIT - EMPLOYER DASHBOARD",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " + user.getUsername(),
                        SwingConstants.CENTER
                );

        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        JPanel headerPanel =
                new JPanel(
                        new GridLayout(2, 1)
                );

        headerPanel.add(titleLabel);
        headerPanel.add(welcomeLabel);

        // =========================
        // BUTTONS
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                15,
                                15
                        )
                );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        50,
                        30,
                        50
                )
        );

        profileButton =
                new JButton(
                        "Manage Company Profile"
                );

        postJobButton =
                new JButton(
                        "Post Job"
                );

        manageJobsButton =
                new JButton(
                        "Manage Job Posts"
                );

        applicantsButton =
                new JButton(
                        "View Applicants"
                );

        statusButton =
                new JButton(
                        "Update Application Status"
                );

        requiredSkillsButton =
                new JButton(
                        "Manage Required Skills"
                );

        logoutButton =
                new JButton(
                        "Logout"
                );

        buttonPanel.add(profileButton);
        buttonPanel.add(postJobButton);

        buttonPanel.add(manageJobsButton);
        buttonPanel.add(applicantsButton);

        buttonPanel.add(statusButton);
        buttonPanel.add(requiredSkillsButton);

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
        // BUTTON EVENTS
        // =========================

        profileButton.addActionListener(e -> {

            EmployerProfileFrame profileFrame =
                    new EmployerProfileFrame(user);

            profileFrame.setVisible(true);
        });

        postJobButton.addActionListener(e -> {

            PostJobFrame postJobFrame =
                    new PostJobFrame(user);

            postJobFrame.setVisible(true);
        });

        manageJobsButton.addActionListener(e -> {

            ManageJobsFrame manageJobsFrame =
                    new ManageJobsFrame(user);

            manageJobsFrame.setVisible(true);
        });

        applicantsButton.addActionListener(e -> {

            ViewApplicantsFrame applicantsFrame =
                    new ViewApplicantsFrame(user);

            applicantsFrame.setVisible(true);
        });

        statusButton.addActionListener(e -> {

            UpdateApplicationStatusFrame statusFrame =
                    new UpdateApplicationStatusFrame(user);

            statusFrame.setVisible(true);
        });

        requiredSkillsButton.addActionListener(e -> {

            ManageRequiredSkillsFrame requiredSkillsFrame =
                    new ManageRequiredSkillsFrame(user);

            requiredSkillsFrame.setVisible(true);
        });

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

        if (choice ==
                JOptionPane.YES_OPTION) {

            dispose();

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);
        }
    }
}