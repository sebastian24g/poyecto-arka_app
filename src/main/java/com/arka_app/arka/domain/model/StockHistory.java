package com.arka_app.arka.domain.model;


import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockHistory {
    private Long id;
    private Long productId;
    private Integer previousStock;
    private Integer newStock;
    private String reason;      // e.g. "order", "adjustment", "import", "correction"
    private String changedBy;   // user id or system
    private OffsetDateTime changedAt;
}

