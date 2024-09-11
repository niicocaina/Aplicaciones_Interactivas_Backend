package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class CheckOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkOutId;
    private Date transactionDate;
    private int total;

    @ManyToOne
    @JoinColumn(name = "basketId")
    private Basket basket;
}
