package com.arka_app.arka.domain.service;

import com.arka_app.arka.domain.model.Customer;
import java.util.List;

public interface CustomerServicePort {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    Customer getCustomer(Long id);
    List<Customer> listCustomers();
    void deleteCustomer(Long id);
}
