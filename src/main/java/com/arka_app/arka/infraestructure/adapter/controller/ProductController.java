package com.arka_app.arka.infraestructure.adapter.controller;




import com.arka_app.arka.application.dto.input.ProductRequestDto;
import com.arka_app.arka.application.dto.output.ProductResponseDto;
import com.arka_app.arka.application.mapper.ProductMapper;
import com.arka_app.arka.domain.model.Product;
import com.arka_app.arka.domain.service.ProductServicePort;
import com.arka_app.arka.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServicePort productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> create(@Valid @RequestBody ProductRequestDto dto) {
        Product domain = ProductMapper.toDomain(dto);
        Product created = productService.createProduct(domain);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product created", ProductMapper.toResponse(created)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> get(@PathVariable Long id) {
        Product p = productService.getProduct(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product retrieved", ProductMapper.toResponse(p)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var list = productService.listProducts(page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, "Products list", ProductMapper.toResponseList(list)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> update(@PathVariable Long id,
                                                                  @Valid @RequestBody ProductRequestDto dto) {
        Product domain = ProductMapper.toDomain(dto);
        Product updated = productService.updateProduct(id, domain);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product updated", ProductMapper.toResponse(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product deleted", null));
    }

    @PostMapping("/stock")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateStock(@RequestParam Long productId,
                                                                       @RequestParam Integer newStock,
                                                                       @RequestParam(required = false) String reason,
                                                                       @RequestParam(required = false) String changedBy) {
        Product updated = productService.updateStock(productId, newStock, reason, changedBy);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stock updated", ProductMapper.toResponse(updated)));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> lowStock(@RequestParam(defaultValue = "10") int threshold) {
        var list = productService.findLowStock(threshold);
        return ResponseEntity.ok(new ApiResponse<>(true, "Low stock products", ProductMapper.toResponseList(list)));
    }
}

