package com.arka_app.arka.infraestructure.percistence.mapper;

import com.arka_app.arka.domain.model.Cart;
import com.arka_app.arka.infraestructure.percistence.entity.CartEntity;


public final class CartEntityMapper {

    private CartEntityMapper() {}

    public static Cart toDomain(CartEntity e) {
        return Cart.builder()
                .id(e.getId())
                .customerId(e.getCustomerId())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .active(e.getActive())
                .build();
    }

    public static CartEntity toEntity(Cart c) {
        return CartEntity.builder()
                .id(c.getId())
                .customerId(c.getCustomerId())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .active(c.getActive())
                .build();
    }
}
