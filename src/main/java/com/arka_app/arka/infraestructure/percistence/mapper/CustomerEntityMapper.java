package com.arka_app.arka.infraestructure.percistence.mapper;


import com.arka_app.arka.domain.model.Customer;
import com.arka_app.arka.infraestructure.percistence.entity.CustomerEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class CustomerEntityMapper {

    private CustomerEntityMapper() {}

    public static Customer toDomain(CustomerEntity e) {
        return Customer.builder()
                .id(e.getId())
                .fullName(e.getFullName())
                .email(e.getEmail())
                .phone(e.getPhone())
                .address(e.getAddress())
                .active(e.getActive())
                .build();
    }

    public static CustomerEntity toEntity(Customer c) {
        return CustomerEntity.builder()
                .id(c.getId())
                .fullName(c.getFullName())
                .email(c.getEmail())
                .phone(c.getPhone())
                .address(c.getAddress())
                .active(c.getActive())
                .build();
    }

    public static List<Customer> toDomainList(List<CustomerEntity> list) {
        return list.stream().map(CustomerEntityMapper::toDomain).collect(Collectors.toList());
    }
}
