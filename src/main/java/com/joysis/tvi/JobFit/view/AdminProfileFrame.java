package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.AdminController;
import com.joysis.tvi.JobFit.controller.UserController;
import com.joysis.tvi.JobFit.model.Admin;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminProfileFrame extends JFrame {

    private final User user;
    private final AdminController adminController;
    private final UserController userController;

    private JTextField emailField;
    private JTextField phoneField;

    private String originalEmail;
    private String originalPhone;

    public AdminProfileFrame(User user) {

        this.user = user;
        this.adminController = new AdminController();
        this.userController = new UserController();

        setTitle("JobFit - Admin Profile");
        setSize(820, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadProfile();
    }

    private void createGUI() {

        // ==========================================
        // MAIN CONTAINER
        // ==========================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );

        // ==========================================
        // LEFT PROFILE PANEL
        // ==========================================

        JPanel leftPanel = new JPanel();

        leftPanel.setPreferredSize(new Dimension(
                        240,
                        0));

        leftPanel.setBackground(UITheme.NAVY);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(
                new EmptyBorder(
                        48,
                        28,
                        40,
                        28));


// CENTER PROFILE CONTENT

        JPanel profileInfoPanel = new JPanel();
        profileInfoPanel.setOpaque(false);

        profileInfoPanel.setLayout(new BoxLayout(
                        profileInfoPanel,
                        BoxLayout.Y_AXIS));

        profileInfoPanel.setMaximumSize(new Dimension(
                        200,
                        280));

        profileInfoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

// AVATAR
        JPanel avatar = new JPanel() {

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        int size = Math.min(
                                getWidth(),
                                getHeight());
                        g2.setColor(UITheme.BLUE);
                        g2.fillOval(
                                0,
                                0,
                                size,
                                size);

                        g2.setColor(Color.WHITE);

                        g2.setFont(new Font("Segoe UI", Font.BOLD, 28));

                        String letter = user.getUsername()
                                        .substring(0, 1)
                                        .toUpperCase();

                        FontMetrics fm = g2.getFontMetrics();

                        int x = (size - fm.stringWidth(letter)) / 2;

                        int y = ((size - fm.getHeight()) / 2)
                                        + fm.getAscent();

                        g2.drawString(letter, x, y);
                        g2.dispose();
                    }
                };

        avatar.setOpaque(false);

        avatar.setPreferredSize(
                new Dimension(90, 90));

        avatar.setMaximumSize(
                new Dimension(90, 90));

        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

// ADMIN
        JLabel adminLabel = new JLabel("ADMIN", SwingConstants.CENTER);

        adminLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        adminLabel.setForeground(Color.WHITE
        );

        adminLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        adminLabel.setMaximumSize(
                new Dimension(
                        200,
                        28
                )
        );

// ROLE
        JLabel roleLabel =
                new JLabel(
                        "Administrator",
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

// DESCRIPTION
        JLabel infoLabel =
                new JLabel(
                        "<html><div style='text-align:center;'>"
                                + "Manage your account<br>"
                                + "information"
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

// BUILD PROFILE BLOCK
        profileInfoPanel.add(avatar);

        profileInfoPanel.add(
                Box.createVerticalStrut(
                        22
                )
        );

        profileInfoPanel.add(adminLabel);

        profileInfoPanel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        profileInfoPanel.add(roleLabel);

        profileInfoPanel.add(
                Box.createVerticalStrut(
                        18
                )
        );

        profileInfoPanel.add(infoLabel);

// ADD TO LEFT PANEL
        leftPanel.add(
                Box.createVerticalGlue()
        );

        leftPanel.add(
                profileInfoPanel
        );

        leftPanel.add(
                Box.createVerticalGlue()
        );

        // ==========================================
        // RIGHT CONTENT PANEL
        // ==========================================

        JPanel rightPanel = new JPanel(new BorderLayout());

        rightPanel.setBackground(UITheme.BACKGROUND);

        rightPanel.setBorder(
                new EmptyBorder(
                        38,
                        42,
                        36,
                        42
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
                        "Profile Information"
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
                        "Update your admin account information."
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
        // PROFILE CARD
        // ==========================================

        RoundedPanel profileCard =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        profileCard.setBackground(
                UITheme.SURFACE
        );

        profileCard.setLayout(
                new GridBagLayout()
        );

        profileCard.setBorder(
                new EmptyBorder(
                        28,
                        28,
                        28,
                        28
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        // ==========================================
        // USERNAME
        // ==========================================

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
                        7,
                        0
                );

        profileCard.add(
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
                        18,
                        0
                );

        profileCard.add(
                usernameField,
                gbc
        );

        // ==========================================
        // EMAIL
        // ==========================================

        JLabel emailLabel =
                createFieldLabel(
                        "Email Address"
                );

        gbc.gridy = 2;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        7,
                        0
                );

        profileCard.add(
                emailLabel,
                gbc
        );

        emailField =
                new JTextField();

        styleField(
                emailField
        );

        gbc.gridy = 3;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        18,
                        0
                );

        profileCard.add(
                emailField,
                gbc
        );

        // ==========================================
        // PHONE
        // ==========================================

        JLabel phoneLabel =
                createFieldLabel(
                        "Phone Number"
                );

        gbc.gridy = 4;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        7,
                        0
                );

        profileCard.add(
                phoneLabel,
                gbc
        );

        phoneField =
                new JTextField();

        styleField(
                phoneField
        );

        gbc.gridy = 5;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        0,
                        0
                );

        profileCard.add(
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

        Dimension mainButtonSize =
                new Dimension(
                        145,
                        42
                );

        updateButton.setPreferredSize(
                mainButtonSize
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

        buttonPanel.setOpaque(
                false
        );

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

        contentWrapper.setOpaque(
                false
        );

        contentWrapper.setBorder(
                new EmptyBorder(
                        24,
                        0,
                        18,
                        0
                )
        );

        contentWrapper.add(
                profileCard,
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
    // CREATE LABEL
    // ==========================================

    private JLabel createFieldLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                UITheme.LABEL
        );

        label.setForeground(
                UITheme.TEXT
        );

        return label;
    }

    // ==========================================
    // NORMAL FIELD STYLE
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
    // READ ONLY FIELD STYLE
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
    // BACK BUTTON STYLE
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

        button.setFocusPainted(
                false
        );

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

        Admin admin =
                adminController.getProfile(
                        user.getId()
                );

        if (admin != null) {

            originalEmail =
                    admin.getEmail();

            originalPhone =
                    admin.getPhone();

            emailField.setText(
                    originalEmail
            );

            phoneField.setText(
                    originalPhone
            );

        } else {

            originalEmail = "";
            originalPhone = "";

            emailField.setText("");
            phoneField.setText("");
        }
    }

    // ==========================================
    // UPDATE PROFILE
    // ==========================================

    private void updateProfile() {

        String email =
                emailField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();

        if (
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

        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Email is required.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String error =
                adminController.saveProfile(
                        user.getId(),
                        email,
                        phone
                );

        if (error == null) {

            originalEmail =
                    email;

            originalPhone =
                    phone;

            JOptionPane.showMessageDialog(
                    this,
                    "Admin profile saved successfully!",
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
    // PASSWORD FIELD STYLE
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