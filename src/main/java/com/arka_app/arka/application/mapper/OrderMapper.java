package com.arka_app.arka.application.mapper;




import com.arka_app.arka.application.dto.input.OrderRequestDto;
import com.arka_app.arka.application.dto.output.OrderResponseDto;
import com.arka_app.arka.domain.model.Order;
import com.arka_app.arka.domain.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public final class OrderMapper {
    private OrderMapper(){}

    public static Order toDomain(OrderRequestDto dto) {
        if (dto == null) return null;
        List<OrderItem> items = dto.getItems().stream().map(i ->
                OrderItem.builder()
                        .productId(i.getProductId())
                        .quantity(i.getQuantity())
                        .build()
        ).collect(Collectors.toList());

        return Order.builder()
                .customerId(dto.getCustomerId())
                .items(items)
                .build();
    }

    public static OrderResponseDto toResponse(Order domain) {
        if (domain == null) return null;
        List<OrderResponseDto.OrderItemResponse> items = domain.getItems() == null ? null :
                domain.getItems().stream().map(i ->
                        new OrderResponseDto.OrderItemResponse(i.getProductId(), i.getProductName(), i.getQuantity(), i.getPrice())
                ).collect(Collectors.toList());

        return OrderResponseDto.builder()
                .id(domain.getId())
                .customerId(domain.getCustomerId())
                .createdAt(domain.getCreatedAt())
                .items(items)
                .total(domain.getTotal())
                .status(domain.getStatus())
                .build();
    }
}
