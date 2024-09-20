package com.uade.tpo.ecommerce.ecommerce.repository.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Array;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
public class Product {
    public Product() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    //private List<Array> imgUrl;
    //private String img;
    //private String img2;
    //private String img3;
    //private String img4;
    //private String img5;

    public ProductDTO toProductDTO(){
        return ProductDTO.builder()
                .id(this.getProductId())
                .name(this.getName())
                .description(this.getDescription())
                .price(this.getPrice())
                .promotionalPrice(this.getPromotionalPrice())
                .stock(this.getStock())
                .category(this.getCategory())
                .build();
    }

}
