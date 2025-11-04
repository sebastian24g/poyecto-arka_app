package com.arka_app.arka.infraestructure.percistence.mapper;


import com.arka_app.arka.domain.model.Order;
import com.arka_app.arka.domain.model.OrderItem;
import com.arka_app.arka.infraestructure.percistence.entity.OrderEntity;
import com.arka_app.arka.infraestructure.percistence.entity.OrderItemEntity;


import java.util.List;
import java.util.stream.Collectors;

public final class OrderEntityMapper {
    private OrderEntityMapper(){}

    public static Order toDomain(OrderEntity e) {
        if (e == null) return null;
        List<OrderItem> items = e.getItems() == null ? null :
                e.getItems().stream().map(OrderEntityMapper::toDomainItem).collect(Collectors.toList());
        return Order.builder()
                .id(e.getId())
                .customerId(e.getCustomerId())
                .createdAt(e.getCreatedAt())
                .items(items)
                .total(e.getTotal())
                .status(e.getStatus())
                .build();
    }

    public static OrderItem toDomainItem(OrderItemEntity ie) {
        if (ie == null) return null;
        return OrderItem.builder()
                .id(ie.getId())
                .productId(ie.getProductId())
                .productName(ie.getProductName())
                .quantity(ie.getQuantity())
                .price(ie.getPrice())
                .build();
    }

    public static OrderEntity toEntity(Order d) {
        if (d == null) return null;
        OrderEntity e = OrderEntity.builder()
                .id(d.getId())
                .customerId(d.getCustomerId())
                .createdAt(d.getCreatedAt())
                .total(d.getTotal())
                .status(d.getStatus())
                .build();
        if (d.getItems() != null) {
            List<OrderItemEntity> items = d.getItems().stream().map(i -> {
                OrderItemEntity ie = OrderItemEntity.builder()
                        .id(i.getId())
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .order(e)
                        .build();
                return ie;
            }).collect(Collectors.toList());
            e.setItems(items);
        }
        return e;
    }
}
