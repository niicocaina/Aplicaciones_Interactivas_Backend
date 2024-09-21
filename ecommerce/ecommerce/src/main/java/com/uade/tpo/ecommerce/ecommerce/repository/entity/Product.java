package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;

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
    //@Min(0)
    private int price;
    private int promotionalPrice;
    private int stock;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;

    public ProductDTO toProductDTO(){
        return ProductDTO.builder()
                .id(this.getProductId())
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
