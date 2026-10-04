package com.joysis.tvi.JobFit.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class BaseDashboardFrame extends JFrame {

    protected JPanel sidebar;
    protected JPanel contentPanel;

    protected final String username;
    protected final String panelName;

    public BaseDashboardFrame(
            String windowTitle,
            String username,
            String panelName
    ) {

        this.username = username;
        this.panelName = panelName;

        setTitle(windowTitle);
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createBaseLayout();
    }

    private void createBaseLayout() {

        JPanel root = new JPanel(new BorderLayout());

        root.setBackground(
                UITheme.BACKGROUND
        );

        // =========================
        // SIDEBAR
        // =========================

        sidebar = new JPanel();

        sidebar.setPreferredSize(
                new Dimension(
                        UITheme.SIDEBAR_WIDTH,
                        0
                )
        );

        sidebar.setBackground(
                UITheme.NAVY
        );

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.setBorder(
                new EmptyBorder(
                        28,
                        UITheme.SIDEBAR_PADDING,
                        25,
                        UITheme.SIDEBAR_PADDING
                )
        );

        JLabel brand = new JLabel("JOBFIT");

        brand.setFont(
                UITheme.LOGO
        );

        brand.setForeground(
                UITheme.BLUE_LIGHT
        );

        brand.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel panelLabel =
                new JLabel(panelName);

        panelLabel.setFont(
                UITheme.SMALL
        );

        panelLabel.setForeground(
                UITheme.TEXT_LIGHT
        );

        panelLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        sidebar.add(brand);

        sidebar.add(
                Box.createVerticalStrut(4)
        );

        sidebar.add(panelLabel);

        sidebar.add(
                Box.createVerticalStrut(30)
        );

        // =========================
        // RIGHT SIDE
        // =========================

        JPanel rightPanel =
                new JPanel(
                        new BorderLayout()
                );

        rightPanel.setBackground(
                UITheme.BACKGROUND
        );

        // =========================
        // TOP BAR
        // =========================

        JPanel topBar =
                createTopBar();

        rightPanel.add(
                topBar,
                BorderLayout.NORTH
        );

        // =========================
        // CONTENT PANEL
        // =========================

        contentPanel = new JPanel();

        contentPanel.setOpaque(false);

        contentPanel.setLayout(
                new BoxLayout(
                        contentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        contentPanel.setBorder(
                new EmptyBorder(
                        UITheme.PAGE_PADDING,
                        UITheme.PAGE_PADDING,
                        UITheme.PAGE_PADDING,
                        UITheme.PAGE_PADDING
                )
        );

        rightPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        // =========================
        // ROOT
        // =========================

        root.add(
                sidebar,
                BorderLayout.WEST
        );

        root.add(
                rightPanel,
                BorderLayout.CENTER
        );

        setContentPane(root);
    }

    private JPanel createTopBar() {

        JPanel topBar = new JPanel(new BorderLayout());

        topBar.setBackground(Color.WHITE);

        topBar.setPreferredSize(
                new Dimension(0, UITheme.TOPBAR_HEIGHT)
        );

        topBar.setBorder(
                new EmptyBorder(12, 25, 12, 25)
        );

        JLabel userLabel = new JLabel(username);

        userLabel.setFont(UITheme.LABEL);
        userLabel.setForeground(UITheme.TEXT);
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        topBar.add(userLabel, BorderLayout.EAST);

        return topBar;
    }

    protected JButton addSidebarButton(
            String text,
            boolean active
    ) {

        JButton button =
                new JButton(text);

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        button.setPreferredSize(
                new Dimension(
                        UITheme.SIDEBAR_WIDTH,
                        45
                )
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setFont(
                UITheme.BODY
        );

        button.setForeground(
                active
                        ? Color.WHITE
                        : new Color(
                        220,
                        232,
                        245
                )
        );

        button.setBackground(
                active
                        ? UITheme.BLUE
                        : UITheme.NAVY
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        sidebar.add(button);

        sidebar.add(
                Box.createVerticalStrut(
                        UITheme.GAP_SM
                )
        );

        return button;
    }

    protected void addSidebarGlue() {

        sidebar.add(
                Box.createVerticalGlue()
        );
    }

    protected void createPageHeader(
            String title,
            String description
    ) {

        JLabel welcome =
                new JLabel(
                        "Welcome back,"
                );

        welcome.setFont(
                UITheme.BODY
        );

        welcome.setForeground(
                UITheme.TEXT_SECONDARY
        );

        welcome.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                UITheme.PAGE_TITLE
        );

        titleLabel.setForeground(
                UITheme.TEXT
        );

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel descriptionLabel =
                new JLabel(description);

        descriptionLabel.setFont(
                UITheme.BODY
        );

        descriptionLabel.setForeground(
                UITheme.TEXT_SECONDARY
        );

        descriptionLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(welcome);

        contentPanel.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        contentPanel.add(titleLabel);

        contentPanel.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        contentPanel.add(
                descriptionLabel
        );

        contentPanel.add(
                Box.createVerticalStrut(
                        UITheme.GAP_LG
                )
        );
    }
}