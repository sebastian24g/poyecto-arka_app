package com.arka_app.arka.application.mapper;




import com.arka_app.arka.application.dto.input.ProductRequestDto;
import com.arka_app.arka.application.dto.output.ProductResponseDto;
import com.arka_app.arka.domain.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public final class ProductMapper {
    private ProductMapper(){}

    public static Product toDomain(ProductRequestDto dto) {
        if (dto == null) return null;
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .sku(dto.getSku())
                .categoryId(dto.getCategoryId())
                .build();
    }

    public static ProductResponseDto toResponse(Product domain) {
        if (domain == null) return null;
        return ProductResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .stock(domain.getStock())
                .sku(domain.getSku())
                .categoryId(domain.getCategoryId())
                .build();
    }

    public static List<ProductResponseDto> toResponseList(List<Product> list) {
        return list.stream().map(ProductMapper::toResponse).collect(Collectors.toList());
    }
}

