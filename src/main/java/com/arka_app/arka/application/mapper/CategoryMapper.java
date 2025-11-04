package com.arka_app.arka.application.mapper;



import com.arka_app.arka.application.dto.input.CategoryRequestDto;
import com.arka_app.arka.application.dto.output.CategoryResponseDto;
import com.arka_app.arka.domain.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public final class CategoryMapper {

    private CategoryMapper() {}

    public static Category toDomain(CategoryRequestDto dto) {
        if (dto == null) return null;
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
    }

    public static CategoryResponseDto toResponse(Category c) {
        return CategoryResponseDto.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .active(c.getActive())
                .build();
    }

    public static List<CategoryResponseDto> toResponseList(List<Category> list) {
        return list.stream().map(CategoryMapper::toResponse).collect(Collectors.toList());
    }
}
