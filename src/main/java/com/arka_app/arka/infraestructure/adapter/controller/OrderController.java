package com.arka_app.arka.infraestructure.adapter.controller;

import com.arka_app.arka.application.dto.input.OrderRequestDto;
import com.arka_app.arka.application.dto.output.OrderResponseDto;
import com.arka_app.arka.application.mapper.OrderMapper;
import com.arka_app.arka.domain.model.Order;
import com.arka_app.arka.domain.service.OrderServicePort;
import com.arka_app.arka.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServicePort orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> create(@Valid @RequestBody OrderRequestDto dto) {
        Order domain = OrderMapper.toDomain(dto);
        Order created = orderService.createOrder(domain);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order created", OrderMapper.toResponse(created)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> update(@PathVariable Long id,
                                                                @Valid @RequestBody OrderRequestDto dto) {
        Order domain = OrderMapper.toDomain(dto);
        Order updated = orderService.updateOrder(id, domain);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order updated", OrderMapper.toResponse(updated)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> get(@PathVariable Long id) {
        Order o = orderService.getOrder(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order retrieved", OrderMapper.toResponse(o)));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> listByCustomer(@PathVariable Long customerId) {
        var list = orderService.listByCustomer(customerId)
                .stream().map(OrderMapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Orders for customer", list));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponseDto>> changeStatus(@PathVariable Long id, @RequestParam String status) {
        Order updated = orderService.changeStatus(id, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Status updated", OrderMapper.toResponse(updated)));
    }
}

