package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JobSeekerDashboard extends BaseDashboardFrame {

    private final User user;

    private JobSeekerProfileFrame jobSeekerProfileFrame;
    private JobSeekerSkillsFrame jobSeekerSkillsFrame;
    private BrowseJobsFrame browseJobsFrame;
    private TrackApplicationsFrame trackApplicationsFrame;

    public JobSeekerDashboard(User user) {

        super(
                "JobFit - Job Seeker Dashboard",
                capitalizeName(user.getUsername()),
                "Job Seeker Panel"
        );

        this.user = user;

        createMenu();
        createContent();
    }

    // ==========================================
    // SIDEBAR MENU
    // ==========================================

    private void createMenu() {

        addSidebarButton(
                "Dashboard",
                true
        );

        JButton profileButton =
                addSidebarButton(
                        "Manage Profile",
                        false
                );

        JButton skillsButton =
                addSidebarButton(
                        "Manage Skills",
                        false
                );

        JButton browseJobsButton =
                addSidebarButton(
                        "Browse Jobs",
                        false
                );

        JButton trackApplicationsButton =
                addSidebarButton(
                        "Track Applications",
                        false
                );

        addSidebarGlue();

        JButton logoutButton =
                addSidebarButton(
                        "Logout",
                        false
                );

        logoutButton.setForeground(
                UITheme.DANGER
        );

        profileButton.addActionListener(e -> {

            if (
                    jobSeekerProfileFrame == null
                            || !jobSeekerProfileFrame.isDisplayable()
            ) {

                jobSeekerProfileFrame =
                        new JobSeekerProfileFrame(user);
            }

            jobSeekerProfileFrame.setVisible(true);
            jobSeekerProfileFrame.toFront();
        });

        skillsButton.addActionListener(e -> {

            if (
                    jobSeekerSkillsFrame == null
                            || !jobSeekerSkillsFrame.isDisplayable()
            ) {

                jobSeekerSkillsFrame =
                        new JobSeekerSkillsFrame(user);
            }

            jobSeekerSkillsFrame.setVisible(true);
            jobSeekerSkillsFrame.toFront();
        });

        browseJobsButton.addActionListener(e -> {

            if (
                    browseJobsFrame == null
                            || !browseJobsFrame.isDisplayable()
            ) {

                browseJobsFrame =
                        new BrowseJobsFrame(user);
            }

            browseJobsFrame.setVisible(true);
            browseJobsFrame.toFront();
        });

        trackApplicationsButton.addActionListener(e -> {

            if (
                    trackApplicationsFrame == null
                            || !trackApplicationsFrame.isDisplayable()
            ) {

                trackApplicationsFrame =
                        new TrackApplicationsFrame(user);
            }

            trackApplicationsFrame.setVisible(true);
            trackApplicationsFrame.toFront();
        });

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    // ==========================================
    // DASHBOARD CONTENT
    // ==========================================

    private void createContent() {

        // ==========================================
        // WELCOME AREA
        // ==========================================

        JPanel welcomePanel =
                new JPanel(
                        new BorderLayout()
                );

        welcomePanel.setOpaque(false);

        welcomePanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        84
                )
        );

        welcomePanel.setPreferredSize(
                new Dimension(
                        0,
                        84
                )
        );

        welcomePanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel welcomeText =
                new JPanel();

        welcomeText.setOpaque(false);

        welcomeText.setLayout(
                new BoxLayout(
                        welcomeText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome back, "
                                + capitalizeName(
                                user.getUsername()
                        )
                );

        welcomeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        welcomeLabel.setForeground(
                UITheme.TEXT
        );

        JLabel subtitle =
                new JLabel(
                        "Find jobs, manage your skills, and track your applications."
                );

        subtitle.setFont(
                UITheme.BODY
        );

        subtitle.setForeground(
                UITheme.TEXT_SECONDARY
        );

        welcomeText.add(
                welcomeLabel
        );

        welcomeText.add(
                Box.createVerticalStrut(
                        4
                )
        );

        welcomeText.add(
                subtitle
        );

        // ==========================================
        // DATE CARD
        // ==========================================

        RoundedPanel dateCard =
                new RoundedPanel(
                        14
                );

        dateCard.setBackground(
                UITheme.SURFACE
        );

        dateCard.setLayout(
                new BorderLayout(
                        10,
                        0
                )
        );

        dateCard.setPreferredSize(
                new Dimension(
                        175,
                        64
                )
        );

        dateCard.setMaximumSize(
                new Dimension(
                        175,
                        64
                )
        );

        dateCard.setBorder(
                new EmptyBorder(
                        10,
                        14,
                        10,
                        14
                )
        );

        JLabel calendarIcon =
                new JLabel(
                        "▣",
                        SwingConstants.CENTER
                );

        calendarIcon.setFont(
                new Font(
                        "Segoe UI Symbol",
                        Font.BOLD,
                        22
                )
        );

        calendarIcon.setForeground(
                UITheme.BLUE
        );

        calendarIcon.setPreferredSize(
                new Dimension(
                        28,
                        28
                )
        );

        JPanel dateText =
                new JPanel();

        dateText.setOpaque(false);

        dateText.setLayout(
                new BoxLayout(
                        dateText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel dateTitle =
                new JLabel(
                        "Today"
                );

        dateTitle.setFont(
                UITheme.SMALL
        );

        dateTitle.setForeground(
                UITheme.TEXT_SECONDARY
        );

        LocalDate today =
                LocalDate.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "MMM d, yyyy"
                );

        JLabel dateValue =
                new JLabel(
                        today.format(
                                formatter
                        )
                );

        dateValue.setFont(
                UITheme.LABEL
        );

        dateValue.setForeground(
                UITheme.TEXT
        );

        dateText.add(
                dateTitle
        );

        dateText.add(
                Box.createVerticalStrut(
                        3
                )
        );

        dateText.add(
                dateValue
        );

        dateCard.add(
                calendarIcon,
                BorderLayout.WEST
        );

        dateCard.add(
                dateText,
                BorderLayout.CENTER
        );

        welcomePanel.add(
                welcomeText,
                BorderLayout.WEST
        );

        welcomePanel.add(
                dateCard,
                BorderLayout.EAST
        );

        contentPanel.add(
                welcomePanel
        );

        contentPanel.add(
                Box.createVerticalStrut(
                        16
                )
        );

        // ==========================================
        // JOB SEEKER CONTAINER
        // ==========================================

        RoundedPanel container =
                new RoundedPanel(
                        22
                );

        container.setBackground(
                Color.WHITE
        );

        container.setLayout(
                new BorderLayout()
        );

        container.setBorder(
                new EmptyBorder(
                        20,
                        22,
                        22,
                        22
                )
        );

        container.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        container.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        365
                )
        );

        container.setPreferredSize(
                new Dimension(
                        0,
                        365
                )
        );

        JLabel title =
                UIComponents.createSectionTitle(
                        "Job Seeker Tools"
                );

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                14,
                                14
                        )
                );

        cards.setOpaque(false);

        cards.setBorder(
                new EmptyBorder(
                        14,
                        0,
                        0,
                        0
                )
        );

        // ==========================================
        // PROFILE CARD
        // ==========================================

        cards.add(
                UIComponents.createClickableActionCard(
                        "Manage Profile",
                        "Update your personal and contact information.",
                        "👤",
                        () -> {

                            if (
                                    jobSeekerProfileFrame == null
                                            || !jobSeekerProfileFrame.isDisplayable()
                            ) {

                                jobSeekerProfileFrame =
                                        new JobSeekerProfileFrame(
                                                user
                                        );
                            }

                            jobSeekerProfileFrame.setVisible(true);
                            jobSeekerProfileFrame.toFront();
                        }
                )
        );

        // ==========================================
        // SKILLS CARD
        // ==========================================

        cards.add(
                UIComponents.createClickableActionCard(
                        "Manage Skills",
                        "Add and update the skills used for job matching.",
                        "⚙",
                        () -> {

                            if (
                                    jobSeekerSkillsFrame == null
                                            || !jobSeekerSkillsFrame.isDisplayable()
                            ) {

                                jobSeekerSkillsFrame =
                                        new JobSeekerSkillsFrame(
                                                user
                                        );
                            }

                            jobSeekerSkillsFrame.setVisible(true);
                            jobSeekerSkillsFrame.toFront();
                        }
                )
        );

        // ==========================================
        // BROWSE JOBS CARD
        // ==========================================

        cards.add(
                UIComponents.createClickableActionCard(
                        "Browse Jobs",
                        "Explore available jobs and view your match scores.",
                        "▣",
                        () -> {

                            if (
                                    browseJobsFrame == null
                                            || !browseJobsFrame.isDisplayable()
                            ) {

                                browseJobsFrame =
                                        new BrowseJobsFrame(
                                                user
                                        );
                            }

                            browseJobsFrame.setVisible(true);
                            browseJobsFrame.toFront();
                        }
                )
        );

        // ==========================================
        // APPLICATIONS CARD
        // ==========================================

        cards.add(
                UIComponents.createClickableActionCard(
                        "Track Applications",
                        "Monitor the status of your submitted applications.",
                        "☑",
                        () -> {

                            if (
                                    trackApplicationsFrame == null
                                            || !trackApplicationsFrame.isDisplayable()
                            ) {

                                trackApplicationsFrame =
                                        new TrackApplicationsFrame(
                                                user
                                        );
                            }

                            trackApplicationsFrame.setVisible(true);
                            trackApplicationsFrame.toFront();
                        }
                )
        );

        container.add(
                title,
                BorderLayout.NORTH
        );

        container.add(
                cards,
                BorderLayout.CENTER
        );

        contentPanel.add(
                container
        );
    }

    // ==========================================
    // CAPITALIZE USERNAME
    // ==========================================

    private static String capitalizeName(
            String name
    ) {

        if (
                name == null
                        || name.trim().isEmpty()
        ) {
            return "";
        }

        String[] words =
                name.trim()
                        .toLowerCase()
                        .split("\\s+");

        StringBuilder result =
                new StringBuilder();

        for (String word : words) {

            if (!word.isEmpty()) {

                result.append(
                        Character.toUpperCase(
                                word.charAt(0)
                        )
                );

                if (word.length() > 1) {

                    result.append(
                            word.substring(1)
                    );
                }

                result.append(" ");
            }
        }

        return result
                .toString()
                .trim();
    }

    // ==========================================
    // LOGOUT
    // ==========================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (
                choice
                        == JOptionPane.YES_OPTION
        ) {

            dispose();

            new LoginFrame()
                    .setVisible(true);
        }
    }
}