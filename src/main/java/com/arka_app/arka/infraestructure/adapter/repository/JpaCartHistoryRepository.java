package com.arka_app.arka.infraestructure.adapter.repository;

import com.arka_app.arka.infraestructure.percistence.entity.CartHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaCartHistoryRepository extends JpaRepository<CartHistoryEntity, Long> {
    List<CartHistoryEntity> findByCartId(Long cartId);
}
