package com.joysis.tvi.JobFit.model;

public class Employer {

    private int id;
    private int userId;
    private String companyName;
    private String email;
    private String phone;

    public Employer() {
    }

    public Employer(
            int id,
            int userId,
            String companyName,
            String email,
            String phone) {

        this.id = id;
        this.userId = userId;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getCompanyName() {
        return companyName;
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}