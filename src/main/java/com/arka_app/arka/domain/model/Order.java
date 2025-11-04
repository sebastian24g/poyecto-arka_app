package com.arka_app.arka.domain.model;


import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private Long customerId;
    private OffsetDateTime createdAt;
    private List<OrderItem> items;
    private BigDecimal total;
    private OrderStatus status;
}

