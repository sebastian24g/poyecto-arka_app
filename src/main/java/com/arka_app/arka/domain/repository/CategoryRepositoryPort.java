package com.arka_app.arka.domain.repository;

import com.arka_app.arka.domain.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    List<Category> findAll();
    void deleteById(Long id);
}

