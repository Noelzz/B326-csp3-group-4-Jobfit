package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.SkillController;
import com.joysis.tvi.JobFit.model.Skill;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageSkillsFrame extends BaseTableFrame {

    private final SkillController controller;

    private JTable skillsTable;
    private DefaultTableModel tableModel;
    private JTextField skillNameField;

    public ManageSkillsFrame() {

        super(
                "JobFit - Manage Skills",
                "Manage Skills",
                "Add, update and organize skills used across JobFit.",
                900,
                720
        );

        this.controller = new SkillController();

        setResizable(true);

        createContent();
        loadSkills();
    }

    private void createContent() {

        // ==========================================
        // FORM CARD
        // ==========================================

        RoundedPanel formCard =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        formCard.setBackground(
                UITheme.SURFACE
        );

        formCard.setLayout(
                new BorderLayout()
        );

        formCard.setBorder(
                new EmptyBorder(
                        18,
                        24,
                        18,
                        24
                )
        );

        formCard.setPreferredSize(
                new Dimension(
                        0,
                        140
                )
        );

        JPanel formContent =
                new JPanel();

        formContent.setOpaque(false);

        formContent.setLayout(
                new BoxLayout(
                        formContent,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel skillLabel =
                new JLabel(
                        "Skill Name"
                );

        skillLabel.setFont(
                UITheme.LABEL
        );

        skillLabel.setForeground(
                UITheme.TEXT
        );

        skillLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel helperLabel =
                new JLabel(
                        "Enter the skill you want to add or update."
                );

        helperLabel.setFont(
                UITheme.SMALL
        );

        helperLabel.setForeground(
                UITheme.TEXT_SECONDARY
        );

        helperLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        skillNameField =
                new JTextField();

        styleField(
                skillNameField
        );

        skillNameField.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        formContent.add(
                skillLabel
        );

        formContent.add(
                Box.createVerticalStrut(
                        5
                )
        );

        formContent.add(
                helperLabel
        );

        formContent.add(
                Box.createVerticalStrut(
                        14
                )
        );

        formContent.add(
                skillNameField
        );

        formCard.add(
                formContent,
                BorderLayout.CENTER
        );

        toolbarPanel.setLayout(
                new BorderLayout()
        );

        toolbarPanel.add(
                formCard,
                BorderLayout.CENTER
        );

        // ==========================================
        // TABLE MODEL
        // ==========================================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
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
                40
        );

        skillsTable.setFillsViewportHeight(
                true
        );

        skillsTable.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );

        // ==========================================
        // COLUMN WIDTHS
        // ==========================================

        skillsTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        80
                );

        skillsTable
                .getColumnModel()
                .getColumn(0)
                .setMaxWidth(
                        90
                );

        skillsTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        700
                );

        // ==========================================
        // SCROLL PANE
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
// BUTTONS
// ==========================================

        JButton addButton =
                new RoundedButton(
                        "Add Skill",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        JButton updateButton =
                new RoundedButton(
                        "Update Skill",
                        UITheme.BLUE,
                        UITheme.BLUE_HOVER
                );

        JButton deleteButton =
                new RoundedButton(
                        "Delete Skill",
                        UITheme.DANGER,
                        new Color(190, 55, 65)
                );

        JButton refreshButton =
                new RoundedButton(
                        "Refresh List",
                        UITheme.NAVY_LIGHT,
                        UITheme.NAVY
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

        addButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        refreshButton.setPreferredSize(buttonSize);
        closeButton.setPreferredSize(buttonSize);

// ==========================================
// BUTTON PANEL
// SAME STYLE AS JOBS & CATEGORIES
// ==========================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

// ==========================================
// BOTTOM PANEL
// ==========================================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        bottomPanel.setBorder(
                new EmptyBorder(
                        10,
                        0,
                        2,
                        0
                )
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.EAST
        );

// ==========================================
// TABLE WRAPPER
// ==========================================

        JPanel tableWrapper =
                new JPanel(
                        new BorderLayout()
                );

        tableWrapper.setOpaque(false);

        tableWrapper.setBorder(
                new EmptyBorder(
                        0,
                        16,
                        0,
                        16
                )
        );

        tableWrapper.add(
                scrollPane,
                BorderLayout.CENTER
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
                tableWrapper,
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

        updateButton.addActionListener(
                e -> updateSkill()
        );

        deleteButton.addActionListener(
                e -> deleteSkill()
        );

        refreshButton.addActionListener(
                e -> loadSkills()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
        skillsTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                int selectedRow =
                                        skillsTable.getSelectedRow();

                                if (selectedRow >= 0) {

                                    String skillName =
                                            tableModel
                                                    .getValueAt(
                                                            selectedRow,
                                                            1
                                                    )
                                                    .toString();

                                    skillNameField.setText(
                                            skillName
                                    );
                                }
                            }
                        }
                );
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
                        0,
                        UITheme.FIELD_HEIGHT
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
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

    private void loadSkills() {

        tableModel.setRowCount(0);

        skillNameField.setText("");

        List<Skill> skills =
                controller.getAllSkills();

        for (Skill skill : skills) {

            tableModel.addRow(
                    new Object[]{
                            skill.getId(),
                            skill.getName()
                    }
            );
        }
    }

    private void addSkill() {

        String name =
                skillNameField
                        .getText()
                        .trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a skill name.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean success =
                controller.addSkill(
                        name
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Skill added successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadSkills();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add skill.\n"
                            + "The skill may already exist.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateSkill() {

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

        String name =
                skillNameField
                        .getText()
                        .trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a skill name.",
                    "JobFit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean success =
                controller.updateSkill(
                        skillId,
                        name
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Skill updated successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadSkills();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update skill.",
                    "JobFit",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteSkill() {

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
                        "Are you sure you want to delete \""
                                + skillName
                                + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success =
                controller.deleteSkill(
                        skillId
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Skill deleted successfully.",
                    "JobFit",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadSkills();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete this skill.\n"
                            + "It may currently be used by a job seeker or job.",
                    "Delete Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}