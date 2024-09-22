package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Category;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
<<<<<<< HEAD:ecommerce/ecommerce/src/main/java/com/uade/tpo/ecommerce/ecommerce/dto/ProductInBasketDTO.java
import jakarta.persistence.*;
=======
>>>>>>> nico:ecommerce/ecommerce/src/main/java/com/uade/tpo/ecommerce/ecommerce/dto/ProductDTO.java
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
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;

<<<<<<< HEAD:ecommerce/ecommerce/src/main/java/com/uade/tpo/ecommerce/ecommerce/dto/ProductInBasketDTO.java
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

=======
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
>>>>>>> nico:ecommerce/ecommerce/src/main/java/com/uade/tpo/ecommerce/ecommerce/dto/ProductDTO.java
}