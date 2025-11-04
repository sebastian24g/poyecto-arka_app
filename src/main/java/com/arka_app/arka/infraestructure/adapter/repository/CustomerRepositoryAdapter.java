package com.arka_app.arka.infraestructure.adapter.repository;


import com.arka_app.arka.domain.model.Customer;
import com.arka_app.arka.domain.repository.CustomerRepositoryPort;
import com.arka_app.arka.infraestructure.percistence.entity.CustomerEntity;
import com.arka_app.arka.infraestructure.percistence.mapper.CustomerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final JpaCustomerRepository jpa;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = CustomerEntityMapper.toEntity(customer);
        CustomerEntity saved = jpa.save(entity);
        return CustomerEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpa.findById(id).map(CustomerEntityMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return jpa.findByEmail(email).map(CustomerEntityMapper::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return CustomerEntityMapper.toDomainList(jpa.findAll());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
