package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;

public class JobSeekerDashboard extends JFrame {

    private final User user;

    private JButton profileButton;
    private JButton skillsButton;
    private JButton browseJobsButton;
    private JButton matchedJobsButton;
    private JButton applyJobButton;
    private JButton trackApplicationsButton;
    private JButton logoutButton;

    public JobSeekerDashboard(User user) {

        this.user = user;

        setTitle("JobFit - Job Seeker Dashboard");
        setSize(700, 600);
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
                        "JOBFIT - JOB SEEKER DASHBOARD",
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
                        new GridLayout(4, 2, 15, 15)
                );

        profileButton =
                new JButton("Manage Profile");

        skillsButton =
                new JButton("Manage Skills");

        browseJobsButton =
                new JButton("Browse Jobs");

        matchedJobsButton =
                new JButton("View Matched Jobs");

        applyJobButton =
                new JButton("Apply for Job");

        trackApplicationsButton =
                new JButton("Track Applications");

        logoutButton =
                new JButton("Logout");

        buttonPanel.add(profileButton);
        buttonPanel.add(skillsButton);
        buttonPanel.add(browseJobsButton);
        buttonPanel.add(matchedJobsButton);
        buttonPanel.add(applyJobButton);
        buttonPanel.add(trackApplicationsButton);
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

        profileButton.addActionListener(e -> {

            JobSeekerProfileFrame profileFrame =
                    new JobSeekerProfileFrame(user);

            profileFrame.setVisible(true);
        });

        skillsButton.addActionListener(e -> {

            JobSeekerSkillsFrame skillsFrame =
                    new JobSeekerSkillsFrame(user);

            skillsFrame.setVisible(true);
        });

        browseJobsButton.addActionListener(e -> {

            BrowseJobsFrame browseJobsFrame =
                    new BrowseJobsFrame(user);

            browseJobsFrame.setVisible(true);
        });

        matchedJobsButton.addActionListener(e -> {

            MatchedJobsFrame matchedJobsFrame =
                    new MatchedJobsFrame(user);

            matchedJobsFrame.setVisible(true);
        });

        applyJobButton.addActionListener(e -> {

            ApplyJobFrame applyJobFrame =
                    new ApplyJobFrame(user);

            applyJobFrame.setVisible(true);
        });

        trackApplicationsButton.addActionListener(e -> {

            TrackApplicationsFrame trackApplicationsFrame =
                    new TrackApplicationsFrame(user);

            trackApplicationsFrame.setVisible(true);
        });

        logoutButton.addActionListener(e -> logout());
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