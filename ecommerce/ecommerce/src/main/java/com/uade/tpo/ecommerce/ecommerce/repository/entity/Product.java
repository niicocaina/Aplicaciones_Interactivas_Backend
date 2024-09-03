package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.*;

import java.awt.*;
import java.lang.reflect.Array;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private List imgUrl;

}
