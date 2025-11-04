package com.arka_app.arka.domain.repository;



import com.arka_app.arka.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findBySku(String sku);
    List<Product> findAll(int page, int size);
    void deleteById(Long id);
    List<Product> findLowStock(int threshold);
}

