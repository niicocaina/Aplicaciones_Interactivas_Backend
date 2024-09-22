package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Category;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;

    private Category category;

    private String imgUrl;

    public ProductDTO(Product product) {
        this.id = product.getProductId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.promotionalPrice = product.getPromotionalPrice();
        this.stock = product.getStock();
        this.imgUrl = product.getImageUrl();
    }

}