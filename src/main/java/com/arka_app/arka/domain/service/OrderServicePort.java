package com.arka_app.arka.domain.service;


import com.arka_app.arka.domain.model.Order;

import java.util.List;

public interface OrderServicePort {
    Order createOrder(Order order); // valida stock y decrementa
    Order updateOrder(Long orderId, Order order); // solo PENDING
    Order getOrder(Long orderId);
    List<Order> listByCustomer(Long customerId);
    Order changeStatus(Long orderId, String status);
}

