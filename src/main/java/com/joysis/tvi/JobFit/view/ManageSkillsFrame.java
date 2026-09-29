package com.joysis.tvi.JobFit.view;

import com.joysis.tvi.JobFit.controller.SkillController;
import com.joysis.tvi.JobFit.model.Skill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageSkillsFrame extends JFrame {

    private final SkillController controller;

    private JTable skillsTable;
    private DefaultTableModel tableModel;

    private JTextField skillNameField;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton closeButton;

    public ManageSkillsFrame() {

        controller = new SkillController();

        setTitle("JobFit - Manage Skills");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
        loadSkills();
    }

    private void createGUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "MANAGE SKILLS",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JPanel inputPanel =
                new JPanel(
                        new GridLayout(1, 2, 10, 10)
                );

        JLabel skillLabel =
                new JLabel("Skill Name:");

        skillNameField =
                new JTextField();

        inputPanel.add(skillLabel);
        inputPanel.add(skillNameField);

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

        mainPanel.add(
                topPanel,
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

        skillsTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    int selectedRow =
                            skillsTable.getSelectedRow();

                    if (selectedRow >= 0) {

                        String skillName =
                                tableModel.getValueAt(
                                        selectedRow,
                                        1
                                ).toString();

                        skillNameField.setText(
                                skillName
                        );
                    }
                });
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
                skillNameField.getText().trim();

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
                controller.addSkill(name);

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
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        String name =
                skillNameField.getText().trim();

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
                controller.deleteSkill(skillId);

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
                            + "It may currently be used by a "
                            + "job seeker or job.",
                    "Delete Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}