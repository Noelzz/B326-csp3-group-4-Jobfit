package com.joysis.tvi.JobFit.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IconLoader {

    public static ImageIcon load(String fileName, int width, int height) {

        URL url = IconLoader.class.getResource("/icons/" + fileName);

        if (url == null) {
            System.out.println("Icon not found: " + fileName);
            return null;
        }

        ImageIcon original = new ImageIcon(url);

        Image scaled = original.getImage().getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(scaled);
    }
}