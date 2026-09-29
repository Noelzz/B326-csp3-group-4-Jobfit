package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.repository.UserRepository;

public class RegisterService {

    private final UserRepository userRepository;

    public RegisterService() {
        userRepository = new UserRepository();
    }

    public boolean registerJobSeeker(
            String username,
            String password,
            String fullName,
            String email,
            String phone) {

        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (password == null || password.isEmpty()) {
            return false;
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        if (userRepository.usernameExists(username)) {
            return false;
        }

        return userRepository.registerJobSeeker(
                username,
                password,
                fullName,
                email,
                phone
        );
    }


    public boolean registerEmployer(
            String username,
            String password,
            String companyName,
            String email,
            String phone) {

        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (password == null || password.isEmpty()) {
            return false;
        }

        if (companyName == null || companyName.trim().isEmpty()) {
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        if (userRepository.usernameExists(username)) {
            return false;
        }

        return userRepository.registerEmployer(
                username,
                password,
                companyName,
                email,
                phone
        );
    }
}