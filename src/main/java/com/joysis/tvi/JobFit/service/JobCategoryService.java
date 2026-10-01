package com.joysis.tvi.JobFit.service;

import com.joysis.tvi.JobFit.model.JobCategory;
import com.joysis.tvi.JobFit.repository.JobCategoryRepository;

import java.util.List;

public class JobCategoryService {

    private final JobCategoryRepository repository;

    public JobCategoryService() {

        repository =
                new JobCategoryRepository();
    }

    // =========================
    // GET ALL CATEGORIES
    // =========================

    public List<JobCategory> getAllCategories() {

        return repository.getAllCategories();
    }

    // =========================
    // ADD CATEGORY
    // =========================

    public boolean addCategory(
            String name,
            String description) {

        if (name == null ||
                name.trim().isEmpty()) {

            return false;
        }

        if (description == null) {
            description = "";
        }

        name = name.trim();
        description = description.trim();

        if (name.length() > 100) {
            return false;
        }

        return repository.addCategory(
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

        if (id <= 0) {
            return false;
        }

        if (name == null ||
                name.trim().isEmpty()) {

            return false;
        }

        if (description == null) {
            description = "";
        }

        name = name.trim();
        description = description.trim();

        if (name.length() > 100) {
            return false;
        }

        return repository.updateCategory(
                id,
                name,
                description
        );
    }

    // =========================
    // DELETE CATEGORY
    // =========================

    public boolean deleteCategory(int id) {

        if (id <= 0) {
            return false;
        }

        return repository.deleteCategory(id);
    }
}