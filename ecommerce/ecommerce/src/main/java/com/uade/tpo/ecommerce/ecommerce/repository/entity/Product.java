package com.uade.tpo.ecommerce.ecommerce.repository.entity;


import jakarta.persistence.*;

import java.lang.reflect.Array;
import java.util.List;

@Entity
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


    //private List<Array> imgUrl;

}
