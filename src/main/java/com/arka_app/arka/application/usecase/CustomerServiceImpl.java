package com.arka_app.arka.application.usecase;

import com.arka_app.arka.domain.model.Customer;
import com.arka_app.arka.domain.repository.CustomerRepositoryPort;
import com.arka_app.arka.domain.service.CustomerServicePort;
import com.arka_app.arka.shared.exeptio.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerServicePort {

    private final CustomerRepositoryPort customerRepository;

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        customer.setActive(customer.getActive() != null ? customer.getActive() : true);
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));

        existing.setFullName(customer.getFullName() != null ? customer.getFullName() : existing.getFullName());
        existing.setEmail(customer.getEmail() != null ? customer.getEmail() : existing.getEmail());
        existing.setPhone(customer.getPhone() != null ? customer.getPhone() : existing.getPhone());
        existing.setAddress(customer.getAddress() != null ? customer.getAddress() : existing.getAddress());
        existing.setActive(customer.getActive() != null ? customer.getActive() : existing.getActive());

        return customerRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
