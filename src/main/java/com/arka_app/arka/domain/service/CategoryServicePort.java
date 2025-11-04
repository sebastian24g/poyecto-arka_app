package com.arka_app.arka.domain.service;


import com.arka_app.arka.domain.model.Category;
import java.util.List;

public interface CategoryServicePort {
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    Category getCategory(Long id);
    List<Category> listCategories();
    void deleteCategory(Long id);
}
