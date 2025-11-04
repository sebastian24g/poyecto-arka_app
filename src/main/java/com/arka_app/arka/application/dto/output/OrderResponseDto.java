package com.arka_app.arka.application.dto.output;


import com.arka_app.arka.domain.model.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private Long customerId;
    private OffsetDateTime createdAt;
    private List<OrderItemResponse> items;
    private BigDecimal total;
    private OrderStatus status;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
    }
}
