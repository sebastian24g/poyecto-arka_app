package com.arka_app.arka.domain.repository;

import com.arka_app.arka.domain.model.Cart;
import java.util.Optional;

public interface CartRepositoryPort {
    Cart save(Cart cart);
    Optional<Cart> findActiveCartByCustomer(Long customerId);
    Optional<Cart> findById(Long id);
    void deleteById(Long id);
}
