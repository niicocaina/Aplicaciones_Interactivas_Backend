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



}
