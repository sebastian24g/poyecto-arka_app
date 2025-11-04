package com.arka_app.arka.infraestructure.percistence.mapper;


import com.arka_app.arka.domain.model.Category;
import com.arka_app.arka.infraestructure.percistence.entity.CategoryEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class CategoryEntityMapper {

    private CategoryEntityMapper() {}

    public static Category toDomain(CategoryEntity e) {
        return Category.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .active(e.getActive())
                .build();
    }

    public static CategoryEntity toEntity(Category c) {
        return CategoryEntity.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .active(c.getActive())
                .build();
    }

    public static List<Category> toDomainList(List<CategoryEntity> list) {
        return list.stream().map(CategoryEntityMapper::toDomain).collect(Collectors.toList());
    }
}
