package com.uade.tpo.ecommerce.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductInBasketDTO {
    private Long id;
    private Long productId;
    private int productStock;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private String img1;

}