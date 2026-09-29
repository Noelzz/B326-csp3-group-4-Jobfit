package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.JobCategoryController;
import com.joysis.tvi.JobFit.model.JobCategory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageJobCategoriesFrame extends JFrame {

    private final JobCategoryController controller;

    private JTable categoriesTable;
    private DefaultTableModel tableModel;

    private JTextField nameField;
    private JTextArea descriptionArea;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton closeButton;

    public ManageJobCategoriesFrame() {

        controller =
                new JobCategoryController();

        setTitle("JobFit - Manage Job Categories");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadCategories();
    }

    private void createGUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "MANAGE JOB CATEGORIES",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        // =========================
        // INPUT PANEL
        // =========================

        JPanel inputPanel =
                new JPanel(
                        new GridLayout(2, 2, 10, 10)
                );

        JLabel nameLabel =
                new JLabel("Category Name:");

        JLabel descriptionLabel =
                new JLabel("Description:");

        nameField =
                new JTextField();

        descriptionArea =
                new JTextArea();

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane descriptionScrollPane =
                new JScrollPane(descriptionArea);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionScrollPane);

        // =========================
        // TOP PANEL
        // =========================

        JPanel topPanel =
                new JPanel(new BorderLayout(10, 10));

        topPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        topPanel.add(
                inputPanel,
                BorderLayout.CENTER
        );

        // =========================
        // TABLE
        // =========================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Category Name",
                                "Description"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        categoriesTable =
                new JTable(tableModel);

        categoriesTable.setRowHeight(30);

        categoriesTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        categoriesTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        categoriesTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(180);

        categoriesTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(400);

        JScrollPane tableScrollPane =
                new JScrollPane(categoriesTable);

        // =========================
        // BUTTONS
        // =========================

        addButton =
                new JButton("ADD");

        updateButton =
                new JButton("UPDATE");

        deleteButton =
                new JButton("DELETE");

        refreshButton =
                new JButton("REFRESH");

        closeButton =
                new JButton("CLOSE");

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                10
                        )
                );

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        // =========================
        // ADD COMPONENTS
        // =========================

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================
        // BUTTON EVENTS
        // =========================

        addButton.addActionListener(
                e -> addCategory()
        );

        updateButton.addActionListener(
                e -> updateCategory()
        );

        deleteButton.addActionListener(
                e -> deleteCategory()
        );

        refreshButton.addActionListener(
                e -> loadCategories()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        // =========================
        // TABLE SELECTION
        // =========================

        categoriesTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    int selectedRow =
                            categoriesTable.getSelectedRow();

                    if (selectedRow >= 0) {

                        String name =
                                tableModel.getValueAt(
                                        selectedRow,
                                        1
                                ).toString();

                        Object descriptionObject =
                                tableModel.getValueAt(
                                        selectedRow,
                                        2
                                );

                        String description =
                                descriptionObject != null
                                        ? descriptionObject.toString()
                                        : "";

                        nameField.setText(name);
                        descriptionArea.setText(
                                description
                        );
                    }
                });
    }

    // =========================
    // LOAD CATEGORIES
    // =========================

    private void loadCategories() {

        tableModel.setRowCount(0);

        nameField.setText("");
        descriptionArea.setText("");

        List<JobCategory> categories =
                controller.getAllCategories();

        for (JobCategory category : categories) {

            tableModel.addRow(
                    new Object[]{
                            category.getId(),
                            category.getName(),
                            category.getDescription()
                    }
            );
        }
    }

    // =========================
    // ADD CATEGORY
    // =========================

    private void addCategory() {

        String name =
                nameField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a category name.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean success =
                controller.addCategory(
                        name,
                        description
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job category added successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add job category.\n"
                            + "The category may already exist.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // UPDATE CATEGORY
    // =========================

    private void updateCategory() {

        int selectedRow =
                categoriesTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int categoryId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        String name =
                nameField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a category name.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean success =
                controller.updateCategory(
                        categoryId,
                        name,
                        description
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job category updated successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update job category.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // DELETE CATEGORY
    // =========================

    private void deleteCategory() {

        int selectedRow =
                categoriesTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int categoryId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        String categoryName =
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete \""
                                + categoryName
                                + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success =
                controller.deleteCategory(
                        categoryId
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job category deleted successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete this category.\n"
                            + "It may currently be used by a job.",
                    "Delete Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}