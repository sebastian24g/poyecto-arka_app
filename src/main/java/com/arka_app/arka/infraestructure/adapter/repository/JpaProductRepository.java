package com.arka_app.arka.infraestructure.adapter.repository;


import com.arka_app.arka.infraestructure.percistence.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findBySku(String sku);
    List<ProductEntity> findByStockLessThanEqual(Integer threshold);
    // for pagination using Pageable
    List<ProductEntity> findAllBy(Pageable pageable);
}

