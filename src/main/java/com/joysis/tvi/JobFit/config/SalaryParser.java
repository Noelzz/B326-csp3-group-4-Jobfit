package com.joysis.tvi.JobFit.config;

public class SalaryParser {

    public static double parse(String input) {

        if (input == null || input.trim().isEmpty()) {
            return -1;
        }

        // Clean the input
        String cleaned = input.toLowerCase()
                .replace("php", "")
                .replace("₱", "")
                .replace(" ", "")
                .replace(",", "")
                .trim();

        int dashIndex = cleaned.indexOf('-');
        if (dashIndex > 0) {
            cleaned = cleaned.substring(0, dashIndex);
        }

        double multiplier = 1;
        if (cleaned.endsWith("k")) {
            multiplier = 1000;
            cleaned = cleaned.substring(0, cleaned.length() - 1);
        }

        // Parse the numeric parts
        try {
            double value = Double.parseDouble(cleaned);
            if (value < 0) return -1;
            return value * multiplier;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}