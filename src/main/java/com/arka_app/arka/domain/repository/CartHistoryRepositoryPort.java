package com.arka_app.arka.domain.repository;

import com.arka_app.arka.domain.model.CartHistory;
import java.util.List;

public interface CartHistoryRepositoryPort {
    CartHistory save(CartHistory history);
    List<CartHistory> findByCartId(Long cartId);
}
