package com.arka_app.arka.infraestructure.percistence.mapper;

import com.arka_app.arka.domain.model.CartItem;
import com.arka_app.arka.infraestructure.percistence.entity.CartItemEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class CartItemEntityMapper {

    private CartItemEntityMapper() {}

    public static CartItem toDomain(CartItemEntity e) {
        return CartItem.builder()
                .id(e.getId())
                .cartId(e.getCartId())
                .productId(e.getProductId())
                .productName(e.getProductName())
                .price(e.getPrice())
                .quantity(e.getQuantity())
                .addedAt(e.getAddedAt())
                .build();
    }

    public static CartItemEntity toEntity(CartItem i) {
        return CartItemEntity.builder()
                .id(i.getId())
                .cartId(i.getCartId())
                .productId(i.getProductId())
                .productName(i.getProductName())
                .price(i.getPrice())
                .quantity(i.getQuantity())
                .addedAt(i.getAddedAt())
                .build();
    }

    public static List<CartItem> toDomainList(List<CartItemEntity> entities) {
        return entities.stream().map(CartItemEntityMapper::toDomain).collect(Collectors.toList());
    }
}
