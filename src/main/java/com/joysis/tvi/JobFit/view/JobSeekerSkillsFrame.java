package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.SkillController;
import com.joysis.tvi.JobFit.model.Skill;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class JobSeekerSkillsFrame extends JFrame {

    private final User user;
    private final SkillController controller;

    private JComboBox<Skill> skillComboBox;

    private JTable skillsTable;
    private DefaultTableModel tableModel;

    private JButton addButton;
    private JButton removeButton;
    private JButton refreshButton;
    private JButton closeButton;

    public JobSeekerSkillsFrame(User user) {

        this.user = user;
        controller = new SkillController();

        setTitle("JobFit - Manage Skills");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();

        loadSkills();
        loadJobSeekerSkills();
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
                        "MANAGE MY SKILLS",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JLabel infoLabel =
                new JLabel(
                        "Add or remove skills from your profile.",
                        SwingConstants.CENTER
                );

        infoLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JPanel headerPanel =
                new JPanel(new GridLayout(2, 1));

        headerPanel.add(titleLabel);
        headerPanel.add(infoLabel);

        // =========================
        // SKILL SELECTION
        // =========================

        JPanel selectionPanel =
                new JPanel(
                        new GridLayout(1, 2, 10, 10)
                );

        JLabel skillLabel =
                new JLabel("Select Skill:");

        skillComboBox =
                new JComboBox<>();

        selectionPanel.add(skillLabel);
        selectionPanel.add(skillComboBox);

        // =========================
        // TABLE
        // =========================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Skill ID",
                                "Skill Name"
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

        skillsTable =
                new JTable(tableModel);

        skillsTable.setRowHeight(30);

        skillsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(skillsTable);

        // =========================
        // BUTTONS
        // =========================

        addButton =
                new JButton("ADD SKILL");

        removeButton =
                new JButton("REMOVE SKILL");

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
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        // =========================
        // NORTH PANEL
        // =========================

        JPanel northPanel =
                new JPanel(new BorderLayout(10, 10));

        northPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        northPanel.add(
                selectionPanel,
                BorderLayout.CENTER
        );

        // =========================
        // MAIN PANEL
        // =========================

        mainPanel.add(
                northPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(
                e -> addSkill()
        );

        removeButton.addActionListener(
                e -> removeSkill()
        );

        refreshButton.addActionListener(
                e -> {
                    loadSkills();
                    loadJobSeekerSkills();
                }
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // LOAD ALL AVAILABLE SKILLS
    // =========================

    private void loadSkills() {

        skillComboBox.removeAllItems();

        List<Skill> skills =
                controller.getAllSkills();

        for (Skill skill : skills) {

            skillComboBox.addItem(skill);
        }
    }

    // =========================
    // LOAD JOB SEEKER SKILLS
    // =========================

    private void loadJobSeekerSkills() {

        tableModel.setRowCount(0);

        int jobSeekerId =
                getJobSeekerId();

        if (jobSeekerId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job seeker profile was not found.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        List<Skill> skills =
                controller.getJobSeekerSkills(
                        jobSeekerId
                );

        for (Skill skill : skills) {

            tableModel.addRow(
                    new Object[]{
                            skill.getId(),
                            skill.getName()
                    }
            );
        }
    }

    // =========================
    // ADD SKILL
    // =========================

    private void addSkill() {

        Skill selectedSkill =
                (Skill) skillComboBox.getSelectedItem();

        if (selectedSkill == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a skill.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int jobSeekerId =
                getJobSeekerId();

        if (jobSeekerId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job seeker profile was not found.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Check whether skill is already added
        List<Skill> existingSkills =
                controller.getJobSeekerSkills(
                        jobSeekerId
                );

        for (Skill skill : existingSkills) {

            if (skill.getId() ==
                    selectedSkill.getId()) {

                JOptionPane.showMessageDialog(
                        this,
                        "This skill is already in your profile.",
                        "JobFit",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        boolean success =
                controller.addSkill(
                        jobSeekerId,
                        selectedSkill.getId()
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Skill added successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadJobSeekerSkills();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add skill.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // REMOVE SKILL
    // =========================

    private void removeSkill() {

        int selectedRow =
                skillsTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a skill first.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int skillId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        String skillName =
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Remove \"" + skillName
                                + "\" from your profile?",
                        "Confirm Remove",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        int jobSeekerId =
                getJobSeekerId();

        if (jobSeekerId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job seeker profile was not found.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        boolean success =
                controller.removeSkill(
                        jobSeekerId,
                        skillId
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Skill removed successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadJobSeekerSkills();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to remove skill.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // GET JOB SEEKER ID
    // =========================

    private int getJobSeekerId() {

        String sql =
                "SELECT id FROM Job_Seeker WHERE user_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    user.getId()
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt("id");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}