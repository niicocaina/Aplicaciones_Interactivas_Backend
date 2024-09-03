package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    private long id;
}