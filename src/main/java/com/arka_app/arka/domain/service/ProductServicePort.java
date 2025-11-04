package com.arka_app.arka.domain.service;


import com.arka_app.arka.domain.model.Product;

import java.util.List;

public interface ProductServicePort {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    Product getProduct(Long id);
    List<Product> listProducts(int page, int size);
    void deleteProduct(Long id);
    Product updateStock(Long productId, Integer newStock, String reason, String changedBy);
    List<Product> findLowStock(int threshold);
}

