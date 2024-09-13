package com.uade.tpo.ecommerce.ecommerce.repository.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    public Product() {

    }


    //private List<Array> imgUrl;

}
