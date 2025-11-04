package com.arka_app.arka.domain.repository;


import com.arka_app.arka.domain.model.StockHistory;

import java.time.OffsetDateTime;
import java.util.List;

public interface StockHistoryRepositoryPort {
    StockHistory save(StockHistory history);
    List<StockHistory> findByProductId(Long productId);
    List<StockHistory> findByProductIdAndPeriod(Long productId, OffsetDateTime from, OffsetDateTime to);
    List<StockHistory> findAllByPeriod(OffsetDateTime from, OffsetDateTime to);
}

