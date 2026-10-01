package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.RoundedButton;
import com.joysis.tvi.JobFit.controller.RegisterController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;

    private JLabel nameLabel;

    private JButton registerButton;
    private JButton backButton;

    private final RegisterController registerController;

    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(35, 40, 48);
    private final Color SECONDARY_TEXT = new Color(110, 118, 130);
    private final Color BLUE = new Color(45, 95, 170);
    private final Color BLUE_HOVER = new Color(35, 78, 145);
    private final Color BORDER_COLOR = new Color(220, 224, 230);

    public RegisterFrame() {

        this.registerController = new RegisterController();

        setTitle("JobFit - Register");
        setSize(520, 760);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(25, 45, 25, 45));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND);

        JLabel logoLabel = new JLabel("JOBFIT");
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(BLUE);

        JLabel subtitleLabel = new JLabel("Create your account");
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(SECONDARY_TEXT);

        headerPanel.add(logoLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        // Card
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel titleLabel = new JLabel("REGISTER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_COLOR);

        JLabel titleSubLabel = new JLabel("Fill in your details to get started", SwingConstants.CENTER);
        titleSubLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleSubLabel.setForeground(SECONDARY_TEXT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(CARD_COLOR);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(titleSubLabel);

        // Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(CARD_COLOR);

        JLabel usernameLabel = createLabel("Username");
        usernameField = new JTextField();
        styleTextField(usernameField);

        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(12));

        JLabel passwordLabel = createLabel("Password");
        passwordField = new JPasswordField();
        styleTextField(passwordField);

        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(12));

        JLabel roleLabel = createLabel("Role");
        roleComboBox = new JComboBox<>(new String[]{"Job Seeker", "Employer"});
        styleComboBox(roleComboBox);

        formPanel.add(roleLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(roleComboBox);
        formPanel.add(Box.createVerticalStrut(12));

        nameLabel = createLabel("Full Name");
        nameField = new JTextField();
        styleTextField(nameField);

        formPanel.add(nameLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(12));

        JLabel emailLabel = createLabel("Email");
        emailField = new JTextField();
        styleTextField(emailField);

        formPanel.add(emailLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(12));

        JLabel phoneLabel = createLabel("Phone");
        phoneField = new JTextField();
        styleTextField(phoneField);

        formPanel.add(phoneLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(phoneField);
        formPanel.add(Box.createVerticalStrut(20));

        registerButton = new RoundedButton("REGISTER", BLUE, BLUE_HOVER);
        registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        registerButton.setMinimumSize(new Dimension(0, 42));
        formPanel.add(registerButton);

        formPanel.add(Box.createVerticalStrut(8));

        backButton = new RoundedButton("BACK TO LOGIN", CARD_COLOR, new Color(240, 245, 253));
        backButton.setForeground(BLUE);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        backButton.setMinimumSize(new Dimension(0, 38));
        formPanel.add(backButton);

        // Card content
        JPanel cardContent = new JPanel(new BorderLayout());
        cardContent.setBackground(CARD_COLOR);
        cardContent.add(titlePanel, BorderLayout.NORTH);

        JPanel cardCenter = new JPanel(new BorderLayout());
        cardCenter.setBackground(CARD_COLOR);
        cardCenter.setBorder(new EmptyBorder(15, 0, 0, 0));
        cardCenter.add(formPanel, BorderLayout.CENTER);

        cardContent.add(cardCenter, BorderLayout.CENTER);
        cardPanel.add(cardContent, BorderLayout.CENTER);

        // Center wrapper
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(BACKGROUND);
        centerWrapper.setBorder(new EmptyBorder(15, 0, 15, 0));
        centerWrapper.add(cardPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        add(mainPanel);

        // Actions
        roleComboBox.addActionListener(e -> updateNameLabel());
        registerButton.addActionListener(e -> register());
        backButton.addActionListener(e -> openLogin());
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    private void styleTextField(JTextField field) {

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setMinimumSize(new Dimension(0, 38));

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(0, 12, 0, 12)
                )
        );

        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_COLOR);
    }

    private void styleComboBox(JComboBox<String> comboBox) {

        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        comboBox.setMinimumSize(new Dimension(0, 38));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(TEXT_COLOR);
    }

    private void updateNameLabel() {

        if (roleComboBox.getSelectedItem().equals("Employer")) {
            nameLabel.setText("Company Name");
        } else {
            nameLabel.setText("Full Name");
        }
    }

    private void register() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (username.isEmpty() || password.isEmpty()
                || name.isEmpty() || email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String error;

        try {

            if (role.equals("Job Seeker")) {
                error = registerController.registerJobSeeker(
                        username, password, name, email, phone);
            } else {
                error = registerController.registerEmployer(
                        username, password, name, email, phone);
            }

            if (error == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Registration successful!\n"
                                + "You can now login to JobFit.",
                        "Registration Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );
                openLogin();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        error,
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "An error occurred during registration.\n\n"
                            + ex.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openLogin() {

        dispose();
        new LoginFrame().setVisible(true);
    }
}