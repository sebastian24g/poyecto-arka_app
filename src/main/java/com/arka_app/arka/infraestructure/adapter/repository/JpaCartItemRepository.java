package com.arka_app.arka.infraestructure.adapter.repository;

import com.arka_app.arka.infraestructure.percistence.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByCartId(Long cartId);
    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);
    void deleteAllByCartId(Long cartId);
}
