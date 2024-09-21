package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProductBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productBasketId;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "basketId")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
