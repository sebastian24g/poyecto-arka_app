package com.arka_app.arka.domain.service;

import com.arka_app.arka.domain.model.Cart;
import com.arka_app.arka.domain.model.CartItem;

import java.util.List;

public interface CartServicePort {

    Cart getOrCreateCart(Long customerId);

    Cart addItem(Long customerId, Long productId, Integer quantity);

    Cart removeItem(Long customerId, Long productId);

    List<CartItem> getItems(Long customerId);

    void clearCart(Long customerId);

    void markCartAsAbandoned(Long customerId);

    void checkoutCart(Long customerId);
}
