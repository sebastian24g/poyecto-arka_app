package com.arka_app.arka.infraestructure.percistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "cart_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cartId;
    private String eventType;
    private Long productId;
    private Integer quantity;
    private OffsetDateTime eventAt;
}
