package com.arka_app.arka.infraestructure.percistence.mapper;

import com.arka_app.arka.domain.model.CartHistory;
import com.arka_app.arka.infraestructure.percistence.entity.CartHistoryEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class CartHistoryEntityMapper {

    private CartHistoryEntityMapper() {}

    public static CartHistory toDomain(CartHistoryEntity e) {
        return CartHistory.builder()
                .id(e.getId())
                .cartId(e.getCartId())
                .eventType(e.getEventType())
                .productId(e.getProductId())
                .quantity(e.getQuantity())
                .eventAt(e.getEventAt())
                .build();
    }

    public static CartHistoryEntity toEntity(CartHistory h) {
        return CartHistoryEntity.builder()
                .id(h.getId())
                .cartId(h.getCartId())
                .eventType(h.getEventType())
                .productId(h.getProductId())
                .quantity(h.getQuantity())
                .eventAt(h.getEventAt())
                .build();
    }

    public static List<CartHistory> toDomainList(List<CartHistoryEntity> entities) {
        return entities.stream().map(CartHistoryEntityMapper::toDomain).collect(Collectors.toList());
    }
}
