package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.config.DatabaseConnection;
import com.joysis.tvi.JobFit.controller.SkillController;
import com.joysis.tvi.JobFit.model.Skill;
import com.joysis.tvi.JobFit.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class JobSeekerSkillsFrame extends BaseTableFrame {

    private final User user;
    private final SkillController controller;

    private JComboBox<Skill> skillComboBox;
    private JTable skillsTable;
    private DefaultTableModel tableModel;

    private JButton addButton;
    private JButton addNewSkillButton;
    private JButton removeButton;
    private JButton refreshButton;
    private JButton closeButton;

    public JobSeekerSkillsFrame(User user) {

        super(
                "JobFit - Manage Skills",
                "Manage My Skills",
                "Add or remove skills from your profile.",
                900,
                620
        );

        this.user = user;
        this.controller = new SkillController();

        createContent();

        loadSkills();
        loadJobSeekerSkills();
    }

    // ==========================================
    // MAIN CONTENT
    // ==========================================

    private void createContent() {

        toolbarPanel.setLayout(
                new BorderLayout()
        );

        // ==========================================
        // SKILL SELECTION CARD
        // ==========================================

        RoundedPanel selectionCard =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        selectionCard.setBackground(
                UITheme.SURFACE_BLUE
        );

        selectionCard.setLayout(
                new BorderLayout(
                        UITheme.GAP_MD,
                        0
                )
        );

        selectionCard.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel sectionTitle =
                new JLabel(
                        "Add a Skill"
                );

        sectionTitle.setFont(
                UITheme.SECTION_TITLE
        );

        sectionTitle.setForeground(
                UITheme.TEXT
        );

        JLabel sectionInfo =
                new JLabel(
                        "Choose an existing skill or create a new one."
                );

        sectionInfo.setFont(
                UITheme.SMALL
        );

        sectionInfo.setForeground(
                UITheme.TEXT_SECONDARY
        );

        sectionTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        sectionInfo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        textPanel.add(
                sectionTitle
        );

        textPanel.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        textPanel.add(
                sectionInfo
        );

        skillComboBox =
                new JComboBox<>();

        styleComboBox(
                skillComboBox
        );

        JPanel selectorPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        selectorPanel.setOpaque(false);

        selectorPanel.add(
                skillComboBox,
                BorderLayout.CENTER
        );

        addButton =
                new RoundedButton(
                        "Add Skill",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        addButton.setPreferredSize(
                new Dimension(
                        120,
                        40
                )
        );

        selectorPanel.add(
                addButton,
                BorderLayout.EAST
        );

        JPanel topCardContent =
                new JPanel(
                        new BorderLayout(
                                0,
                                14
                        )
                );

        topCardContent.setOpaque(false);

        topCardContent.add(
                textPanel,
                BorderLayout.NORTH
        );

        topCardContent.add(
                selectorPanel,
                BorderLayout.CENTER
        );

        selectionCard.add(
                topCardContent,
                BorderLayout.CENTER
        );

        toolbarPanel.add(
                selectionCard,
                BorderLayout.CENTER
        );

        // ==========================================
        // TABLE MODEL
        // ==========================================

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
                            int column
                    ) {
                        return false;
                    }
                };

        skillsTable =
                new JTable(
                        tableModel
                );

        skillsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        styleTable(
                skillsTable
        );

        skillsTable.setRowHeight(
                42
        );

        skillsTable.setFillsViewportHeight(
                true
        );

        skillsTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        100
                );

        skillsTable
                .getColumnModel()
                .getColumn(0)
                .setMaxWidth(
                        120
                );

        skillsTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        600
                );

        // ==========================================
        // TABLE SCROLL
        // ==========================================

        JScrollPane scrollPane =
                new JScrollPane(
                        skillsTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getViewport()
                .setBackground(
                        Color.WHITE
                );

        // ==========================================
        // BOTTOM BUTTONS
        // ==========================================

        addNewSkillButton =
                new RoundedButton(
                        "Add New Skill",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        removeButton =
                new RoundedButton(
                        "Remove Skill",
                        UITheme.DANGER,
                        new Color(
                                190,
                                55,
                                65
                        )
                );

        refreshButton =
                new RoundedButton(
                        "Refresh",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        closeButton =
                new RoundedButton(
                        "Close",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
                );

        addNewSkillButton.setPreferredSize(
                new Dimension(
                        135,
                        40
                )
        );

        removeButton.setPreferredSize(
                new Dimension(
                        135,
                        40
                )
        );

        refreshButton.setPreferredSize(
                new Dimension(
                        110,
                        40
                )
        );

        closeButton.setPreferredSize(
                new Dimension(
                        100,
                        40
                )
        );

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
                addNewSkillButton
        );

        buttonPanel.add(
                removeButton
        );

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                closeButton
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

        // ==========================================
        // TABLE SECTION
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
        // ACTIONS
        // ==========================================

        addButton.addActionListener(
                e -> addSkill()
        );

        addNewSkillButton.addActionListener(
                e -> addNewSkill()
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

    // ==========================================
    // COMBO BOX STYLE
    // ==========================================

    private void styleComboBox(
            JComboBox<Skill> comboBox
    ) {

        comboBox.setFont(
                UITheme.BODY
        );

        comboBox.setForeground(
                UITheme.TEXT
        );

        comboBox.setBackground(
                Color.WHITE
        );

        comboBox.setPreferredSize(
                new Dimension(
                        0,
                        UITheme.FIELD_HEIGHT
                )
        );

        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER_BLUE
                )
        );
    }

    // ==========================================
    // LOAD ALL SKILLS
    // ==========================================

    private void loadSkills() {

        skillComboBox.removeAllItems();

        List<Skill> skills =
                controller.getAllSkills();

        for (Skill skill : skills) {

            skillComboBox.addItem(
                    skill
            );
        }
    }

    // ==========================================
    // LOAD JOB SEEKER SKILLS
    // ==========================================

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

    // ==========================================
    // ADD EXISTING SKILL
    // ==========================================

    private void addSkill() {

        Skill selectedSkill =
                (Skill) skillComboBox
                        .getSelectedItem();

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

        List<Skill> existingSkills =
                controller.getJobSeekerSkills(
                        jobSeekerId
                );

        for (Skill skill : existingSkills) {

            if (
                    skill.getId()
                            == selectedSkill.getId()
            ) {

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

    // ==========================================
    // REMOVE SKILL
    // ==========================================

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
                (int) tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        );

        String skillName =
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Remove \""
                                + skillName
                                + "\" from your profile?",
                        "Confirm Remove",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                choice
                        != JOptionPane.YES_OPTION
        ) {
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

    // ==========================================
    // GET JOB SEEKER ID
    // ==========================================

    private int getJobSeekerId() {

        String sql =
                "SELECT id FROM Job_Seeker WHERE user_id = ?";

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
    // ADD NEW SKILL
    // ==========================================

    private void addNewSkill() {

        String name =
                JOptionPane.showInputDialog(
                        this,
                        "Enter new skill name:",
                        "Add New Skill",
                        JOptionPane.PLAIN_MESSAGE
                );

        if (name == null) {
            return;
        }

        name =
                name.trim();

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

        Skill skill =
                controller.findOrCreateSkill(
                        name
                );

        if (skill == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add skill. Please try again.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
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

        List<Skill> existingSkills =
                controller.getJobSeekerSkills(
                        jobSeekerId
                );

        for (Skill s : existingSkills) {

            if (
                    s.getId()
                            == skill.getId()
            ) {

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
                        skill.getId()
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Skill \""
                            + skill.getName()
                            + "\" added successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadSkills();
            loadJobSeekerSkills();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add skill to your profile.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}