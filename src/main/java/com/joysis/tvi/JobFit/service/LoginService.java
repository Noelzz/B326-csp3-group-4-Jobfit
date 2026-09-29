package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.User;
import com.joysis.tvi.JobFit.repository.UserRepository;

public class LoginService {

    private final UserRepository userRepository;

    public LoginService() {
        userRepository = new UserRepository();
    }

    public User login(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.isEmpty()) {
            return null;
        }

        return userRepository.login(username, password);
    }
}