package com.arka_app.arka.infraestructure.adapter.repository;



import com.arka_app.arka.domain.model.Product;
import com.arka_app.arka.domain.repository.ProductRepositoryPort;
import com.arka_app.arka.infraestructure.percistence.entity.ProductEntity;
import com.arka_app.arka.infraestructure.percistence.mapper.ProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final JpaProductRepository jpa;

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductEntityMapper.toEntity(product);
        ProductEntity saved = jpa.save(entity);
        return ProductEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpa.findById(id).map(ProductEntityMapper::toDomain);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return jpa.findBySku(sku).map(ProductEntityMapper::toDomain);
    }

    @Override
    public List<Product> findAll(int page, int size) {
        var entities = jpa.findAllBy(PageRequest.of(Math.max(0, page), Math.max(1, size)));
        return ProductEntityMapper.toDomainList(entities);
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    @Override
    public List<Product> findLowStock(int threshold) {
        var entities = jpa.findByStockLessThanEqual(threshold);
        return ProductEntityMapper.toDomainList(entities);
    }
}
