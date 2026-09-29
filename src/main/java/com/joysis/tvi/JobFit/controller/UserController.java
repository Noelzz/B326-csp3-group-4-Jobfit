package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.User;
import com.joysis.tvi.JobFit.service.UserService;

import java.util.List;

public class UserController {

    private final UserService service;

    public UserController() {
        service = new UserService();
    }

    // =========================
    // GET ALL USERS
    // =========================

    public List<User> getAllUsers() {

        return service.getAllUsers();
    }

    // =========================
    // ADD USER
    // =========================

    public boolean addUser(
            String username,
            String password,
            String role) {

        return service.addUser(
                username,
                password,
                role
        );
    }

    // =========================
    // UPDATE USER
    // =========================

    public boolean updateUser(
            int id,
            String username,
            String password,
            String role) {

        return service.updateUser(
                id,
                username,
                password,
                role
        );
    }

    // =========================
    // DELETE USER
    // =========================

    public boolean deleteUser(int id) {

        return service.deleteUser(id);
    }
}