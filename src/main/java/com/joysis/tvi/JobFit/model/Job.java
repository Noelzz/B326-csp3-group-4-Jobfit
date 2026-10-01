package com.joysis.tvi.JobFit.model;

public class Job {

    private int id;
    private int employerId;
    private int categoryId;
    private String title;
    private String description;
    private String location;
    private double salary;

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

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}