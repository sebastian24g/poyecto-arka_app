package com.arka_app.arka.domain.repository;

import com.arka_app.arka.domain.model.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemRepositoryPort {
    CartItem save(CartItem item);
    List<CartItem> findByCartId(Long cartId);
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    void deleteById(Long id);
    void deleteAllByCartId(Long cartId);
}
