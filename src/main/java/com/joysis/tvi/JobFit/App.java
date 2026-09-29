package com.joysis.tvi.JobFit;

import com.joysis.tvi.JobFit.view.LoginFrame;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);
        });
    }
}