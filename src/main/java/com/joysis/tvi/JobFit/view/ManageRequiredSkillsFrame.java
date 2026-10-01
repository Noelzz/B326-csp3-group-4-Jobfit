package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.config.RoundedButton;
import com.joysis.tvi.JobFit.controller.JobController;
import com.joysis.tvi.JobFit.controller.JobRequiredSkillController;
import com.joysis.tvi.JobFit.model.Job;
import com.joysis.tvi.JobFit.model.JobRequiredSkill;
import com.joysis.tvi.JobFit.model.Skill;
import com.joysis.tvi.JobFit.model.User;
import com.joysis.tvi.JobFit.repository.SkillRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ManageRequiredSkillsFrame extends JFrame {

    private final User user;
    private final JobController jobController;
    private final JobRequiredSkillController requiredSkillController;
    private final SkillRepository skillRepository;

    private JComboBox<Job> jobComboBox;
    private JComboBox<Skill> skillComboBox;

    private JTable skillsTable;
    private DefaultTableModel tableModel;

    public ManageRequiredSkillsFrame(User user) {

        this.user = user;
        this.jobController = new JobController();
        this.requiredSkillController = new JobRequiredSkillController();
        this.skillRepository = new SkillRepository();

        setTitle("JobFit - Manage Required Skills");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();

        loadJobs();
        loadSkills();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("MANAGE JOB REQUIRED SKILLS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel selectionPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel jobLabel = new JLabel("Select Job:");
        JLabel skillLabel = new JLabel("Select Skill:");

        jobComboBox = new JComboBox<>();
        skillComboBox = new JComboBox<>();

        selectionPanel.add(jobLabel);
        selectionPanel.add(jobComboBox);
        selectionPanel.add(skillLabel);
        selectionPanel.add(skillComboBox);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Skill"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        skillsTable = new JTable(tableModel);
        skillsTable.setRowHeight(30);
        skillsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(skillsTable);

        JButton addButton = new RoundedButton("ADD SKILL");
        JButton removeButton = new RoundedButton("REMOVE", new Color(220, 70, 70), new Color(195, 55, 55));
        JButton refreshButton = new RoundedButton("REFRESH");
        JButton closeButton = new RoundedButton("CLOSE");

        Dimension btnSize = new Dimension(120, 36);
        addButton.setPreferredSize(btnSize);
        removeButton.setPreferredSize(btnSize);
        refreshButton.setPreferredSize(btnSize);
        closeButton.setPreferredSize(btnSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        JPanel northPanel = new JPanel(new BorderLayout(10, 10));
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(selectionPanel, BorderLayout.CENTER);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        jobComboBox.addActionListener(e -> loadRequiredSkills());
        addButton.addActionListener(e -> addRequiredSkill());
        removeButton.addActionListener(e -> removeRequiredSkill());
        refreshButton.addActionListener(e -> {
            loadJobs();
            loadSkills();
            loadRequiredSkills();
        });
        closeButton.addActionListener(e -> dispose());
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
        int skillId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Remove this required skill?",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

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