package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.JobCategoryController;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobCategory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class AdminJobManagementFrame extends BaseTableFrame {

    private final JobCategoryController categoryController;
    private final JobController jobController;

    private JTable categoriesTable;
    private DefaultTableModel categoriesModel;

    private JTextField categoryNameField;
    private JTextArea categoryDescriptionArea;

    private JTable jobsTable;
    private DefaultTableModel jobsModel;

    public AdminJobManagementFrame() {

        super(
                "JobFit - Job Management",
                "Jobs & Categories",
                "Manage job categories and monitor job listings.",
                1100,
                760
        );

        setResizable(true);

        categoryController = new JobCategoryController();
        jobController = new JobController();

        createContent();

        loadCategories();
        loadJobs();
    }

    private void createContent() {

        toolbarPanel.setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(UITheme.LABEL);
        tabs.setForeground(UITheme.TEXT);
        tabs.setBackground(UITheme.SURFACE);
        tabs.addTab("Categories", createCategoriesTab());
        tabs.addTab("Job Listings", createJobsTab());

        tableContainer.add(tabs, BorderLayout.CENTER);
    }


    // JOB CATEGORIES TAB
    private JPanel createCategoriesTab() {

        JPanel panel = new JPanel(new BorderLayout(0, UITheme.GAP_MD));

        panel.setOpaque(false);

        panel.setBorder(new EmptyBorder(10, 16, 10, 16));

        // CATEGORY FORM CARD
        RoundedPanel formCard = new RoundedPanel(UITheme.CARD_RADIUS);

        formCard.setBackground(UITheme.SURFACE_BLUE);
        formCard.setLayout(new BorderLayout());
        formCard.setBorder(new EmptyBorder(16, 26, 16, 26));
        formCard.setPreferredSize(new Dimension(0, 165));

        // =========================
        // FORM HEADER
        // =========================

        JLabel formTitle = new JLabel("Category Information");
        formTitle.setFont(UITheme.SECTION_TITLE);
        formTitle.setForeground(UITheme.TEXT);
        JLabel helperText = new JLabel("Create a new job category or select an existing category to edit.");
        helperText.setFont(UITheme.SMALL);
        helperText.setForeground(UITheme.TEXT_SECONDARY);
        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        helperText.setAlignmentX(Component.LEFT_ALIGNMENT);
        headingPanel.add(formTitle);
        headingPanel.add(Box.createVerticalStrut(UITheme.GAP_XS));
        headingPanel.add(helperText);

        // =========================
        // INPUT FIELDS
        // =========================

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        fieldsPanel.setBorder(new EmptyBorder(12, 0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 7, 18);

        // CATEGORY NAME LABEL
        JLabel nameLabel = createFieldLabel("Category Name");
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);

        // DESCRIPTION LABEL
        JLabel descLabel = createFieldLabel("Category Description");

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 7, 0);
        fieldsPanel.add(descLabel, gbc);

        // CATEGORY NAME FIELD
        categoryNameField = new JTextField();

        styleField(categoryNameField);

        categoryNameField.setPreferredSize(new Dimension(0, 42));

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.insets = new Insets(0, 0, 0, 18);

        fieldsPanel.add(categoryNameField, gbc);


        // DESCRIPTION FIELD

        categoryDescriptionArea = new JTextArea(1, 20);
        categoryDescriptionArea.setLineWrap(true);
        categoryDescriptionArea.setWrapStyleWord(true);
        categoryDescriptionArea.setFont(UITheme.BODY);
        categoryDescriptionArea.setForeground(
                UITheme.TEXT);
        categoryDescriptionArea.setBackground(Color.WHITE);

        categoryDescriptionArea.setBorder(new EmptyBorder(9, 12, 9, 12));

        JScrollPane descScroll = new JScrollPane(categoryDescriptionArea);
        descScroll.setPreferredSize(new Dimension(0, 42));
        descScroll.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_BLUE));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        fieldsPanel.add(descScroll, gbc);
        formCard.add(headingPanel, BorderLayout.NORTH);
        formCard.add(fieldsPanel, BorderLayout.CENTER);

        // =========================
        // CATEGORY TABLE
        // =========================

        categoriesModel = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Category Name",
                        "Description"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        categoriesTable = new JTable(categoriesModel);
        categoriesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        styleTable(categoriesTable);
        categoriesTable.setRowHeight(36);
        categoriesTable.setFillsViewportHeight(true);
        categoriesTable.setIntercellSpacing(new Dimension(0, 1));
        categoriesTable.setPreferredScrollableViewportSize(new Dimension(900, 200));

        categoriesTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(55);

        categoriesTable
                .getColumnModel()
                .getColumn(0)
                .setMaxWidth(70);

        categoriesTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(260);

        categoriesTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(580);

        JScrollPane tableScroll = new JScrollPane(categoriesTable);

        tableScroll.setBorder(BorderFactory.createEmptyBorder());

        tableScroll.getViewport().setBackground(Color.WHITE);

        // =========================
        // ACTION BUTTONS
        // =========================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        JButton addBtn = new RoundedButton("Add Category", UITheme.BLUE, UITheme.BLUE_HOVER);
        JButton updateBtn = new RoundedButton("Update Category", UITheme.BLUE, UITheme.BLUE_HOVER);
        JButton deleteBtn = new RoundedButton("Delete Category", UITheme.DANGER, new Color(190, 55, 65));
        JButton refreshBtn = new RoundedButton("Refresh", UITheme.NAVY_LIGHT, UITheme.NAVY);

        Dimension buttonSize = new Dimension(140, 40);

        addBtn.setPreferredSize(buttonSize);
        updateBtn.setPreferredSize(buttonSize);
        deleteBtn.setPreferredSize(buttonSize);
        refreshBtn.setPreferredSize(buttonSize);

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(8, 0, 0, 0));
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        JPanel tableSection = new JPanel(new BorderLayout());

        tableSection.setOpaque(false);
        tableSection.setBorder(new EmptyBorder(2, 0, 0, 0));
        tableSection.add(tableScroll, BorderLayout.CENTER);
        tableSection.add(bottomPanel, BorderLayout.SOUTH);

        panel.add(
                formCard,
                BorderLayout.NORTH
        );

        panel.add(
                tableSection,
                BorderLayout.CENTER
        );

        // =========================
        // ACTIONS
        // =========================
        addBtn.addActionListener(
                e -> addCategory());
        updateBtn.addActionListener(e -> updateCategory());
        deleteBtn.addActionListener(e -> deleteCategory());

        refreshBtn.addActionListener(e -> loadCategories());

        categoriesTable.getSelectionModel().addListSelectionListener(
                e ->
                {
                    if (!e.getValueIsAdjusting()) {
                        int row = categoriesTable.getSelectedRow();
                        if (row >= 0) {
                            categoryNameField.setText(categoriesModel.getValueAt(row, 1).toString());
                            Object desc = categoriesModel.getValueAt(row, 2);
                            categoryDescriptionArea.setText(desc == null ? "" : desc.toString());
                        }
                    }
                }

                );

        return panel;
    }

    // ==========================================
    // JOB POSTS TAB
    // ==========================================

    private JPanel createJobsTab() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setOpaque(false);

        panel.setBorder(
                new EmptyBorder(UITheme.GAP_MD, UITheme.GAP_MD, UITheme.GAP_MD, UITheme.GAP_MD));

        JLabel infoLabel = new JLabel("All job listings posted by employers.");
        infoLabel.setFont(UITheme.BODY);
        infoLabel.setForeground(UITheme.TEXT_SECONDARY);
        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setOpaque(false);
        headingPanel.setBorder(new EmptyBorder(0, 0, UITheme.GAP_MD, 0));
        headingPanel.add(infoLabel, BorderLayout.WEST);
        jobsModel = new DefaultTableModel(new Object[]{
                "ID",
                "Employer ID",
                "Category ID",
                "Title",
                "Location",
                "Salary"
        },
                0
        ) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jobsTable = new JTable(jobsModel);

        jobsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        styleTable(jobsTable);

        JScrollPane scroll = new JScrollPane(jobsTable);

        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        // =========================
        // JOB BUTTONS
        // =========================

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, UITheme.GAP_SM, 0));

        buttonPanel.setOpaque(false);

        JButton refreshBtn = new RoundedButton("Refresh Jobs", UITheme.NAVY_LIGHT, UITheme.NAVY);

        JButton deleteBtn =new RoundedButton(
                        "Delete Job",
                        UITheme.DANGER,
                        new Color(
                                190,
                                55,
                                65
                        )
                );

        Dimension buttonSize =
                new Dimension(
                        135,
                        40
                );

        refreshBtn.setPreferredSize(
                buttonSize
        );

        deleteBtn.setPreferredSize(
                buttonSize
        );

        buttonPanel.add(
                refreshBtn
        );

        buttonPanel.add(
                deleteBtn
        );

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        bottomPanel.setBorder(
                new EmptyBorder(
                        UITheme.GAP_MD,
                        0,
                        0,
                        0
                )
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.EAST
        );

        panel.add(
                headingPanel,
                BorderLayout.NORTH
        );

        panel.add(
                scroll,
                BorderLayout.CENTER
        );

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        refreshBtn.addActionListener(
                e -> loadJobs()
        );

        deleteBtn.addActionListener(
                e -> deleteJob()
        );

        return panel;
    }

    // ==========================================
    // STYLE HELPERS
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
                        200,
                        UITheme.FIELD_HEIGHT
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
    // LOAD CATEGORIES
    // ==========================================

    private void loadCategories() {

        categoriesModel.setRowCount(0);

        categoryNameField.setText("");
        categoryDescriptionArea.setText("");

        List<JobCategory> categories =
                categoryController
                        .getAllCategories();

        for (JobCategory c : categories) {

            categoriesModel.addRow(
                    new Object[]{
                            c.getId(),
                            c.getName(),
                            c.getDescription()
                    }
            );
        }
    }

    // ==========================================
    // ADD CATEGORY
    // ==========================================

    private void addCategory() {

        String name =
                categoryNameField
                        .getText()
                        .trim();

        String description =
                categoryDescriptionArea
                        .getText()
                        .trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a category name.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (
                categoryController
                        .addCategory(
                                name,
                                description
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category added successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add category.\n"
                            + "It may already exist.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // UPDATE CATEGORY
    // ==========================================

    private void updateCategory() {

        int row =
                categoriesTable
                        .getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                (int) categoriesModel
                        .getValueAt(
                                row,
                                0
                        );

        String name =
                categoryNameField
                        .getText()
                        .trim();

        String description =
                categoryDescriptionArea
                        .getText()
                        .trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a category name.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (
                categoryController
                        .updateCategory(
                                id,
                                name,
                                description
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category updated successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update category.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // DELETE CATEGORY
    // ==========================================

    private void deleteCategory() {

        int row =
                categoriesTable
                        .getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                (int) categoriesModel
                        .getValueAt(
                                row,
                                0
                        );

        String name =
                categoriesModel
                        .getValueAt(
                                row,
                                1
                        )
                        .toString();

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete \""
                                + name
                                + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (
                categoryController
                        .deleteCategory(
                                id
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category deleted successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete this category.\n"
                            + "It may be used by existing jobs.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ==========================================
    // LOAD JOBS
    // ==========================================

    private void loadJobs() {

        jobsModel.setRowCount(0);

        List<Job> jobs =
                jobController.getAllJobs();

        for (Job job : jobs) {

            jobsModel.addRow(
                    new Object[]{
                            job.getId(),
                            job.getEmployerId(),
                            job.getCategoryId(),
                            job.getTitle(),
                            job.getLocation(),
                            String.format(
                                    "₱%.2f",
                                    job.getSalary()
                            )
                    }
            );
        }
    }

    // ==========================================
    // DELETE JOB
    // ==========================================

    private void deleteJob() {

        int row =
                jobsTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                (int) jobsModel
                        .getValueAt(
                                row,
                                0
                        );

        String title =
                jobsModel
                        .getValueAt(
                                row,
                                3
                        )
                        .toString();

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete job \""
                                + title
                                + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (
                deleteJobFromDb(
                        id
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job deleted successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadJobs();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete this job.\n"
                            + "It may have related records.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ==========================================
    // DATABASE DELETE
    // ==========================================

    private boolean deleteJobFromDb(
            int jobId
    ) {

        String sql =
                "DELETE FROM Jobs WHERE id = ?";

        try (
                Connection conn =
                        DatabaseConnection
                                .getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                sql
                        )
        ) {

            stmt.setInt(
                    1,
                    jobId
            );

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}