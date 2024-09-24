package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Category;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import lombok.*;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;
    private boolean featured;
    private Category category;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;

    public Product toProduct(){
        return Product.builder()
                .productId(this.getId())
                .name(this.getName())
                .description(this.getDescription())
                .price(this.getPrice())
                .promotionalPrice(this.getPromotionalPrice())
                .stock(this.getStock())
                .category(this.getCategory())
                .img1(this.getImg1())
                .img2(this.getImg2())
                .img3(this.getImg3())
                .img4(this.getImg4())
                .img5(this.getImg5())
                .build();
    }
}