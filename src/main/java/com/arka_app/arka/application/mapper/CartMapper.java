package com.arka_app.arka.application.mapper;


import com.arka_app.arka.application.dto.output.CartItemResponseDto;
import com.arka_app.arka.application.dto.output.CartResponseDto;
import com.arka_app.arka.domain.model.Cart;
import com.arka_app.arka.domain.model.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public final class CartMapper {

    private CartMapper() {}

    public static CartItemResponseDto toResponse(CartItem item) {
        return CartItemResponseDto.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .addedAt(item.getAddedAt())
                .build();
    }

    public static List<CartItemResponseDto> toResponseList(List<CartItem> items) {
        return items.stream().map(CartMapper::toResponse).collect(Collectors.toList());
    }

    public static CartResponseDto toResponse(Cart cart) {
        return CartResponseDto.builder()
                .id(cart.getId())
                .customerId(cart.getCustomerId())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .active(cart.getActive())
                .items(cart.getItems() != null ? toResponseList(cart.getItems()) : List.of())
                .build();
    }
}
