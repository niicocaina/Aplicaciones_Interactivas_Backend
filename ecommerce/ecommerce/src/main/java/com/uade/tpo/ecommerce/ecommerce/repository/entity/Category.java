package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.*;

import java.awt.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;

}
