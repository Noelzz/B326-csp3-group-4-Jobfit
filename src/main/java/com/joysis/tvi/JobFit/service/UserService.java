package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.config.PasswordHashGenerator;
import com.joysis.tvi.JobFit.config.PasswordValidator;
import com.joysis.tvi.JobFit.model.User;
import com.joysis.tvi.JobFit.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository repository;

    public UserService() {
        repository = new UserRepository();
    }

    // Get all users
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    // Add User
    public String addUser(String username, String password, String role) {

        if (!PasswordValidator.isValidUsername(username)) {
            return "Invalid username. Use 3-50 letters, numbers, or underscores.";
        }
        if (!PasswordValidator.isValidPassword(password)) {
            return "Invalid password. Must be at least 8 characters with a letter and a number.";
        }
        if (role == null || (!role.equals("admin")
                && !role.equals("job_seeker")
                && !role.equals("employer"))) {
            return "Invalid role. Must be admin, job_seeker, or employer.";
        }
        if (repository.usernameExists(username.trim())) {
            return "Username is already taken.";
        }

        boolean created = repository.addUser(
                username.trim(), password, role
        );

        return created ? null : "Failed to create user due to a database error.";
    }

    // Update User
    public String updateUser(int id, String username, String password, String role) {

        if (id <= 0) {
            return "Invalid user ID.";
        }
        if (!PasswordValidator.isValidUsername(username)) {
            return "Invalid username. Use 3-50 letters, numbers, or underscores.";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty.";
        }

        if (!PasswordHashGenerator.isBcryptHash(password)
                && !PasswordValidator.isValidPassword(password)) {
            return "Invalid password. Must be at least 8 characters with a letter and a number.";
        }
        if (role == null || (!role.equals("admin")
                && !role.equals("job_seeker")
                && !role.equals("employer"))) {
            return "Invalid role. Must be admin, job_seeker, or employer.";
        }
        if (repository.usernameExistsExceptId(username.trim(), id)) {
            return "Username is already taken.";
        }

        boolean updated = repository.updateUser(
                id, username.trim(), password, role
        );

        return updated ? null : "Failed to update user due to a database error.";
    }

    // Delete User
    public String deleteUser(int id) {
        if (id <= 0) {
            return "Invalid user ID.";
        }

        boolean deleted = repository.deleteUser(id);
        return deleted ? null : "Failed to delete user.";
    }

    // Change Password
    public String changePassword(int userId, String currentPassword, String newPassword, String confirmPassword) {

        if (userId <= 0) {
            return "Invalid user ID.";
        }
        if (currentPassword == null || currentPassword.isEmpty()) {
            return "Current password is required.";
        }
        if (newPassword == null || confirmPassword == null) {
            return "New password and confirmation are required.";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "New password and confirmation do not match.";
        }
        if (!PasswordValidator.isValidPassword(newPassword)) {
            return "New password must be at least 8 characters with a letter and a number.";
        }
        if (currentPassword.equals(newPassword)) {
            return "New password must be different from the current password.";
        }
        if (!repository.verifyPassword(userId, currentPassword)) {
            return "Current password is incorrect.";
        }

        boolean updated = repository.updatePassword(userId, newPassword);
        return updated ? null : "Failed to update password. Please try again.";
    }
}