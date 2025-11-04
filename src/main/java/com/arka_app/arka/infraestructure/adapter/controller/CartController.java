package com.arka_app.arka.infraestructure.adapter.controller;

import com.arka_app.arka.application.dto.input.CartItemRequestDto;
import com.arka_app.arka.application.mapper.CartMapper;
import com.arka_app.arka.domain.service.CartServicePort;
import com.arka_app.arka.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartServicePort cartService;

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<?>> getCart(@PathVariable Long customerId) {
        var cart = cartService.getOrCreateCart(customerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart loaded", CartMapper.toResponse(cart)));
    }

    @PostMapping("/{customerId}/items")
    public ResponseEntity<ApiResponse<?>> addItem(@PathVariable Long customerId,
                                                  @Valid @RequestBody CartItemRequestDto dto) {
        var cart = cartService.addItem(customerId, dto.getProductId(), dto.getQuantity());
        return ResponseEntity.ok(new ApiResponse<>(true, "Item added to cart", CartMapper.toResponse(cart)));
    }

    @DeleteMapping("/{customerId}/items/{productId}")
    public ResponseEntity<ApiResponse<?>> removeItem(@PathVariable Long customerId,
                                                     @PathVariable Long productId) {
        var cart = cartService.removeItem(customerId, productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item removed from cart", CartMapper.toResponse(cart)));
    }

    @DeleteMapping("/{customerId}/clear")
    public ResponseEntity<ApiResponse<?>> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart cleared", null));
    }

    @PostMapping("/{customerId}/abandon")
    public ResponseEntity<ApiResponse<?>> markAbandoned(@PathVariable Long customerId) {
        cartService.markCartAsAbandoned(customerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart marked as abandoned", null));
    }

    @PostMapping("/{customerId}/checkout")
    public ResponseEntity<ApiResponse<?>> checkout(@PathVariable Long customerId) {
        cartService.checkoutCart(customerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Checkout completed successfully", null));
    }
}
