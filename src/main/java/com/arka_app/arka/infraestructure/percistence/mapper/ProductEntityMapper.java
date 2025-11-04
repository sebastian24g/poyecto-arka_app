package com.arka_app.arka.infraestructure.percistence.mapper;

import com.arka_app.arka.domain.model.Product;
import com.arka_app.arka.infraestructure.percistence.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class ProductEntityMapper {
    private ProductEntityMapper(){}

    public static Product toDomain(ProductEntity e) {
        if (e == null) return null;
        return Product.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .price(e.getPrice())
                .stock(e.getStock())
                .sku(e.getSku())
                .categoryId(e.getCategoryId())
                .build();
    }

    public static ProductEntity toEntity(Product d) {
        if (d == null) return null;
        return ProductEntity.builder()
                .id(d.getId())
                .name(d.getName())
                .description(d.getDescription())
                .price(d.getPrice())
                .stock(d.getStock())
                .sku(d.getSku())
                .categoryId(d.getCategoryId())
                .build();
    }

    public static List<Product> toDomainList(List<ProductEntity> list) {
        return list.stream().map(ProductEntityMapper::toDomain).collect(Collectors.toList());
    }

    public static List<ProductEntity> toEntityList(List<Product> list) {
        return list.stream().map(ProductEntityMapper::toEntity).collect(Collectors.toList());
    }
}
