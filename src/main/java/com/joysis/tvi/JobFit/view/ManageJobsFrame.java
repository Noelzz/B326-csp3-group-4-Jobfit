package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.controller.JobRequiredSkillController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobRequiredSkill;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ManageJobsFrame extends BaseTableFrame {

    private final User user;
    private final JobController jobController;
    private final JobRequiredSkillController requiredSkillController;

    private JTable jobsTable;
    private DefaultTableModel tableModel;

    public ManageJobsFrame(User user) {

        super(
                "JobFit - Manage Job Posts",
                "Manage Job Posts",
                "View and manage all job listings posted by your company.",
                1000,
                650
        );

        this.user = user;
        this.jobController = new JobController();
        this.requiredSkillController =
                new JobRequiredSkillController();

        createContent();
        loadJobs();
    }

    // ==========================================
    // MAIN CONTENT
    // ==========================================

    private void createContent() {

        toolbarPanel.setLayout(
                new BorderLayout()
        );

        // ==========================================
        // INFORMATION CARD
        // ==========================================

        RoundedPanel infoCard =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        infoCard.setBackground(
                UITheme.SURFACE_BLUE
        );

        infoCard.setLayout(
                new BorderLayout()
        );

        infoCard.setBorder(
                new EmptyBorder(
                        16,
                        20,
                        16,
                        20
                )
        );

        JPanel infoText =
                new JPanel();

        infoText.setOpaque(false);

        infoText.setLayout(
                new BoxLayout(
                        infoText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel infoTitle =
                new JLabel(
                        "Job Listings"
                );

        infoTitle.setFont(
                UITheme.SECTION_TITLE
        );

        infoTitle.setForeground(
                UITheme.TEXT
        );

        JLabel infoDescription =
                new JLabel(
                        "Select a job below to view its details or manage the listing."
                );

        infoDescription.setFont(
                UITheme.SMALL
        );

        infoDescription.setForeground(
                UITheme.TEXT_SECONDARY
        );

        infoTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        infoDescription.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        infoText.add(
                infoTitle
        );

        infoText.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        infoText.add(
                infoDescription
        );

        infoCard.add(
                infoText,
                BorderLayout.CENTER
        );

        toolbarPanel.add(
                infoCard,
                BorderLayout.CENTER
        );

        // ==========================================
        // TABLE MODEL
        // ==========================================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Job Title",
                                "Category",
                                "Location",
                                "Salary"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        jobsTable =
                new JTable(
                        tableModel
                );

        jobsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        styleTable(
                jobsTable
        );

        jobsTable.setRowHeight(
                42
        );

        jobsTable.setFillsViewportHeight(
                true
        );

        // ==========================================
        // COLUMN WIDTHS
        // ==========================================

        jobsTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        55
                );

        jobsTable
                .getColumnModel()
                .getColumn(0)
                .setMaxWidth(
                        70
                );

        jobsTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        260
                );

        jobsTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        200
                );

        jobsTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        200
                );

        jobsTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        140
                );

        // ==========================================
        // TABLE SCROLL
        // ==========================================

        JScrollPane scrollPane =
                new JScrollPane(
                        jobsTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getViewport()
                .setBackground(
                        Color.WHITE
                );

        // ==========================================
        // BUTTONS
        // ==========================================

        JButton refreshButton =
                new RoundedButton(
                        "Refresh Jobs",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        JButton viewDetailsButton =
                new RoundedButton(
                        "View Details",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        JButton deleteButton =
                new RoundedButton(
                        "Delete Job",
                        UITheme.DANGER,
                        new Color(
                                190,
                                55,
                                65
                        )
                );

        JButton closeButton =
                new RoundedButton(
                        "Close",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        Dimension buttonSize =
                new Dimension(
                        135,
                        40
                );

        refreshButton.setPreferredSize(
                buttonSize
        );

        viewDetailsButton.setPreferredSize(
                buttonSize
        );

        deleteButton.setPreferredSize(
                buttonSize
        );

        closeButton.setPreferredSize(
                buttonSize
        );

        // ==========================================
        // BUTTON PANEL
        // ==========================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                UITheme.GAP_SM,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                viewDetailsButton
        );

        buttonPanel.add(
                deleteButton
        );

        buttonPanel.add(
                closeButton
        );

        // ==========================================
        // BOTTOM AREA
        // prevents overlap
        // ==========================================

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

        // ==========================================
        // TABLE CONTENT
        // ==========================================

        JPanel tableSection =
                new JPanel(
                        new BorderLayout()
                );

        tableSection.setOpaque(false);

        tableSection.add(
                scrollPane,
                BorderLayout.CENTER
        );

        tableSection.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        tableContainer.add(
                tableSection,
                BorderLayout.CENTER
        );

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        refreshButton.addActionListener(
                e -> loadJobs()
        );

        viewDetailsButton.addActionListener(
                e -> viewJobDetails()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedJob()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    // ==========================================
    // LOAD JOBS
    // ==========================================

    private void loadJobs() {

        tableModel.setRowCount(0);

        int employerId =
                getEmployerId();

        if (employerId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Employer profile was not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        List<Job> jobs =
                jobController.getJobsByEmployer(
                        employerId
                );

        for (Job job : jobs) {

            String categoryName =
                    getCategoryName(
                            job.getCategoryId()
                    );

            String salary =
                    String.format(
                            "₱%,.2f",
                            job.getSalary()
                    );

            tableModel.addRow(
                    new Object[]{
                            job.getId(),
                            job.getTitle(),
                            categoryName,
                            job.getLocation(),
                            salary
                    }
            );
        }
    }

    // ==========================================
    // VIEW JOB DETAILS
    // ==========================================

    private void viewJobDetails() {

        int selectedRow =
                jobsTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int jobId =
                (int) tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        );

        Job job = null;

        List<Job> jobs =
                jobController.getJobsByEmployer(
                        getEmployerId()
                );

        for (Job j : jobs) {

            if (j.getId() == jobId) {

                job = j;
                break;
            }
        }

        if (job == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load job details.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String categoryName =
                getCategoryName(
                        job.getCategoryId()
                );

        String categoryDescription =
                getCategoryDescription(
                        job.getCategoryId()
                );

        StringBuilder skillsText =
                new StringBuilder();

        List<JobRequiredSkill> requiredSkills =
                requiredSkillController
                        .getRequiredSkillsByJob(
                                jobId
                        );

        if (requiredSkills.isEmpty()) {

            skillsText.append("None");

        } else {

            for (
                    int i = 0;
                    i < requiredSkills.size();
                    i++
            ) {

                if (i > 0) {

                    skillsText.append(
                            ", "
                    );
                }

                skillsText.append(
                        requiredSkills
                                .get(i)
                                .getSkillName()
                );
            }
        }

        // ==========================================
        // JOB DETAILS PANEL
        // ==========================================

        JPanel detailsPanel =
                new JPanel();

        detailsPanel.setLayout(
                new BoxLayout(
                        detailsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        detailsPanel.setBorder(
                new EmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );

        detailsPanel.setPreferredSize(
                new Dimension(
                        470,
                        340
                )
        );

        detailsPanel.add(
                createDetailRow(
                        "Job Title",
                        job.getTitle()
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        detailsPanel.add(
                createDetailRow(
                        "Category",
                        categoryName
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        detailsPanel.add(
                createDetailRow(
                        "Category Description",
                        categoryDescription
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        detailsPanel.add(
                createDetailRow(
                        "Location",
                        job.getLocation()
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        detailsPanel.add(
                createDetailRow(
                        "Salary",
                        String.format(
                                "₱%,.2f",
                                job.getSalary()
                        )
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        detailsPanel.add(
                createDetailRow(
                        "Required Skills",
                        skillsText.toString()
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        detailsPanel.add(
                createDetailRow(
                        "Job Description",
                        job.getDescription()
                )
        );

        JOptionPane.showMessageDialog(
                this,
                detailsPanel,
                "Job Details",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // ==========================================
    // DETAIL ROW
    // ==========================================

    private JPanel createDetailRow(
            String label,
            String value
    ) {

        JPanel row =
                new JPanel(
                        new BorderLayout(
                                15,
                                0
                        )
                );

        row.setOpaque(false);

        JLabel labelComponent =
                new JLabel(
                        label
                );

        labelComponent.setFont(
                UITheme.LABEL
        );

        labelComponent.setForeground(
                UITheme.TEXT
        );

        labelComponent.setPreferredSize(
                new Dimension(
                        145,
                        24
                )
        );

        JLabel valueComponent =
                new JLabel(
                        "<html>"
                                + (value == null
                                ? ""
                                : value)
                                + "</html>"
                );

        valueComponent.setFont(
                UITheme.BODY
        );

        valueComponent.setForeground(
                UITheme.TEXT_SECONDARY
        );

        row.add(
                labelComponent,
                BorderLayout.WEST
        );

        row.add(
                valueComponent,
                BorderLayout.CENTER
        );

        return row;
    }

    // ==========================================
    // GET EMPLOYER ID
    // ==========================================

    private int getEmployerId() {

        String sql =
                "SELECT id FROM Employer WHERE user_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            statement.setInt(
                    1,
                    user.getId()
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(
                        "id"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    // ==========================================
    // GET CATEGORY NAME
    // ==========================================

    private String getCategoryName(
            int categoryId
    ) {

        String sql =
                "SELECT name FROM Job_Categories WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            statement.setInt(
                    1,
                    categoryId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getString(
                        "name"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "Unknown";
    }

    // ==========================================
    // GET CATEGORY DESCRIPTION
    // ==========================================

    private String getCategoryDescription(
            int categoryId
    ) {

        String sql =
                "SELECT description FROM Job_Categories WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            statement.setInt(
                    1,
                    categoryId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                String desc =
                        resultSet.getString(
                                "description"
                        );

                return desc == null
                        ? "None"
                        : desc;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "None";
    }

    // ==========================================
    // DELETE SELECTED JOB
    // ==========================================

    private void deleteSelectedJob() {

        int selectedRow =
                jobsTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int jobId =
                (int) tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        );

        String jobTitle =
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete \""
                                + jobTitle
                                + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (deleteJob(jobId)) {

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
                    "Failed to delete job.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // DELETE JOB
    // ==========================================

    private boolean deleteJob(
            int jobId
    ) {

        String sql =
                "DELETE FROM Jobs WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            statement.setInt(
                    1,
                    jobId
            );

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}