package com.arka_app.arka.application.dto.input;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockHistoryRequestDto {
    @NotNull
    private Long productId;

    @NotNull
    @Min(0)
    private Integer previousStock;

    @NotNull
    @Min(0)
    private Integer newStock;

    @NotBlank
    private String reason;

    @NotBlank
    private String changedBy;
}
