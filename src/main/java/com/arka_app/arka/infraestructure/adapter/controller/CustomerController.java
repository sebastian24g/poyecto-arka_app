package com.arka_app.arka.infraestructure.adapter.controller;



import com.arka_app.arka.application.dto.input.CustomerRequestDto;
import com.arka_app.arka.application.dto.output.CustomerResponseDto;
import com.arka_app.arka.application.mapper.CustomerMapper;
import com.arka_app.arka.domain.model.Customer;
import com.arka_app.arka.domain.service.CustomerServicePort;
import com.arka_app.arka.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServicePort customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDto>> create(@Valid @RequestBody CustomerRequestDto dto) {
        Customer customer = CustomerMapper.toDomain(dto);
        Customer saved = customerService.createCustomer(customer);
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer created", CustomerMapper.toResponse(saved)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> update(@PathVariable Long id,
                                                                   @Valid @RequestBody CustomerRequestDto dto) {
        Customer customer = CustomerMapper.toDomain(dto);
        Customer updated = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer updated", CustomerMapper.toResponse(updated)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> getById(@PathVariable Long id) {
        Customer customer = customerService.getCustomer(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer found", CustomerMapper.toResponse(customer)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> list() {
        var list = customerService.listCustomers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer list", CustomerMapper.toResponseList(list)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer deleted", null));
    }
}
