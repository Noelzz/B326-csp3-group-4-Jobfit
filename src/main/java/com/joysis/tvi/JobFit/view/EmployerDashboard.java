package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.RoundedButton;
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

    private EmployerProfileFrame employerProfileFrame;
    private PostJobFrame postJobFrame;
    private ManageJobsFrame manageJobsFrame;
    private ViewApplicantsFrame viewApplicantsFrame;
    private UpdateApplicationStatusFrame updateApplicationStatusFrame;
    private ManageRequiredSkillsFrame manageRequiredSkillsFrame;

    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(35, 40, 48);
    private final Color SECONDARY_TEXT = new Color(110, 118, 130);
    private final Color BUTTON_COLOR = new Color(45, 95, 170);
    private final Color BUTTON_HOVER = new Color(35, 78, 145);
    private final Color LOGOUT_COLOR = new Color(220, 70, 70);
    private final Color LOGOUT_HOVER = new Color(195, 55, 55);

    public EmployerDashboard(User user) {

        this.user = user;

        setTitle("JobFit - Employer Dashboard");
        setSize(760, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(25, 35, 25, 35));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel("JOBFIT");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(BUTTON_COLOR);

        JLabel dashboardLabel = new JLabel("EMPLOYER DASHBOARD");
        dashboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dashboardLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        dashboardLabel.setForeground(TEXT_COLOR);

        JLabel welcomeLabel = new JLabel("Welcome back, " + user.getUsername());
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(SECONDARY_TEXT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(3));
        headerPanel.add(dashboardLabel);
        headerPanel.add(Box.createVerticalStrut(6));
        headerPanel.add(welcomeLabel);

        // Card
        JPanel cardPanel = new JPanel(new BorderLayout(0, 20));
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel sectionLabel = new JLabel("Employer Management");
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionLabel.setForeground(TEXT_COLOR);

        JPanel cardHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardHeader.setBackground(CARD_COLOR);
        cardHeader.add(sectionLabel);

        // Button grid
        JPanel buttonGrid = new JPanel(new GridBagLayout());
        buttonGrid.setBackground(CARD_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        profileButton = createMenuButton("Manage Company Profile");
        postJobButton = createMenuButton("Post Job");
        manageJobsButton = createMenuButton("Manage Job Posts");
        applicantsButton = createMenuButton("View Applicants");
        updateStatusButton = createMenuButton("Update Application Status");
        requiredSkillsButton = createMenuButton("Manage Required Skills");

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonGrid.add(profileButton, gbc);

        gbc.gridx = 1;
        buttonGrid.add(postJobButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonGrid.add(manageJobsButton, gbc);

        gbc.gridx = 1;
        buttonGrid.add(applicantsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonGrid.add(updateStatusButton, gbc);

        gbc.gridx = 1;
        buttonGrid.add(requiredSkillsButton, gbc);

        cardPanel.add(cardHeader, BorderLayout.NORTH);
        cardPanel.add(buttonGrid, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(BACKGROUND);

        logoutButton = new RoundedButton("Logout", LOGOUT_COLOR, LOGOUT_HOVER);
        logoutButton.setPreferredSize(new Dimension(120, 36));

        bottomPanel.add(logoutButton);

        // Center wrapper
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(BACKGROUND);
        centerWrapper.setBorder(new EmptyBorder(25, 0, 10, 0));
        centerWrapper.add(cardPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Button actions
        profileButton.addActionListener(e -> {
            if (employerProfileFrame == null || !employerProfileFrame.isDisplayable()) {
                employerProfileFrame = new EmployerProfileFrame(user);
            }
            employerProfileFrame.toFront();
            employerProfileFrame.setVisible(true);
        });

        postJobButton.addActionListener(e -> {
            if (postJobFrame == null || !postJobFrame.isDisplayable()) {
                postJobFrame = new PostJobFrame(user);
            }
            postJobFrame.toFront();
            postJobFrame.setVisible(true);
        });

        manageJobsButton.addActionListener(e -> {
            if (manageJobsFrame == null || !manageJobsFrame.isDisplayable()) {
                manageJobsFrame = new ManageJobsFrame(user);
            }
            manageJobsFrame.toFront();
            manageJobsFrame.setVisible(true);
        });

        applicantsButton.addActionListener(e -> {
            if (viewApplicantsFrame == null || !viewApplicantsFrame.isDisplayable()) {
                viewApplicantsFrame = new ViewApplicantsFrame(user);
            }
            viewApplicantsFrame.toFront();
            viewApplicantsFrame.setVisible(true);
        });

        updateStatusButton.addActionListener(e -> {
            if (updateApplicationStatusFrame == null || !updateApplicationStatusFrame.isDisplayable()) {
                updateApplicationStatusFrame = new UpdateApplicationStatusFrame(user);
            }
            updateApplicationStatusFrame.toFront();
            updateApplicationStatusFrame.setVisible(true);
        });

        requiredSkillsButton.addActionListener(e -> {
            if (manageRequiredSkillsFrame == null || !manageRequiredSkillsFrame.isDisplayable()) {
                manageRequiredSkillsFrame = new ManageRequiredSkillsFrame(user);
            }
            manageRequiredSkillsFrame.toFront();
            manageRequiredSkillsFrame.setVisible(true);
        });

        logoutButton.addActionListener(e -> logout());
    }

    private JButton createMenuButton(String text) {

        RoundedButton button = new RoundedButton(text, BUTTON_COLOR, BUTTON_HOVER);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(250, 55));
        button.setMinimumSize(new Dimension(250, 55));
        button.setMaximumSize(new Dimension(250, 55));

        return button;
    }

    private void logout() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
}