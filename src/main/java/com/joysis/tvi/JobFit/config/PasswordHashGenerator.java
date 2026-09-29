package com.joysis.tvi.JobFit.config;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashGenerator {

    public static void main(String[] args) {

        String password = "admin123";

        String hashedPassword =
                BCrypt.hashpw(
                        password,
                        BCrypt.gensalt()
                );

        System.out.println(
                "BCrypt Hash:"
        );

        System.out.println(
                hashedPassword
        );
    }
}