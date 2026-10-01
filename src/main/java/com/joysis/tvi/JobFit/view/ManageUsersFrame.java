package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.RoundedButton;
import com.joysis.tvi.JobFit.controller.UserController;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageUsersFrame extends JFrame {

    private final UserController controller;
    private final User currentUser;

    private JTable usersTable;
    private DefaultTableModel tableModel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public ManageUsersFrame(User currentUser) {

        this.currentUser = currentUser;
        this.controller = new UserController();

        setTitle("JobFit - Manage Users");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadUsers();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("MANAGE USERS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        roleComboBox = new JComboBox<>(new String[]{
                "admin", "job_seeker", "employer"
        });

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(roleLabel);
        inputPanel.add(roleComboBox);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Username", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        usersTable = new JTable(tableModel);
        usersTable.setRowHeight(30);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(usersTable);

        JButton addButton = new RoundedButton("ADD");
        JButton updateButton = new RoundedButton("UPDATE");
        JButton deleteButton = new RoundedButton("DELETE", new Color(220, 70, 70), new Color(195, 55, 55));
        JButton refreshButton = new RoundedButton("REFRESH");
        JButton closeButton = new RoundedButton("CLOSE");

        Dimension btnSize = new Dimension(110, 36);
        addButton.setPreferredSize(btnSize);
        updateButton.setPreferredSize(btnSize);
        deleteButton.setPreferredSize(btnSize);
        refreshButton.setPreferredSize(btnSize);
        closeButton.setPreferredSize(btnSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

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

                roleComboBox.setSelectedItem(
                        tableModel.getValueAt(selectedRow, 2).toString());
            }
        });
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