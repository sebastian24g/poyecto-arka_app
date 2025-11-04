package com.arka_app.arka.infraestructure.adapter.repository;


import com.arka_app.arka.domain.model.StockHistory;
import com.arka_app.arka.domain.repository.StockHistoryRepositoryPort;
import com.arka_app.arka.infraestructure.percistence.entity.StockHistoryEntity;
import com.arka_app.arka.infraestructure.percistence.mapper.StockHistoryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StockHistoryRepositoryAdapter implements StockHistoryRepositoryPort {

    private final JpaStockHistoryRepository jpa;

    @Override
    public StockHistory save(StockHistory history) {
        StockHistoryEntity entity = StockHistoryEntityMapper.toEntity(history);
        // Ensure timestamp set if null
        if (entity.getChangedAt() == null) {
            entity.setChangedAt(OffsetDateTime.now());
        }
        StockHistoryEntity saved = jpa.save(entity);
        return StockHistoryEntityMapper.toDomain(saved);
    }

    @Override
    public List<StockHistory> findByProductId(Long productId) {
        var entities = jpa.findByProductIdOrderByChangedAtDesc(productId);
        return StockHistoryEntityMapper.toDomainList(entities);
    }

    @Override
    public List<StockHistory> findByProductIdAndPeriod(Long productId, OffsetDateTime from, OffsetDateTime to) {
        var entities = jpa.findByProductIdAndChangedAtBetweenOrderByChangedAtDesc(productId, from, to);
        return StockHistoryEntityMapper.toDomainList(entities);
    }

    @Override
    public List<StockHistory> findAllByPeriod(OffsetDateTime from, OffsetDateTime to) {
        var entities = jpa.findByChangedAtBetweenOrderByChangedAtDesc(from, to);
        return StockHistoryEntityMapper.toDomainList(entities);
    }
}
