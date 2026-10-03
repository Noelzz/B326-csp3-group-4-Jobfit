package com.joysis.tvi.JobFit;

import com.joysis.tvi.JobFit.view.LoginFrame;

import javax.swing.*;

public class App {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame = new LoginFrame();

            loginFrame.setVisible(true);
        }
        );
    }
}