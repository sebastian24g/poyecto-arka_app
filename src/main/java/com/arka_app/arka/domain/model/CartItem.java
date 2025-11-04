package com.arka_app.arka.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private OffsetDateTime addedAt;
}
