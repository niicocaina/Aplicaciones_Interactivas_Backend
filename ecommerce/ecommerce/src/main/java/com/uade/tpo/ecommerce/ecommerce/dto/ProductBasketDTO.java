package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ProductBasketDTO {
    private Long productBasketId;
    private int quantity;
    private Basket basket;
    private Product product;

    public ProductBasketDTO() {}
}
