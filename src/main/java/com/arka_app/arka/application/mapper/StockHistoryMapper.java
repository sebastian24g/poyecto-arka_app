package com.arka_app.arka.application.mapper;



import com.arka_app.arka.application.dto.input.StockHistoryRequestDto;
import com.arka_app.arka.application.dto.output.StockHistoryResponseDto;
import com.arka_app.arka.domain.model.StockHistory;

import java.util.List;
import java.util.stream.Collectors;
import java.time.OffsetDateTime;

public final class StockHistoryMapper {
    private StockHistoryMapper(){}

    public static StockHistory toDomain(StockHistoryRequestDto dto) {
        if (dto == null) return null;
        return StockHistory.builder()
                .productId(dto.getProductId())
                .previousStock(dto.getPreviousStock())
                .newStock(dto.getNewStock())
                .reason(dto.getReason())
                .changedBy(dto.getChangedBy())
                .changedAt(OffsetDateTime.now())
                .build();
    }

    public static StockHistoryResponseDto toResponse(StockHistory d) {
        if (d == null) return null;
        return StockHistoryResponseDto.builder()
                .id(d.getId())
                .productId(d.getProductId())
                .previousStock(d.getPreviousStock())
                .newStock(d.getNewStock())
                .reason(d.getReason())
                .changedBy(d.getChangedBy())
                .changedAt(d.getChangedAt())
                .build();
    }

    public static List<StockHistoryResponseDto> toResponseList(List<StockHistory> list) {
        return list.stream().map(StockHistoryMapper::toResponse).collect(Collectors.toList());
    }
}
