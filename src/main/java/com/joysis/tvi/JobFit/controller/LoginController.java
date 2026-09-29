package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.User;
import com.joysis.tvi.JobFit.service.LoginService;

public class LoginController {

    private final LoginService loginService;

    public LoginController() {
        loginService = new LoginService();
    }

    public User login(String username, String password) {
        return loginService.login(username, password);
    }
}