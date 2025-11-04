package com.arka_app.arka.infraestructure.percistence.mapper;


import com.arka_app.arka.domain.model.StockHistory;
import com.arka_app.arka.infraestructure.percistence.entity.StockHistoryEntity;


import java.util.List;
import java.util.stream.Collectors;

public final class StockHistoryEntityMapper {
    private StockHistoryEntityMapper(){}

    public static StockHistory toDomain(StockHistoryEntity e) {
        if (e == null) return null;
        return StockHistory.builder()
                .id(e.getId())
                .productId(e.getProductId())
                .previousStock(e.getPreviousStock())
                .newStock(e.getNewStock())
                .reason(e.getReason())
                .changedBy(e.getChangedBy())
                .changedAt(e.getChangedAt())
                .build();
    }

    public static StockHistoryEntity toEntity(StockHistory d) {
        if (d == null) return null;
        return StockHistoryEntity.builder()
                .id(d.getId())
                .productId(d.getProductId())
                .previousStock(d.getPreviousStock())
                .newStock(d.getNewStock())
                .reason(d.getReason())
                .changedBy(d.getChangedBy())
                .changedAt(d.getChangedAt())
                .build();
    }

    public static List<StockHistory> toDomainList(List<StockHistoryEntity> list) {
        return list.stream().map(StockHistoryEntityMapper::toDomain).collect(Collectors.toList());
    }
}
