package com.arka_app.arka.infraestructure.adapter.repository;


import com.arka_app.arka.infraestructure.percistence.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JpaCartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByCustomerIdAndActiveTrue(Long customerId);
}
