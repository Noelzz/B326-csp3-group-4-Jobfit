package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.config.PasswordValidator;
import com.joysis.tvi.JobFit.model.Admin;
import com.joysis.tvi.JobFit.repository.AdminRepository;

public class AdminService {

    private final AdminRepository repository;

    public AdminService() {
        repository = new AdminRepository();
    }

    public Admin getProfile(int userId) {
        return repository.getAdminByUserId(userId);
    }

    public String saveProfile(int userId, String email, String phone) {

        if (!PasswordValidator.isValidEmail(email)) {
            return "Invalid email format.";
        }
        if (!PasswordValidator.isValidPhone(phone)) {
            return "Invalid phone number.";
        }

        boolean saved = repository.saveAdminProfile(userId, email, phone);
        return saved ? null : "Failed to save admin profile.";
    }
}