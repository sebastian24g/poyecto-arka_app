package com.arka_app.arka.application.mapper;


import com.arka_app.arka.application.dto.input.CustomerRequestDto;
import com.arka_app.arka.application.dto.output.CustomerResponseDto;
import com.arka_app.arka.domain.model.Customer;

import java.util.List;
import java.util.stream.Collectors;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static Customer toDomain(CustomerRequestDto dto) {
        if (dto == null) return null;
        return Customer.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
    }

    public static CustomerResponseDto toResponse(Customer c) {
        return CustomerResponseDto.builder()
                .id(c.getId())
                .fullName(c.getFullName())
                .email(c.getEmail())
                .phone(c.getPhone())
                .address(c.getAddress())
                .active(c.getActive())
                .build();
    }

    public static List<CustomerResponseDto> toResponseList(List<Customer> list) {
        return list.stream().map(CustomerMapper::toResponse).collect(Collectors.toList());
    }
}
