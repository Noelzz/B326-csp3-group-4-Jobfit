package com.joysis.tvi.JobFit.view;

import java.awt.*;

public class UITheme {

    // ==========================================
    // COLORS
    // ==========================================

    // Main backgrounds
    public static final Color BACKGROUND =
            new Color(244, 248, 253);

    public static final Color SURFACE =
            Color.WHITE;

    public static final Color SURFACE_BLUE =
            new Color(247, 251, 255);

    // Navy
    public static final Color NAVY =
            new Color(8, 35, 72);

    public static final Color NAVY_LIGHT =
            new Color(18, 58, 105);

    // Main blue
    public static final Color BLUE =
            new Color(26, 105, 220);

    public static final Color BLUE_HOVER =
            new Color(20, 88, 190);

    // Accent
    public static final Color CYAN =
            new Color(28, 185, 220);

    public static final Color LIGHT_BLUE =
            new Color(225, 240, 253);

    // Borders
    public static final Color BORDER =
            new Color(215, 226, 240);

    public static final Color BORDER_BLUE =
            new Color(180, 210, 240);

    // Text
    public static final Color TEXT =
            new Color(20, 43, 78);

    public static final Color TEXT_SECONDARY =
            new Color(92, 112, 140);

    public static final Color TEXT_GRAY =
            TEXT_SECONDARY;

    public static final Color TEXT_LIGHT =
            new Color(165, 188, 215);

    // Compatibility colors
    public static final Color CARD =
            SURFACE;

    public static final Color BLUE_LIGHT =
            new Color(40, 170, 255);

    // Status colors
    public static final Color SUCCESS =
            new Color(25, 170, 135);

    public static final Color WARNING =
            new Color(235, 160, 45);

    public static final Color DANGER =
            new Color(220, 70, 80);

    // ==========================================
    // FONTS
    // ==========================================

    public static final Font LOGO =
            new Font("Segoe UI", Font.BOLD, 29);

    public static final Font PAGE_TITLE =
            new Font("Segoe UI", Font.BOLD, 30);

    public static final Font SECTION_TITLE =
            new Font("Segoe UI", Font.BOLD, 20);

    public static final Font CARD_TITLE =
            new Font("Segoe UI", Font.BOLD, 14);

    public static final Font LABEL =
            new Font("Segoe UI", Font.BOLD, 13);

    public static final Font BODY =
            new Font("Segoe UI", Font.PLAIN, 13);

    public static final Font SMALL =
            new Font("Segoe UI", Font.PLAIN, 12);

    public static final Font BUTTON =
            new Font("Segoe UI", Font.BOLD, 13);

    // Compatibility font names
    public static final Font TITLE =
            PAGE_TITLE;

    public static final Font HEADING =
            SECTION_TITLE;

    public static final Font NORMAL =
            BODY;

    // ==========================================
    // SPACING
    // ==========================================

    public static final int GAP_XS = 5;
    public static final int GAP_SM = 8;
    public static final int GAP_MD = 16;
    public static final int GAP_LG = 24;
    public static final int GAP_XL = 32;

    // ==========================================
    // PADDING
    // ==========================================

    public static final int PAGE_PADDING = 32;

    public static final int CARD_PADDING = 22;

    public static final int FORM_PADDING = 28;

    public static final int SIDEBAR_PADDING = 18;

    // ==========================================
    // COMPONENT SIZES
    // ==========================================

    public static final int SIDEBAR_WIDTH = 220;
    public static final int TOPBAR_HEIGHT = 68;
    public static final int FIELD_HEIGHT = 42;
    public static final int BUTTON_HEIGHT = 44;
    public static final int CARD_RADIUS = 18;
    public static final int BUTTON_RADIUS = 14;
    private UITheme() {
    }
}