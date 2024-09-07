package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long userId;

    private String name;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private Date birthday;

    @OneToOne(mappedBy = "user")
    private Basket basket;

    @OneToMany(mappedBy = "user")
    private List<Favorites> products;
}
