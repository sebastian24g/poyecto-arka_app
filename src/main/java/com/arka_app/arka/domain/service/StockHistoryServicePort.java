package com.arka_app.arka.domain.service;

import com.arka_app.arka.domain.model.StockHistory;

import java.time.OffsetDateTime;
import java.util.List;

public interface StockHistoryServicePort {
    StockHistory record(StockHistory history); // registrar un cambio de stock
    List<StockHistory> getByProduct(Long productId);
    List<StockHistory> getByProductAndPeriod(Long productId, OffsetDateTime from, OffsetDateTime to);
    List<StockHistory> getByPeriod(OffsetDateTime from, OffsetDateTime to);
}

