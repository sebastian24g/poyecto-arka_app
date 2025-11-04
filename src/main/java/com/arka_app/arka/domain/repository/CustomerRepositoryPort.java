package com.arka_app.arka.domain.repository;

import com.arka_app.arka.domain.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    void deleteById(Long id);
}
