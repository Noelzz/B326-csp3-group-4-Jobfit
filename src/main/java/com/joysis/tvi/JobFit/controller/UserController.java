package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.User;
import com.joysis.tvi.JobFit.service.UserService;

import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public String addUser(String username, String password, String role) {
        return userService.addUser(username, password, role);
    }

    public String updateUser(int id, String username, String password, String role) {
        return userService.updateUser(id, username, password, role);
    }

    public String deleteUser(int id) {
        return userService.deleteUser(id);
    }

    public String changePassword(int userId, String currentPassword, String newPassword, String confirmPassword) {
        return userService.changePassword(userId, currentPassword, newPassword, confirmPassword);
    }
}