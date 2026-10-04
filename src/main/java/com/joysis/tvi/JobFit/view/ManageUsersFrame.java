package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.UserController;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageUsersFrame extends BaseTableFrame {

    private final UserController controller;
    private final User currentUser;

    private JTable usersTable;
    private DefaultTableModel tableModel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public ManageUsersFrame(User currentUser) {

        super(
                "JobFit - Manage Users",
                "Manage Users",
                "Add, update and manage JobFit system users.",
                950,
                650
        );

        this.currentUser = currentUser;
        this.controller = new UserController();

        createContent();
        loadUsers();
    }

    private void createContent() {

        // =========================
        // USER FORM
        // =========================

        RoundedPanel formCard =
                new RoundedPanel(UITheme.CARD_RADIUS);

        formCard.setBackground(UITheme.SURFACE);

        formCard.setLayout(
                new GridLayout(2, 3, UITheme.GAP_MD, UITheme.GAP_SM)
        );

        formCard.setBorder(
                new EmptyBorder(
                        UITheme.CARD_PADDING,
                        UITheme.CARD_PADDING,
                        UITheme.CARD_PADDING,
                        UITheme.CARD_PADDING
                )
        );

        JLabel usernameLabel = createFieldLabel("Username");
        JLabel passwordLabel = createFieldLabel("Password");
        JLabel roleLabel = createFieldLabel("Role");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[]{
                "admin", "job_seeker", "employer"
        });

        styleField(usernameField);
        styleField(passwordField);
        styleComboBox(roleComboBox);

        // =========================
        // PASSWORD ROW WITH SHOW CHECKBOX
        // =========================

        JCheckBox showPassword = new JCheckBox("Show");
        showPassword.setFont(UITheme.SMALL);
        showPassword.setForeground(UITheme.TEXT_SECONDARY);
        showPassword.setOpaque(false);
        showPassword.setFocusPainted(false);
        showPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));

        showPassword.addActionListener(e ->
                togglePasswordVisibility(showPassword.isSelected())
        );

        JPanel passwordPanel = new JPanel(new BorderLayout(6, 0));
        passwordPanel.setOpaque(false);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(showPassword, BorderLayout.EAST);

        formCard.add(usernameLabel);
        formCard.add(passwordLabel);
        formCard.add(roleLabel);

        formCard.add(usernameField);
        formCard.add(passwordPanel);
        formCard.add(roleComboBox);

        toolbarPanel.setLayout(new BorderLayout());
        toolbarPanel.add(formCard, BorderLayout.CENTER);

        // =========================
        // TABLE MODEL
        // =========================

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Username", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        usersTable = new JTable(tableModel);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        styleTable(usersTable);

        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tableSection = new JPanel(new BorderLayout());
        tableSection.setOpaque(false);
        tableSection.add(scrollPane, BorderLayout.CENTER);

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, UITheme.GAP_SM, 0)
        );
        buttonPanel.setOpaque(false);

        JButton addButton = new RoundedButton(
                "Add User", UITheme.BLUE, UITheme.BLUE_HOVER);
        JButton updateButton = new RoundedButton(
                "Update User", UITheme.BLUE, UITheme.BLUE_HOVER);
        JButton deleteButton = new RoundedButton(
                "Delete User", UITheme.DANGER, new Color(190, 55, 65));
        JButton refreshButton = new RoundedButton(
                "Refresh List", UITheme.NAVY_LIGHT, UITheme.NAVY);
        JButton closeButton = new RoundedButton(
                "Close", UITheme.NAVY_LIGHT, UITheme.NAVY);

        Dimension buttonSize = new Dimension(125, 40);
        addButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        refreshButton.setPreferredSize(buttonSize);
        closeButton.setPreferredSize(buttonSize);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(
                new EmptyBorder(UITheme.GAP_MD, 0, 0, 0)
        );
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        tableSection.add(bottomPanel, BorderLayout.SOUTH);

        tableContainer.add(tableSection, BorderLayout.CENTER);

        // =========================
        // ACTIONS
        // =========================

        addButton.addActionListener(e -> addUser());
        updateButton.addActionListener(e -> updateUser());
        deleteButton.addActionListener(e -> deleteUser());
        refreshButton.addActionListener(e -> loadUsers());
        closeButton.addActionListener(e -> dispose());

        usersTable.getSelectionModel().addListSelectionListener(e -> {

            int selectedRow = usersTable.getSelectedRow();

            if (selectedRow >= 0) {
                usernameField.setText(
                        tableModel.getValueAt(selectedRow, 1).toString());

                passwordField.setText("");

                // reset show checkbox when row changes
                showPassword.setSelected(false);
                togglePasswordVisibility(false);

                roleComboBox.setSelectedItem(
                        tableModel.getValueAt(selectedRow, 2).toString());
            }
        });
    }

    private void togglePasswordVisibility(boolean show) {

        if (show) {
            passwordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('\u2022');
        }
    }

    private JLabel createFieldLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(UITheme.LABEL);
        label.setForeground(UITheme.TEXT);

        return label;
    }

    private void styleField(JTextField field) {

        field.setFont(UITheme.BODY);
        field.setForeground(UITheme.TEXT);
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(200, UITheme.FIELD_HEIGHT));
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.BORDER),
                        new EmptyBorder(8, 12, 8, 12)
                )
        );
    }

    private void styleComboBox(JComboBox<String> comboBox) {

        comboBox.setFont(UITheme.BODY);
        comboBox.setForeground(UITheme.TEXT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setPreferredSize(new Dimension(200, UITheme.FIELD_HEIGHT));
    }

    private void loadUsers() {

        tableModel.setRowCount(0);

        usernameField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);

        List<User> users = controller.getAllUsers();

        for (User user : users) {
            tableModel.addRow(new Object[]{
                    user.getId(),
                    user.getUsername(),
                    user.getRole()
            });
        }
    }

    private void addUser() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = roleComboBox.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String error = controller.addUser(username, password, role);

        if (error == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "User added successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    error,
                    "Add User Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateUser() {

        int selectedRow = usersTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = roleComboBox.getSelectedItem().toString();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Username cannot be empty.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (password.isEmpty()) {

            User selectedUser = null;
            List<User> users = controller.getAllUsers();

            for (User user : users) {
                if (user.getId() == userId) {
                    selectedUser = user;
                    break;
                }
            }

            if (selectedUser == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Unable to find the selected user.",
                        "JobFit",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            password = selectedUser.getPassword();
        }

        String error = controller.updateUser(userId, username, password, role);

        if (error == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "User updated successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    error,
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteUser() {

        int selectedRow = usersTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = tableModel.getValueAt(selectedRow, 1).toString();

        if (currentUser != null && userId == currentUser.getId()) {
            JOptionPane.showMessageDialog(
                    this,
                    "You cannot delete the account currently logged in.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete user \"" + username + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) return;

        String error = controller.deleteUser(userId);

        if (error == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "User deleted successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    error,
                    "Delete Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}