package com.arka_app.arka.application.usecase;


import com.arka_app.arka.domain.model.StockHistory;
import com.arka_app.arka.domain.repository.StockHistoryRepositoryPort;
import com.arka_app.arka.domain.service.StockHistoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockHistoryServiceImpl implements StockHistoryServicePort {

    private final StockHistoryRepositoryPort historyRepository;

    @Override
    @Transactional
    public StockHistory record(StockHistory history) {
        // Ensure timestamp
        if (history.getChangedAt() == null) {
            history.setChangedAt(OffsetDateTime.now());
        }
        return historyRepository.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockHistory> getByProduct(Long productId) {
        return historyRepository.findByProductId(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockHistory> getByProductAndPeriod(Long productId, OffsetDateTime from, OffsetDateTime to) {
        return historyRepository.findByProductIdAndPeriod(productId, from, to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockHistory> getByPeriod(OffsetDateTime from, OffsetDateTime to) {
        return historyRepository.findAllByPeriod(from, to);
    }
}

