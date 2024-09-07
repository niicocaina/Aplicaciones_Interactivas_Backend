package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date creationDate;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "basket")
    private List<ProductBasket> productBasketList;
}
