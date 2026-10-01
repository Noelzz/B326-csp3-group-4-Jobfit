package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.Admin;
import com.joysis.tvi.JobFit.service.AdminService;

public class AdminController {

    private final AdminService service;

    public AdminController() {
        service = new AdminService();
    }

    public Admin getProfile(int userId) {
        return service.getProfile(userId);
    }

    public String saveProfile(int userId, String email, String phone) {
        return service.saveProfile(userId, email, phone);
    }
}