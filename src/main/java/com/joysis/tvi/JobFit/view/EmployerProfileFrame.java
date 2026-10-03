package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.EmployerController;
import com.joysis.tvi.JobFit.controller.UserController;
import com.joysis.tvi.JobFit.model.Employer;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EmployerProfileFrame extends JFrame {

    private final User user;
    private final EmployerController employerController;
    private final UserController userController;

    private JTextField companyNameField;
    private JTextField emailField;
    private JTextField phoneField;

    private String originalCompanyName;
    private String originalEmail;
    private String originalPhone;

    public EmployerProfileFrame(User user) {

        this.user = user;
        this.employerController = new EmployerController();
        this.userController = new UserController();

        setTitle("JobFit - Company Profile");
        setSize(860, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadProfile();
    }

    private void createGUI() {

        // ==========================================
        // MAIN PANEL
        // ==========================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );

        // ==========================================
        // LEFT PROFILE AREA
        // ==========================================

        JPanel leftPanel =
                new JPanel();

        leftPanel.setPreferredSize(
                new Dimension(
                        240,
                        0
                )
        );

        leftPanel.setBackground(
                UITheme.NAVY
        );

        leftPanel.setLayout(
                new BoxLayout(
                        leftPanel,
                        BoxLayout.Y_AXIS
                )
        );

        leftPanel.setBorder(
                new EmptyBorder(
                        40,
                        24,
                        40,
                        24
                )
        );

        JPanel profileBlock =
                new JPanel();

        profileBlock.setOpaque(false);

        profileBlock.setLayout(
                new BoxLayout(
                        profileBlock,
                        BoxLayout.Y_AXIS
                )
        );

        profileBlock.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        profileBlock.setMaximumSize(
                new Dimension(
                        200,
                        300
                )
        );

        // Employer Icon
        JPanel avatar =
                new JPanel() {

                    @Override
                    protected void paintComponent(Graphics g) {

                        super.paintComponent(g);

                        Graphics2D g2 =
                                (Graphics2D) g.create();

                        g2.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON
                        );

                        int size =
                                Math.min(
                                        getWidth(),
                                        getHeight()
                                );

                        g2.setColor(
                                UITheme.BLUE
                        );

                        g2.fillOval(
                                0,
                                0,
                                size,
                                size
                        );

                        g2.setColor(
                                Color.WHITE
                        );

                        g2.setFont(
                                new Font(
                                        "Segoe UI",
                                        Font.BOLD,
                                        28
                                )
                        );

                        String letter =
                                user.getUsername()
                                        .substring(0, 1)
                                        .toUpperCase();

                        FontMetrics fm =
                                g2.getFontMetrics();

                        int x =
                                (size - fm.stringWidth(letter)) / 2;

                        int y =
                                ((size - fm.getHeight()) / 2)
                                        + fm.getAscent();

                        g2.drawString(
                                letter,
                                x,
                                y
                        );

                        g2.dispose();
                    }
                };

        avatar.setOpaque(false);

        avatar.setPreferredSize(
                new Dimension(
                        90,
                        90
                )
        );

        avatar.setMaximumSize(
                new Dimension(
                        90,
                        90
                )
        );

        avatar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel employerLabel =
                new JLabel(
                        "EMPLOYER",
                        SwingConstants.CENTER
                );

        employerLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        employerLabel.setForeground(
                Color.WHITE
        );

        employerLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        employerLabel.setMaximumSize(
                new Dimension(
                        200,
                        28
                )
        );

        JLabel roleLabel =
                new JLabel(
                        "Company Account",
                        SwingConstants.CENTER
                );

        roleLabel.setFont(
                UITheme.BODY
        );

        roleLabel.setForeground(
                new Color(
                        185,
                        210,
                        235
                )
        );

        roleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        roleLabel.setMaximumSize(
                new Dimension(
                        200,
                        24
                )
        );

        JLabel infoLabel =
                new JLabel(
                        "<html><div style='text-align:center;'>"
                                + "Manage your company<br>"
                                + "profile information"
                                + "</div></html>",
                        SwingConstants.CENTER
                );

        infoLabel.setFont(
                UITheme.SMALL
        );

        infoLabel.setForeground(
                new Color(
                        160,
                        190,
                        220
                )
        );

        infoLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        infoLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        infoLabel.setMaximumSize(
                new Dimension(
                        200,
                        50
                )
        );

        profileBlock.add(
                avatar
        );

        profileBlock.add(
                Box.createVerticalStrut(
                        20
                )
        );

        profileBlock.add(
                employerLabel
        );

        profileBlock.add(
                Box.createVerticalStrut(
                        5
                )
        );

        profileBlock.add(
                roleLabel
        );

        profileBlock.add(
                Box.createVerticalStrut(
                        18
                )
        );

        profileBlock.add(
                infoLabel
        );

        leftPanel.add(
                Box.createVerticalGlue()
        );

        leftPanel.add(
                profileBlock
        );

        leftPanel.add(
                Box.createVerticalGlue()
        );

        // ==========================================
        // RIGHT CONTENT AREA
        // ==========================================

        JPanel rightPanel =
                new JPanel(
                        new BorderLayout()
                );

        rightPanel.setBackground(
                UITheme.BACKGROUND
        );

        rightPanel.setBorder(
                new EmptyBorder(
                        34,
                        40,
                        34,
                        40
                )
        );

        // ==========================================
        // HEADER
        // ==========================================

        JPanel headerPanel =
                new JPanel();

        headerPanel.setOpaque(false);

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "Company Profile"
                );

        titleLabel.setFont(
                UITheme.PAGE_TITLE
        );

        titleLabel.setForeground(
                UITheme.TEXT
        );

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Update your company and contact information."
                );

        subtitleLabel.setFont(
                UITheme.BODY
        );

        subtitleLabel.setForeground(
                UITheme.TEXT_SECONDARY
        );

        subtitleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        headerPanel.add(
                titleLabel
        );

        headerPanel.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        headerPanel.add(
                subtitleLabel
        );

        // ==========================================
        // FORM CARD
        // ==========================================

        RoundedPanel formCard =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        formCard.setBackground(
                UITheme.SURFACE
        );

        formCard.setLayout(
                new GridBagLayout()
        );

        formCard.setBorder(
                new EmptyBorder(
                        24,
                        26,
                        24,
                        26
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        // Username
        JLabel usernameLabel =
                createFieldLabel(
                        "Username"
                );

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        6,
                        0
                );

        formCard.add(
                usernameLabel,
                gbc
        );

        JTextField usernameField =
                new JTextField(
                        user.getUsername()
                );

        usernameField.setEditable(
                false
        );

        styleReadOnlyField(
                usernameField
        );

        gbc.gridy = 1;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        14,
                        0
                );

        formCard.add(
                usernameField,
                gbc
        );

        // Company Name
        JLabel companyLabel =
                createFieldLabel(
                        "Company Name"
                );

        gbc.gridy = 2;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        6,
                        0
                );

        formCard.add(
                companyLabel,
                gbc
        );

        companyNameField =
                new JTextField();

        styleField(
                companyNameField
        );

        gbc.gridy = 3;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        14,
                        0
                );

        formCard.add(
                companyNameField,
                gbc
        );

        // Email
        JLabel emailLabel =
                createFieldLabel(
                        "Email Address"
                );

        gbc.gridy = 4;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        6,
                        0
                );

        formCard.add(
                emailLabel,
                gbc
        );

        emailField =
                new JTextField();

        styleField(
                emailField
        );

        gbc.gridy = 5;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        14,
                        0
                );

        formCard.add(
                emailField,
                gbc
        );

        // Phone
        JLabel phoneLabel =
                createFieldLabel(
                        "Phone Number"
                );

        gbc.gridy = 6;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        6,
                        0
                );

        formCard.add(
                phoneLabel,
                gbc
        );

        phoneField =
                new JTextField();

        styleField(
                phoneField
        );

        gbc.gridy = 7;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        0,
                        0
                );

        formCard.add(
                phoneField,
                gbc
        );

        // ==========================================
        // BUTTONS
        // ==========================================

        JButton updateButton =
                new RoundedButton(
                        "Update Profile",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        JButton changePasswordButton =
                new RoundedButton(
                        "Change Password",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        JButton backButton =
                new JButton(
                        "Back"
                );

        styleBackButton(
                backButton
        );

        updateButton.setPreferredSize(
                new Dimension(
                        145,
                        42
                )
        );

        changePasswordButton.setPreferredSize(
                new Dimension(
                        165,
                        42
                )
        );

        backButton.setPreferredSize(
                new Dimension(
                        105,
                        42
                )
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(
                updateButton
        );

        buttonPanel.add(
                changePasswordButton
        );

        buttonPanel.add(
                backButton
        );

        // ==========================================
        // CONTENT WRAPPER
        // ==========================================

        JPanel contentWrapper =
                new JPanel(
                        new BorderLayout()
                );

        contentWrapper.setOpaque(false);

        contentWrapper.setBorder(
                new EmptyBorder(
                        22,
                        0,
                        16,
                        0
                )
        );

        contentWrapper.add(
                formCard,
                BorderLayout.CENTER
        );

        rightPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        rightPanel.add(
                contentWrapper,
                BorderLayout.CENTER
        );

        rightPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // FINAL LAYOUT
        // ==========================================

        mainPanel.add(
                leftPanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                rightPanel,
                BorderLayout.CENTER
        );

        setContentPane(
                mainPanel
        );

        // ==========================================
        // ACTIONS
        // ==========================================

        updateButton.addActionListener(
                e -> updateProfile()
        );

        changePasswordButton.addActionListener(
                e -> openChangePasswordDialog()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // ==========================================
    // FIELD LABEL
    // ==========================================

    private JLabel createFieldLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                UITheme.LABEL
        );

        label.setForeground(
                UITheme.TEXT
        );

        return label;
    }

    // ==========================================
    // NORMAL FIELD
    // ==========================================

    private void styleField(
            JTextField field
    ) {

        field.setFont(
                UITheme.BODY
        );

        field.setForeground(
                UITheme.TEXT
        );

        field.setBackground(
                Color.WHITE
        );

        field.setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER_BLUE
                        ),
                        new EmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );
    }

    // ==========================================
    // READ ONLY FIELD
    // ==========================================

    private void styleReadOnlyField(
            JTextField field
    ) {

        field.setFont(
                UITheme.BODY
        );

        field.setForeground(
                UITheme.TEXT_SECONDARY
        );

        field.setBackground(
                new Color(
                        239,
                        244,
                        250
                )
        );

        field.setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        new EmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );
    }

    // ==========================================
    // BACK BUTTON
    // ==========================================

    private void styleBackButton(
            JButton button
    ) {

        button.setFont(
                UITheme.BUTTON
        );

        button.setForeground(
                UITheme.NAVY
        );

        button.setBackground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER_BLUE
                )
        );
    }

    // ==========================================
    // LOAD PROFILE
    // ==========================================

    private void loadProfile() {

        Employer employer =
                employerController.getProfile(
                        user.getId()
                );

        if (employer != null) {

            originalCompanyName =
                    employer.getCompanyName();

            originalEmail =
                    employer.getEmail();

            originalPhone =
                    employer.getPhone();

            companyNameField.setText(
                    originalCompanyName
            );

            emailField.setText(
                    originalEmail
            );

            phoneField.setText(
                    originalPhone
            );

        } else {

            originalCompanyName = "";
            originalEmail = "";
            originalPhone = "";

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load company profile.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // UPDATE PROFILE
    // ==========================================

    private void updateProfile() {

        String companyName =
                companyNameField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();

        if (
                companyName.equals(
                        originalCompanyName == null
                                ? ""
                                : originalCompanyName
                )
                        &&
                        email.equals(
                                originalEmail == null
                                        ? ""
                                        : originalEmail
                        )
                        &&
                        phone.equals(
                                originalPhone == null
                                        ? ""
                                        : originalPhone
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No changes to save.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        if (
                companyName.isEmpty()
                        || email.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Company Name and Email are required.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Employer employer =
                new Employer(
                        0,
                        user.getId(),
                        companyName,
                        email,
                        phone
                );

        String error =
                employerController.updateProfile(
                        employer
                );

        if (error == null) {

            originalCompanyName =
                    companyName;

            originalEmail =
                    email;

            originalPhone =
                    phone;

            JOptionPane.showMessageDialog(
                    this,
                    "Company profile updated successfully!",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    error,
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // CHANGE PASSWORD
    // ==========================================

    private void openChangePasswordDialog() {

        JPasswordField currentField =
                new JPasswordField();

        JPasswordField newField =
                new JPasswordField();

        JPasswordField confirmField =
                new JPasswordField();

        stylePasswordField(
                currentField
        );

        stylePasswordField(
                newField
        );

        stylePasswordField(
                confirmField
        );

        JPanel passwordPanel =
                new JPanel(
                        new GridLayout(
                                6,
                                1,
                                0,
                                7
                        )
                );

        passwordPanel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        passwordPanel.add(
                new JLabel(
                        "Current Password"
                )
        );

        passwordPanel.add(
                currentField
        );

        passwordPanel.add(
                new JLabel(
                        "New Password"
                )
        );

        passwordPanel.add(
                newField
        );

        passwordPanel.add(
                new JLabel(
                        "Confirm New Password"
                )
        );

        passwordPanel.add(
                confirmField
        );

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        passwordPanel,
                        "Change Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (
                option
                        != JOptionPane.OK_OPTION
        ) {
            return;
        }

        String current =
                new String(
                        currentField.getPassword()
                );

        String newPass =
                new String(
                        newField.getPassword()
                );

        String confirm =
                new String(
                        confirmField.getPassword()
                );

        String error =
                userController.changePassword(
                        user.getId(),
                        current,
                        newPass,
                        confirm
                );

        if (error == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password changed successfully!",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    error,
                    "Change Password Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // PASSWORD FIELD
    // ==========================================

    private void stylePasswordField(
            JPasswordField field
    ) {

        field.setPreferredSize(
                new Dimension(
                        300,
                        38
                )
        );

        field.setFont(
                UITheme.BODY
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER_BLUE
                        ),
                        new EmptyBorder(
                                7,
                                10,
                                7,
                                10
                        )
                )
        );
    }
}