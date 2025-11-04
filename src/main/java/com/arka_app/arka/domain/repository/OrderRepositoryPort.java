package com.arka_app.arka.domain.repository;


import com.arka_app.arka.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findByCustomerId(Long customerId);
    void deleteById(Long id);
}
