package com.joysis.tvi.JobFit.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;

public abstract class BaseTableFrame extends JFrame {

    protected JPanel mainPanel;
    protected JPanel toolbarPanel;
    protected JPanel tableContainer;

    public BaseTableFrame(
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

        createBaseLayout(
                pageTitle,
                description
        );
    }

    private void createBaseLayout(
            String pageTitle,
            String description
    ) {

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

        // HEADER
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
                new JLabel(pageTitle);

        title.setFont(
                UITheme.PAGE_TITLE
        );

        title.setForeground(
                UITheme.TEXT
        );

        JLabel subtitle =
                new JLabel(description);

        subtitle.setFont(
                UITheme.BODY
        );

        subtitle.setForeground(
                UITheme.TEXT_SECONDARY
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        header.add(title);

        header.add(
                Box.createVerticalStrut(
                        UITheme.GAP_XS
                )
        );

        header.add(subtitle);

        // TOOLBAR
        toolbarPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                UITheme.GAP_SM,
                                0
                        )
                );

        toolbarPanel.setOpaque(false);

        toolbarPanel.setBorder(
                new EmptyBorder(
                        UITheme.GAP_LG,
                        0,
                        UITheme.GAP_MD,
                        0
                )
        );

        // TABLE CARD
        tableContainer =
                new RoundedPanel(
                        UITheme.CARD_RADIUS
                );

        tableContainer.setBackground(
                UITheme.SURFACE
        );

        tableContainer.setLayout(
                new BorderLayout()
        );

        tableContainer.setBorder(
                new EmptyBorder(
                        UITheme.CARD_PADDING,
                        UITheme.CARD_PADDING,
                        UITheme.CARD_PADDING,
                        UITheme.CARD_PADDING
                )
        );

        mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setOpaque(false);

        mainPanel.add(
                toolbarPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                tableContainer,
                BorderLayout.CENTER
        );

        root.add(
                header,
                BorderLayout.NORTH
        );

        root.add(
                mainPanel,
                BorderLayout.CENTER
        );

        setContentPane(root);
    }

    protected void styleTable(
            JTable table
    ) {

        table.setFont(
                UITheme.BODY
        );

        table.setForeground(
                UITheme.TEXT
        );

        table.setBackground(
                Color.WHITE
        );

        table.setSelectionBackground(
                UITheme.LIGHT_BLUE
        );

        table.setSelectionForeground(
                UITheme.TEXT
        );

        table.setRowHeight(40);

        table.setShowVerticalLines(false);

        table.setGridColor(
                UITheme.BORDER
        );

        table.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );

        JTableHeader header =
                table.getTableHeader();

        header.setFont(
                UITheme.LABEL
        );

        header.setForeground(
                UITheme.TEXT
        );

        header.setBackground(
                new Color(
                        236,
                        244,
                        253
                )
        );

        header.setPreferredSize(
                new Dimension(
                        header.getWidth(),
                        42
                )
        );
    }
}