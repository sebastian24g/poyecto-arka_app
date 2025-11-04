package com.arka_app.arka.domain.model;

import lombok.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartHistory {
    private Long id;
    private Long cartId;
    private String eventType;   // e.g. ADDED, REMOVED, ABANDONED, CHECKOUT
    private Long productId;
    private Integer quantity;
    private OffsetDateTime eventAt;
}

