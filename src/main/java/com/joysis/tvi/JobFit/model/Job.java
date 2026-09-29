package com.joysis.tvi.JobFit.model;

public class Job {

    private int id;
    private int employerId;
    private int categoryId;
    private String title;
    private String description;
    private String location;
    private double salary;

    public Job() {
    }

    public Job(
            int id,
            int employerId,
            int categoryId,
            String title,
            String description,
            String location,
            double salary) {

        this.id = id;
        this.employerId = employerId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public int getEmployerId() {
        return employerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public double getSalary() {
        return salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return title;
    }
}