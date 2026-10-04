package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.controller.JobRequiredSkillController;
import com.joysis.tvi.JobFit.controller.SkillController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobRequiredSkill;
import com.joysis.tvi.JobFit.model.Skill;
import com.joysis.tvi.JobFit.model.User;
import com.joysis.tvi.JobFit.repository.SkillRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ManageRequiredSkillsFrame extends BaseTableFrame {

    private final User user;
    private final JobController jobController;
    private final JobRequiredSkillController requiredSkillController;
    private final SkillRepository skillRepository;
    private final SkillController skillController;

    private JComboBox<Job> jobComboBox;
    private JComboBox<Skill> skillComboBox;

    private JTable skillsTable;
    private DefaultTableModel tableModel;

    public ManageRequiredSkillsFrame(User user) {

        super(
                "JobFit - Manage Required Skills",
                "Manage Required Skills",
                "Assign and manage the skills required for each job post.",
                900,
                650
        );

        this.user = user;
        this.jobController = new JobController();
        this.requiredSkillController = new JobRequiredSkillController();
        this.skillRepository = new SkillRepository();
        this.skillController = new SkillController();

        createContent();

        loadJobs();
        loadSkills();
        loadRequiredSkills();
    }

    private void createContent() {

        toolbarPanel.setLayout(new BorderLayout());

        // ==========================================
        // SELECTION CARD
        // ==========================================

        RoundedPanel selectionCard = new RoundedPanel(UITheme.CARD_RADIUS);
        selectionCard.setBackground(UITheme.SURFACE_BLUE);
        selectionCard.setLayout(new BorderLayout());
        selectionCard.setBorder(
                new EmptyBorder(18, 20, 18, 20)
        );

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel sectionTitle = new JLabel("Job Skill Requirements");
        sectionTitle.setFont(UITheme.SECTION_TITLE);
        sectionTitle.setForeground(UITheme.TEXT);

        JLabel sectionInfo = new JLabel(
                "Select a job and assign the skills required for the position.");
        sectionInfo.setFont(UITheme.SMALL);
        sectionInfo.setForeground(UITheme.TEXT_SECONDARY);

        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectionInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(sectionTitle);
        infoPanel.add(Box.createVerticalStrut(UITheme.GAP_XS));
        infoPanel.add(sectionInfo);

        // ==========================================
        // FORM AREA
        // ==========================================

        JPanel fieldsPanel = new JPanel(
                new GridLayout(2, 2, UITheme.GAP_MD, UITheme.GAP_SM)
        );
        fieldsPanel.setOpaque(false);
        fieldsPanel.setBorder(
                new EmptyBorder(UITheme.GAP_MD, 0, 0, 0)
        );

        JLabel jobLabel = createLabel("Select Job");
        JLabel skillLabel = createLabel("Select Skill");

        jobComboBox = new JComboBox<>();
        skillComboBox = new JComboBox<>();

        styleComboBox(jobComboBox);
        styleComboBox(skillComboBox);

        // ==========================================
        // SKILL ROW WITH + NEW BUTTON
        // ==========================================

        JButton addNewSkillButton = new RoundedButton(
                "+ New",
                UITheme.NAVY_LIGHT,
                UITheme.NAVY
        );
        addNewSkillButton.setPreferredSize(new Dimension(90, UITheme.FIELD_HEIGHT));

        JPanel skillPanel = new JPanel(new BorderLayout(8, 0));
        skillPanel.setOpaque(false);
        skillPanel.add(skillComboBox, BorderLayout.CENTER);
        skillPanel.add(addNewSkillButton, BorderLayout.EAST);

        fieldsPanel.add(jobLabel);
        fieldsPanel.add(skillLabel);
        fieldsPanel.add(jobComboBox);
        fieldsPanel.add(skillPanel);

        selectionCard.add(infoPanel, BorderLayout.NORTH);
        selectionCard.add(fieldsPanel, BorderLayout.CENTER);

        toolbarPanel.add(selectionCard, BorderLayout.CENTER);

        // ==========================================
        // TABLE
        // ==========================================

        tableModel = new DefaultTableModel(
                new Object[]{"Skill ID", "Required Skill"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        skillsTable = new JTable(tableModel);
        skillsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        styleTable(skillsTable);
        skillsTable.setRowHeight(42);
        skillsTable.setFillsViewportHeight(true);

        skillsTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        skillsTable.getColumnModel().getColumn(0).setMaxWidth(120);
        skillsTable.getColumnModel().getColumn(1).setPreferredWidth(600);

        JScrollPane scrollPane = new JScrollPane(skillsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // ==========================================
        // BUTTONS
        // ==========================================

        JButton addButton = new RoundedButton(
                "Add Skill", UITheme.BLUE, UITheme.BLUE_HOVER);
        JButton removeButton = new RoundedButton(
                "Remove Skill", UITheme.DANGER, new Color(190, 55, 65));
        JButton refreshButton = new RoundedButton(
                "Refresh", UITheme.NAVY_LIGHT, UITheme.NAVY);
        JButton closeButton = new RoundedButton(
                "Close", UITheme.NAVY_LIGHT, UITheme.NAVY);

        addButton.setPreferredSize(new Dimension(125, 40));
        removeButton.setPreferredSize(new Dimension(135, 40));
        refreshButton.setPreferredSize(new Dimension(110, 40));
        closeButton.setPreferredSize(new Dimension(100, 40));

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, UITheme.GAP_SM, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(
                new EmptyBorder(UITheme.GAP_MD, 0, 0, 0));
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        JPanel tableSection = new JPanel(new BorderLayout());
        tableSection.setOpaque(false);
        tableSection.add(scrollPane, BorderLayout.CENTER);
        tableSection.add(bottomPanel, BorderLayout.SOUTH);

        tableContainer.add(tableSection, BorderLayout.CENTER);

        // ==========================================
        // ACTIONS
        // ==========================================

        jobComboBox.addActionListener(e -> loadRequiredSkills());

        addNewSkillButton.addActionListener(e -> addNewSkill());

        addButton.addActionListener(e -> addRequiredSkill());

        removeButton.addActionListener(e -> removeRequiredSkill());

        refreshButton.addActionListener(e -> {
            loadJobs();
            loadSkills();
            loadRequiredSkills();
        });

        closeButton.addActionListener(e -> dispose());
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(UITheme.LABEL);
        label.setForeground(UITheme.TEXT);

        return label;
    }

    private void styleComboBox(JComboBox<?> comboBox) {

        comboBox.setFont(UITheme.BODY);
        comboBox.setForeground(UITheme.TEXT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setPreferredSize(new Dimension(0, UITheme.FIELD_HEIGHT));
        comboBox.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_BLUE));
    }

    private void loadJobs() {

        jobComboBox.removeAllItems();

        int employerId = getEmployerId();

        if (employerId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Employer profile was not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        List<Job> jobs = jobController.getJobsByEmployer(employerId);

        for (Job job : jobs) {
            jobComboBox.addItem(job);
        }

        if (!jobs.isEmpty()) {
            jobComboBox.setSelectedIndex(0);
        }
    }

    private void loadSkills() {

        skillComboBox.removeAllItems();

        List<Skill> skills = skillRepository.getAllSkills();

        for (Skill skill : skills) {
            skillComboBox.addItem(skill);
        }
    }

    private void loadRequiredSkills() {

        tableModel.setRowCount(0);

        Job selectedJob = (Job) jobComboBox.getSelectedItem();

        if (selectedJob == null) {
            return;
        }

        List<JobRequiredSkill> skills =
                requiredSkillController.getRequiredSkillsByJob(selectedJob.getId());

        for (JobRequiredSkill skill : skills) {
            tableModel.addRow(new Object[]{
                    skill.getSkillId(),
                    skill.getSkillName()
            });
        }
    }

    // ==========================================
    // ADD NEW SKILL TO CATALOG
    // ==========================================
    private void addNewSkill() {

        String name = JOptionPane.showInputDialog(
                this,
                "Enter new skill name:",
                "Add New Skill",
                JOptionPane.PLAIN_MESSAGE
        );

        if (name == null) return;

        name = name.trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Skill name cannot be empty.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (name.length() > 100) {
            JOptionPane.showMessageDialog(
                    this,
                    "Skill name is too long (max 100 characters).",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Skill skill = skillController.findOrCreateSkill(name);

        if (skill == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add skill. Please try again.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Refresh the dropdown so the new skill appears
        loadSkills();

        // Select the new skill in the combo box
        for (int i = 0; i < skillComboBox.getItemCount(); i++) {
            if (skillComboBox.getItemAt(i).getId() == skill.getId()) {
                skillComboBox.setSelectedIndex(i);
                break;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Skill \"" + skill.getName() + "\" added.",
                "JobFit",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void addRequiredSkill() {

        Job selectedJob = (Job) jobComboBox.getSelectedItem();
        Skill selectedSkill = (Skill) skillComboBox.getSelectedItem();

        if (selectedJob == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (selectedSkill == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a skill.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean success = requiredSkillController.addRequiredSkill(
                selectedJob.getId(),
                selectedSkill.getId()
        );

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Required skill added successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            loadRequiredSkills();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "This skill may already be required for this job.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void removeRequiredSkill() {

        int selectedRow = skillsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a required skill first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Job selectedJob = (Job) jobComboBox.getSelectedItem();

        if (selectedJob == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int skillId = (int) tableModel.getValueAt(selectedRow, 0);
        String skillName = tableModel.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Remove \"" + skillName + "\" from this job?",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = requiredSkillController.removeRequiredSkill(
                selectedJob.getId(),
                skillId
        );

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Required skill removed successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );
            loadRequiredSkills();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to remove required skill.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private int getEmployerId() {

        String sql = "SELECT id FROM Employer WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, user.getId());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}