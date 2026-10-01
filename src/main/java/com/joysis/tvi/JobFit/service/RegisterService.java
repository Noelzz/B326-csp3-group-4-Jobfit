package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.config.PasswordValidator;
import com.joysis.tvi.JobFit.repository.UserRepository;

public class RegisterService {

    private final UserRepository userRepository;

    public RegisterService() {
        userRepository = new UserRepository();
    }

    public String registerJobSeeker(
            String username,
            String password,
            String fullName,
            String email,
            String phone) {

        if (!PasswordValidator.isValidUsername(username)) {
            return "Invalid username. Use 3-50 letters, numbers, or underscores.";
        }
        if (!PasswordValidator.isValidPassword(password)) {
            return "Invalid password. Must be at least 8 characters with a letter and a number.";
        }
        if (!PasswordValidator.isValidName(fullName)) {
            return "Invalid full name. Must be 2-100 characters.";
        }
        if (!PasswordValidator.isValidEmail(email)) {
            return "Invalid email format.";
        }
        if (!PasswordValidator.isValidPhone(phone)) {
            return "Invalid phone number. Use digits, optional '+' and dashes.";
        }
        if (userRepository.usernameExists(username.trim())) {
            return "Username is already taken.";
        }
        if (userRepository.emailExistsJobSeeker(email.trim())) {
            return "Email is already registered.";
        }

        boolean created = userRepository.registerJobSeeker(
                username.trim(),
                password,
                fullName.trim(),
                email.trim(),
                phone == null ? null : phone.trim()
        );

        return created ? null : "Registration failed due to a database error.";
    }

    public String registerEmployer(
            String username,
            String password,
            String companyName,
            String email,
            String phone) {

        if (!PasswordValidator.isValidUsername(username)) {
            return "Invalid username. Use 3-50 letters, numbers, or underscores.";
        }
        if (!PasswordValidator.isValidPassword(password)) {
            return "Invalid password. Must be at least 8 characters with a letter and a number.";
        }
        if (!PasswordValidator.isValidName(companyName)) {
            return "Invalid company name. Must be 2-100 characters.";
        }
        if (!PasswordValidator.isValidEmail(email)) {
            return "Invalid email format.";
        }
        if (!PasswordValidator.isValidPhone(phone)) {
            return "Invalid phone number. Use digits, optional '+' and dashes.";
        }
        if (userRepository.usernameExists(username.trim())) {
            return "Username is already taken.";
        }
        if (userRepository.emailExistsEmployer(email.trim())) {
            return "Email is already registered.";
        }

        boolean created = userRepository.registerEmployer(
                username.trim(),
                password,
                companyName.trim(),
                email.trim(),
                phone == null ? null : phone.trim()
        );

        return created ? null : "Registration failed due to a database error.";
    }
}