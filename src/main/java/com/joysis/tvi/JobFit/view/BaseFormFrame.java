package com.joysis.tvi.JobFit.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class BaseFormFrame extends JFrame {

    protected JPanel formPanel;
    protected JPanel contentPanel;

    public BaseFormFrame(
            String windowTitle,
            String pageTitle,
            String description,
            int width,
            int height
    ) {

        setTitle(windowTitle);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        createBaseLayout(
                pageTitle,
                description
        );
    }

    private void createBaseLayout(
            String pageTitle,
            String description
    ) {

        // ==========================================
        // ROOT
        // ==========================================

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(
                UITheme.BACKGROUND
        );

        root.setBorder(
                new EmptyBorder(
                        UITheme.PAGE_PADDING,
                        UITheme.PAGE_PADDING,
                        UITheme.PAGE_PADDING,
                        UITheme.PAGE_PADDING
                )
        );

        // ==========================================
        // PAGE HEADER
        // ==========================================

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        pageTitle
                );

        title.setFont(
                UITheme.PAGE_TITLE
        );

        title.setForeground(
                UITheme.TEXT
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        description
                );

        subtitle.setFont(
                UITheme.BODY
        );

        subtitle.setForeground(
                UITheme.TEXT_SECONDARY
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        header.add(
                title
        );

        header.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        header.add(
                subtitle
        );

        // ==========================================
        // FORM CARD
        // ==========================================

        RoundedPanel card =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        card.setBackground(
                UITheme.SURFACE
        );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        UITheme.FORM_PADDING,
                        UITheme.FORM_PADDING,
                        UITheme.FORM_PADDING,
                        UITheme.FORM_PADDING
                )
        );

        // ==========================================
        // FORM PANEL
        // ==========================================

        formPanel =
                new JPanel();

        formPanel.setOpaque(false);

        formPanel.setLayout(
                new BoxLayout(
                        formPanel,
                        BoxLayout.Y_AXIS
                )
        );

        card.add(
                formPanel,
                BorderLayout.CENTER
        );

        // ==========================================
        // CONTENT WRAPPER
        // ==========================================

        contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setOpaque(false);

        contentPanel.setBorder(
                new EmptyBorder(
                        UITheme.GAP_LG,
                        0,
                        0,
                        0
                )
        );

        contentPanel.add(card, BorderLayout.CENTER);
        // ==========================================
        // FINAL LAYOUT
        // ==========================================

        root.add(header, BorderLayout.NORTH);

        root.add(contentPanel, BorderLayout.CENTER);

        setContentPane(root);
    }
}