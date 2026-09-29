package com.joysis.tvi.JobFit.controller;

import com.joysis.tvi.JobFit.model.JobCategory;
import com.joysis.tvi.JobFit.service.JobCategoryService;

import java.util.List;

public class JobCategoryController {

    private final JobCategoryService service;

    public JobCategoryController() {

        service =
                new JobCategoryService();
    }

    // =========================
    // GET ALL CATEGORIES
    // =========================

    public List<JobCategory> getAllCategories() {

        return service.getAllCategories();
    }

    // =========================
    // ADD CATEGORY
    // =========================

    public boolean addCategory(
            String name,
            String description) {

        return service.addCategory(
                name,
                description
        );
    }

    // =========================
    // UPDATE CATEGORY
    // =========================

    public boolean updateCategory(
            int id,
            String name,
            String description) {

        return service.updateCategory(
                id,
                name,
                description
        );
    }

    // =========================
    // DELETE CATEGORY
    // =========================

    public boolean deleteCategory(int id) {

        return service.deleteCategory(id);
    }
}