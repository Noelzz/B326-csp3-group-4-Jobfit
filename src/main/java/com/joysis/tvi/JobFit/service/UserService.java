package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.User;
import com.joysis.tvi.JobFit.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository repository;

    public UserService() {
        repository = new UserRepository();
    }

    // =========================
    // GET ALL USERS
    // =========================

    public List<User> getAllUsers() {

        return repository.getAllUsers();
    }

    // =========================
    // ADD USER
    // =========================

    public boolean addUser(
            String username,
            String password,
            String role) {

        if (username == null ||
                username.trim().isEmpty()) {

            return false;
        }

        if (password == null ||
                password.trim().isEmpty()) {

            return false;
        }

        if (role == null ||
                role.trim().isEmpty()) {

            return false;
        }

        username = username.trim();
        password = password.trim();
        role = role.trim();

        if (!role.equals("admin") &&
                !role.equals("job_seeker") &&
                !role.equals("employer")) {

            return false;
        }

        if (repository.usernameExists(username)) {
            return false;
        }

        return repository.addUser(
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

        if (id <= 0) {
            return false;
        }

        if (username == null ||
                username.trim().isEmpty()) {

            return false;
        }

        if (password == null ||
                password.trim().isEmpty()) {

            return false;
        }

        if (role == null ||
                role.trim().isEmpty()) {

            return false;
        }

        username = username.trim();
        password = password.trim();
        role = role.trim();

        if (!role.equals("admin") &&
                !role.equals("job_seeker") &&
                !role.equals("employer")) {

            return false;
        }

        if (repository.usernameExistsExceptId(
                username,
                id)) {

            return false;
        }

        return repository.updateUser(
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

        if (id <= 0) {
            return false;
        }

        return repository.deleteUser(id);
    }
}