package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Category;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;
    private Category category;
    //private List imgUrl;
    //private String img;
    //private String img2;
    //private String img3;
    //private String img4;
    //private String img5;

    public Product toProduct(){
        return Product.builder()
                .productId(this.getId())
                .name(this.getName())
                .description(this.getDescription())
                .price(this.getPrice())
                .promotionalPrice(this.getPromotionalPrice())
                .stock(this.getStock())
                .category(this.getCategory())
                .build();
    }
}