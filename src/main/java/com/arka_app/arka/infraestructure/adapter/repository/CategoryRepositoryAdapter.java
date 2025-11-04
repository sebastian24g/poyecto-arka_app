package com.arka_app.arka.infraestructure.adapter.repository;


import com.arka_app.arka.domain.model.Category;
import com.arka_app.arka.domain.repository.CategoryRepositoryPort;
import com.arka_app.arka.infraestructure.percistence.entity.CategoryEntity;
import com.arka_app.arka.infraestructure.percistence.mapper.CategoryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final JpaCategoryRepository jpa;

    @Override
    public Category save(Category category) {
        CategoryEntity entity = CategoryEntityMapper.toEntity(category);
        CategoryEntity saved = jpa.save(entity);
        return CategoryEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return jpa.findById(id).map(CategoryEntityMapper::toDomain);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return jpa.findByName(name).map(CategoryEntityMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return CategoryEntityMapper.toDomainList(jpa.findAll());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
