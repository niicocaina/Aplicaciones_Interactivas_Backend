package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.*;

@Entity
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    @ManyToOne
    @JoinColumn(name = "prodcutId")
    private Product product;
}
