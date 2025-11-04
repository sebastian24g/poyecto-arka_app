package com.arka_app.arka.infraestructure.adapter.repository;


import com.arka_app.arka.domain.model.Cart;
import com.arka_app.arka.domain.repository.CartRepositoryPort;
import com.arka_app.arka.infraestructure.percistence.entity.CartEntity;
import com.arka_app.arka.infraestructure.percistence.mapper.CartEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepositoryPort {

    private final JpaCartRepository jpa;

    @Override
    public Cart save(Cart cart) {
        CartEntity entity = CartEntityMapper.toEntity(cart);
        CartEntity saved = jpa.save(entity);
        return CartEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Cart> findActiveCartByCustomer(Long customerId) {
        return jpa.findByCustomerIdAndActiveTrue(customerId).map(CartEntityMapper::toDomain);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return jpa.findById(id).map(CartEntityMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
