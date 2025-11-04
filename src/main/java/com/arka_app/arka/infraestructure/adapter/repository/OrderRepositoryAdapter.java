package com.arka_app.arka.infraestructure.adapter.repository;


import com.arka_app.arka.domain.model.Order;
import com.arka_app.arka.domain.repository.OrderRepositoryPort;

import com.arka_app.arka.infraestructure.percistence.entity.OrderEntity;
import com.arka_app.arka.infraestructure.percistence.mapper.OrderEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final JpaOrderRepository jpa;

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderEntityMapper.toEntity(order);
        OrderEntity saved = jpa.save(entity);
        return OrderEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpa.findById(id).map(OrderEntityMapper::toDomain);
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return jpa.findByCustomerId(customerId)
                .stream().map(OrderEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
