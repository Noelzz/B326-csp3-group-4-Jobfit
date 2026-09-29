package com.joysis.tvi.JobFit.model;

public class JobSeeker {

    private int id;
    private int userId;
    private String fullName;
    private String email;
    private String phone;

    public JobSeeker() {
    }

    public JobSeeker(
            int id,
            int userId,
            String fullName,
            String email,
            String phone) {

        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}