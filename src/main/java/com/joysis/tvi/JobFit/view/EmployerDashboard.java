package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployerDashboard extends BaseDashboardFrame {

    private final User user;

    private EmployerProfileFrame employerProfileFrame;
    private PostJobFrame postJobFrame;
    private ManageJobsFrame manageJobsFrame;
    private ViewApplicantsFrame viewApplicantsFrame;
    private UpdateApplicationStatusFrame updateApplicationStatusFrame;
    private ManageRequiredSkillsFrame manageRequiredSkillsFrame;

    public EmployerDashboard(User user) {

        super(
                "JobFit - Employer Dashboard",
                capitalizeName(user.getUsername()),
                "Employer Panel"
        );

        this.user = user;

        createMenu();
        createContent();
    }

    private void createMenu() {

        addSidebarButton(
                "Dashboard",
                true
        );

        JButton profileButton =
                addSidebarButton(
                        "Company Profile",
                        false
                );

        JButton postJobButton =
                addSidebarButton(
                        "Post Job",
                        false
                );

        JButton manageJobsButton =
                addSidebarButton(
                        "Manage Jobs",
                        false
                );

        JButton applicantsButton =
                addSidebarButton(
                        "Applicants",
                        false
                );

        JButton statusButton =
                addSidebarButton(
                        "Application Status",
                        false
                );

        JButton skillsButton =
                addSidebarButton(
                        "Required Skills",
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

        profileButton.addActionListener(
                e -> openProfile()
        );

        postJobButton.addActionListener(
                e -> openPostJob()
        );

        manageJobsButton.addActionListener(
                e -> openManageJobs()
        );

        applicantsButton.addActionListener(
                e -> openApplicants()
        );

        statusButton.addActionListener(
                e -> openStatus()
        );

        skillsButton.addActionListener(
                e -> openRequiredSkills()
        );

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    private void createContent() {

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
                        "Manage your company profile, job posts and applicants."
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
                BorderFactory.createEmptyBorder(
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

        JLabel dateValue =
                new JLabel(
                        LocalDate.now().format(
                                DateTimeFormatter.ofPattern(
                                        "MMM d, yyyy"
                                )
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
                BorderFactory.createEmptyBorder(
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
                        500
                )
        );

        container.setPreferredSize(
                new Dimension(
                        0,
                        500
                )
        );

        JLabel title =
                UIComponents.createSectionTitle(
                        "Employer Management"
                );

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                14,
                                14
                        )
                );

        cards.setOpaque(false);

        cards.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        0,
                        0,
                        0
                )
        );

        cards.add(
                UIComponents.createClickableActionCard(
                        "Manage Company Profile",
                        "Update your company information and details.",
                        "▦",
                        this::openProfile
                )
        );

        cards.add(
                UIComponents.createClickableActionCard(
                        "Post Job",
                        "Create and publish a new job posting.",
                        "▤",
                        this::openPostJob
                )
        );

        cards.add(
                UIComponents.createClickableActionCard(
                        "Manage Job Posts",
                        "View and manage your existing job postings.",
                        "▣",
                        this::openManageJobs
                )
        );

        cards.add(
                UIComponents.createClickableActionCard(
                        "View Applicants",
                        "Review applicants and application details.",
                        "👥",
                        this::openApplicants
                )
        );

        cards.add(
                UIComponents.createClickableActionCard(
                        "Update Application Status",
                        "Update applicant status throughout hiring.",
                        "✓",
                        this::openStatus
                )
        );

        cards.add(
                UIComponents.createClickableActionCard(
                        "Manage Required Skills",
                        "Set required skills for your job postings.",
                        "⚙",
                        this::openRequiredSkills
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

    private void openProfile() {

        if (
                employerProfileFrame == null
                        || !employerProfileFrame.isDisplayable()
        ) {

            employerProfileFrame =
                    new EmployerProfileFrame(
                            user
                    );
        }

        employerProfileFrame.setVisible(true);
        employerProfileFrame.toFront();
    }

    private void openPostJob() {

        if (
                postJobFrame == null
                        || !postJobFrame.isDisplayable()
        ) {

            postJobFrame =
                    new PostJobFrame(
                            user
                    );
        }

        postJobFrame.setVisible(true);
        postJobFrame.toFront();
    }

    private void openManageJobs() {

        if (
                manageJobsFrame == null
                        || !manageJobsFrame.isDisplayable()
        ) {

            manageJobsFrame =
                    new ManageJobsFrame(
                            user
                    );
        }

        manageJobsFrame.setVisible(true);
        manageJobsFrame.toFront();
    }

    private void openApplicants() {

        if (
                viewApplicantsFrame == null
                        || !viewApplicantsFrame.isDisplayable()
        ) {

            viewApplicantsFrame =
                    new ViewApplicantsFrame(
                            user
                    );
        }

        viewApplicantsFrame.setVisible(true);
        viewApplicantsFrame.toFront();
    }

    private void openStatus() {

        if (
                updateApplicationStatusFrame == null
                        || !updateApplicationStatusFrame.isDisplayable()
        ) {

            updateApplicationStatusFrame =
                    new UpdateApplicationStatusFrame(
                            user
                    );
        }

        updateApplicationStatusFrame.setVisible(true);
        updateApplicationStatusFrame.toFront();
    }

    private void openRequiredSkills() {

        if (
                manageRequiredSkillsFrame == null
                        || !manageRequiredSkillsFrame.isDisplayable()
        ) {

            manageRequiredSkillsFrame =
                    new ManageRequiredSkillsFrame(
                            user
                    );
        }

        manageRequiredSkillsFrame.setVisible(true);
        manageRequiredSkillsFrame.toFront();
    }

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