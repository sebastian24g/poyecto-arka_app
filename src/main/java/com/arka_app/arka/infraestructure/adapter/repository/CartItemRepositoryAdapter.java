package com.arka_app.arka.infraestructure.adapter.repository;

import com.arka_app.arka.domain.model.CartItem;
import com.arka_app.arka.domain.repository.CartItemRepositoryPort;
import com.arka_app.arka.infraestructure.percistence.entity.CartItemEntity;
import com.arka_app.arka.infraestructure.percistence.mapper.CartItemEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemRepositoryAdapter implements CartItemRepositoryPort {

    private final JpaCartItemRepository jpa;

    @Override
    public CartItem save(CartItem item) {
        CartItemEntity entity = CartItemEntityMapper.toEntity(item);
        CartItemEntity saved = jpa.save(entity);
        return CartItemEntityMapper.toDomain(saved);
    }

    @Override
    public List<CartItem> findByCartId(Long cartId) {
        return CartItemEntityMapper.toDomainList(jpa.findByCartId(cartId));
    }

    @Override
    public Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId) {
        return jpa.findByCartIdAndProductId(cartId, productId).map(CartItemEntityMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    @Override
    public void deleteAllByCartId(Long cartId) {
        jpa.deleteAllByCartId(cartId);
    }
}
