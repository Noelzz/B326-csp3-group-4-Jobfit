package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.JobCategoryController;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobCategory;
import com.joysis.tvi.JobFit.config.RoundedButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class AdminJobManagementFrame extends JFrame {

    private final JobCategoryController categoryController;
    private final JobController jobController;

    private JTable categoriesTable;
    private DefaultTableModel categoriesModel;
    private JTextField categoryNameField;
    private JTextArea categoryDescriptionArea;

    private JTable jobsTable;
    private DefaultTableModel jobsModel;

    public AdminJobManagementFrame() {

        categoryController = new JobCategoryController();
        jobController = new JobController();

        setTitle("JobFit - Job Management");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();

        loadCategories();
        loadJobs();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("JOB MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Job Categories", createCategoriesTab());
        tabs.addTab("Job Posts", createJobsTab());

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(tabs, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createCategoriesTab() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel nameLabel = new JLabel("Category Name:");
        JLabel descLabel = new JLabel("Description:");

        categoryNameField = new JTextField();

        categoryDescriptionArea = new JTextArea(3, 20);
        categoryDescriptionArea.setLineWrap(true);
        categoryDescriptionArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(categoryDescriptionArea);

        form.add(nameLabel);
        form.add(categoryNameField);
        form.add(descLabel);
        form.add(descScroll);

        categoriesModel = new DefaultTableModel(
                new Object[]{"ID", "Category Name", "Description"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        categoriesTable = new JTable(categoriesModel);
        categoriesTable.setRowHeight(30);
        categoriesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        categoriesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        categoriesTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        categoriesTable.getColumnModel().getColumn(2).setPreferredWidth(500);

        JScrollPane tableScroll = new JScrollPane(categoriesTable);

        JButton addBtn = new RoundedButton("ADD");
        JButton updateBtn = new RoundedButton("UPDATE");
        JButton deleteBtn = new RoundedButton("DELETE", new Color(220, 70, 70), new Color(195, 55, 55));
        JButton refreshBtn = new RoundedButton("REFRESH");

        Dimension buttonSize = new Dimension(110, 36);
        addBtn.setPreferredSize(buttonSize);
        updateBtn.setPreferredSize(buttonSize);
        deleteBtn.setPreferredSize(buttonSize);
        refreshBtn.setPreferredSize(buttonSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(form, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addCategory());
        updateBtn.addActionListener(e -> updateCategory());
        deleteBtn.addActionListener(e -> deleteCategory());
        refreshBtn.addActionListener(e -> loadCategories());

        categoriesTable.getSelectionModel().addListSelectionListener(e -> {
            int row = categoriesTable.getSelectedRow();
            if (row >= 0) {
                categoryNameField.setText(categoriesModel.getValueAt(row, 1).toString());

                Object desc = categoriesModel.getValueAt(row, 2);
                categoryDescriptionArea.setText(desc == null ? "" : desc.toString());
            }
        });

        return panel;
    }

    private void loadCategories() {

        categoriesModel.setRowCount(0);
        categoryNameField.setText("");
        categoryDescriptionArea.setText("");

        List<JobCategory> categories = categoryController.getAllCategories();

        for (JobCategory c : categories) {
            categoriesModel.addRow(new Object[]{
                    c.getId(),
                    c.getName(),
                    c.getDescription()
            });
        }
    }

    private void addCategory() {

        String name = categoryNameField.getText().trim();
        String description = categoryDescriptionArea.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a category name.",
                    "JobFit", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (categoryController.addCategory(name, description)) {
            JOptionPane.showMessageDialog(this,
                    "Category added successfully.",
                    "JobFit", JOptionPane.INFORMATION_MESSAGE);
            loadCategories();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add category.\nIt may already exist.",
                    "JobFit", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCategory() {

        int row = categoriesTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a category first.",
                    "JobFit", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) categoriesModel.getValueAt(row, 0);
        String name = categoryNameField.getText().trim();
        String description = categoryDescriptionArea.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a category name.",
                    "JobFit", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (categoryController.updateCategory(id, name, description)) {
            JOptionPane.showMessageDialog(this,
                    "Category updated successfully.",
                    "JobFit", JOptionPane.INFORMATION_MESSAGE);
            loadCategories();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to update category.",
                    "JobFit", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCategory() {

        int row = categoriesTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a category first.",
                    "JobFit", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) categoriesModel.getValueAt(row, 0);
        String name = categoriesModel.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete \"" + name + "\"?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        if (categoryController.deleteCategory(id)) {
            JOptionPane.showMessageDialog(this,
                    "Category deleted successfully.",
                    "JobFit", JOptionPane.INFORMATION_MESSAGE);
            loadCategories();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Unable to delete this category.\n"
                            + "It may be used by existing jobs.",
                    "JobFit", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JPanel createJobsTab() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        jobsModel = new DefaultTableModel(
                new Object[]{
                        "ID", "Employer ID", "Category ID",
                        "Title", "Location", "Salary"
                }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jobsTable = new JTable(jobsModel);
        jobsTable.setRowHeight(30);
        jobsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(jobsTable);

        JButton refreshBtn = new RoundedButton("REFRESH");
        JButton deleteBtn = new RoundedButton("DELETE", new Color(220, 70, 70), new Color(195, 55, 55));

        Dimension buttonSize = new Dimension(110, 36);
        refreshBtn.setPreferredSize(buttonSize);
        deleteBtn.setPreferredSize(buttonSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(refreshBtn);
        buttonPanel.add(deleteBtn);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadJobs());
        deleteBtn.addActionListener(e -> deleteJob());

        return panel;
    }

    private void loadJobs() {

        jobsModel.setRowCount(0);

        List<Job> jobs = jobController.getAllJobs();

        for (Job job : jobs) {
            jobsModel.addRow(new Object[]{
                    job.getId(),
                    job.getEmployerId(),
                    job.getCategoryId(),
                    job.getTitle(),
                    job.getLocation(),
                    String.format("₱%.2f", job.getSalary())
            });
        }
    }

    private void deleteJob() {

        int row = jobsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a job first.",
                    "JobFit", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) jobsModel.getValueAt(row, 0);
        String title = jobsModel.getValueAt(row, 3).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete job \"" + title + "\"?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        if (deleteJobFromDb(id)) {
            JOptionPane.showMessageDialog(this,
                    "Job deleted successfully.",
                    "JobFit", JOptionPane.INFORMATION_MESSAGE);
            loadJobs();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Unable to delete this job.\n"
                            + "It may have related records.",
                    "JobFit", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean deleteJobFromDb(int jobId) {

        String sql = "DELETE FROM Jobs WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jobId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}