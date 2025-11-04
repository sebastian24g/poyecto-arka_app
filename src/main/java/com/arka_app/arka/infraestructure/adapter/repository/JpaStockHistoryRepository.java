package com.arka_app.arka.infraestructure.adapter.repository;



import com.arka_app.arka.infraestructure.percistence.entity.StockHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface JpaStockHistoryRepository extends JpaRepository<StockHistoryEntity, Long> {
    List<StockHistoryEntity> findByProductIdOrderByChangedAtDesc(Long productId);
    List<StockHistoryEntity> findByProductIdAndChangedAtBetweenOrderByChangedAtDesc(Long productId, OffsetDateTime from, OffsetDateTime to);
    List<StockHistoryEntity> findByChangedAtBetweenOrderByChangedAtDesc(OffsetDateTime from, OffsetDateTime to);
}
