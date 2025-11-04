package com.arka_app.arka.infraestructure.adapter.repository;

import com.arka_app.arka.domain.model.CartHistory;
import com.arka_app.arka.domain.repository.CartHistoryRepositoryPort;
import com.arka_app.arka.infraestructure.percistence.entity.CartHistoryEntity;
import com.arka_app.arka.infraestructure.percistence.mapper.CartHistoryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartHistoryRepositoryAdapter implements CartHistoryRepositoryPort {

    private final JpaCartHistoryRepository jpa;

    @Override
    public CartHistory save(CartHistory history) {
        CartHistoryEntity entity = CartHistoryEntityMapper.toEntity(history);
        CartHistoryEntity saved = jpa.save(entity);
        return CartHistoryEntityMapper.toDomain(saved);
    }

    @Override
    public List<CartHistory> findByCartId(Long cartId) {
        return CartHistoryEntityMapper.toDomainList(jpa.findByCartId(cartId));
    }
}
