package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.service.RegisterService;

public class RegisterController {

    private final RegisterService registerService;

    public RegisterController() {
        registerService = new RegisterService();
    }

    public String registerJobSeeker(
            String username,
            String password,
            String fullName,
            String email,
            String phone) {

        return registerService.registerJobSeeker(
                username, password, fullName, email, phone
        );
    }

    public String registerEmployer(
            String username,
            String password,
            String companyName,
            String email,
            String phone) {

        return registerService.registerEmployer(
                username, password, companyName, email, phone
        );
    }
}