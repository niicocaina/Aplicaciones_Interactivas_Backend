package com.uade.tpo.ecommerce.ecommerce.repository.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;
    private String imageUrl;
    private boolean featured;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;



}
