package com.arka_app.arka.application.dto.output;


import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockHistoryResponseDto {
    private Long id;
    private Long productId;
    private Integer previousStock;
    private Integer newStock;
    private String reason;
    private String changedBy;
    private OffsetDateTime changedAt;
}
